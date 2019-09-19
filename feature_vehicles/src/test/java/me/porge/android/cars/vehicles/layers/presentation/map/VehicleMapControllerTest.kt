package me.porge.android.cars.vehicles.layers.presentation.map

import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.UiSettings
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import io.mockk.*
import me.porge.android.cars.vehicles.layers.domain.entities.Vehicle
import me.porge.android.cars.vehicles.layers.presentation.extensions.toBitmapDescriptor
import me.porge.android.cars.vehicles.tests.createFakeVehicle
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class VehicleMapControllerTest {

    private val mockMarker = mockk<Marker>(relaxUnitFun = true) {
        every { position } returns mockk()
    }

    private val mockUiSettings = mockk<UiSettings>(relaxUnitFun = true)

    private val mockGoogleMap = mockk<GoogleMap>(relaxUnitFun = true) {
        every { addMarker(any()) } returns mockMarker
        every { uiSettings } returns mockUiSettings
    }

    private val mockSelectedIcon = mockk<BitmapDescriptor>()
    private val mockNormalIcon = mockk<BitmapDescriptor>()
    private val mockOnVehicleSelected: (Vehicle) -> Unit = spyk()

    private val mapController = spyk(
        VehicleMapController(
            selectedMarkerIcon = mockSelectedIcon,
            normalMarkerIcon = mockNormalIcon,
            googleMap = mockGoogleMap,
            onVehicleSelected = mockOnVehicleSelected
        )
    )

    @Before
    fun onSetup() {
        mockkStatic(ContextCompat::class)
        mockkStatic(CameraUpdateFactory::class)
        mockkStatic(DRAWABLE_EXT)

        every {
            CameraUpdateFactory.newLatLngBounds(any(), any())
        } returns mockk()

        every {
            CameraUpdateFactory.newLatLngZoom(any(), any())
        } returns mockk()

        val mockDrawable = mockk<Drawable> {
            every { toBitmapDescriptor() } returns mockk()
        }

        every {
            ContextCompat.getDrawable(any(), any())
        } returns mockDrawable

        verify(exactly = 1) {
            mockUiSettings.apply {
                isZoomControlsEnabled = false
                isZoomGesturesEnabled = true
                isRotateGesturesEnabled = true
                isCompassEnabled = false
            }

            mockGoogleMap.setOnMarkerClickListener(any())
        }
    }

    @Test
    fun `GIVEN fake vehicles, WHEN setting vehicles, THEN assert success`() {
        // Given
        mockkConstructor(LatLngBounds.Builder::class)
        val latLngSlot = slot<LatLng>()
        val vehicle = createFakeVehicle(99)

        every {
            with(mapController) {
                mockGoogleMap.addVehicleMarker(vehicle)
            }
        } returns mockMarker

        every {
            mockMarker.position
        } returns LatLng(vehicle.latitude, vehicle.longitude)

        // When
        mapController.setVehicles(listOf(vehicle))

        // Then
        verify(exactly = 1) {
            mockGoogleMap.clear()
        }

        assertEquals(1, mapController.markers.size)
        assertEquals(mockMarker, mapController.markers.values.first())

        verify {
            anyConstructed<LatLngBounds.Builder>().apply {
                include(capture(latLngSlot))
                build()
            }

            mockGoogleMap.animateCamera(any())
        }

        assertEquals(vehicle.latitude, latLngSlot.captured.latitude, 0.0)
        assertEquals(vehicle.longitude, latLngSlot.captured.longitude, 0.0)
    }

    @Test
    fun `GIVEN empty list of vehicles, WHEN setting vehicles, THEN assert success`() {
        // Given
        mockkConstructor(LatLngBounds.Builder::class)
        val latLngSlot = slot<LatLng>()

        // When
        mapController.setVehicles(emptyList())

        // Then
        verify(exactly = 1) {
            mockGoogleMap.clear()
        }

        assertEquals(0, mapController.markers.size)

        verify(exactly = 0) {
            anyConstructed<LatLngBounds.Builder>().apply {
                include(capture(latLngSlot))
                build()
            }

            mockGoogleMap.animateCamera(any())
        }
    }

    @Test
    fun `GIVEN fake vehicle, WHEN focused on existent vehicle, THEN assert success`() {
        // Given
        val vehicle = createFakeVehicle()
        mapController.setVehicles(listOf(vehicle))
        every { mapController.onMarkerClick(any()) } returns true

        // When
        mapController.focusOnVehicle(vehicle)

        // Then
        verify(exactly = 1) {
            mapController.onMarkerClick(any())
        }
    }

    @Test
    fun `GIVEN fake valid vehicle, WHEN focused on nonexistent vehicle, THEN assert success`() {
        // Given
        val vehicle = createFakeVehicle()
        every { mapController.onMarkerClick(any()) } returns true
        every {
            with(mapController) {
                mockGoogleMap.addVehicleMarker(vehicle)
            }
        } returns mockMarker

        // When
        mapController.focusOnVehicle(vehicle)

        // Then
        verify(exactly = 1) {
            with(mapController) {
                mockGoogleMap.addVehicleMarker(vehicle)
                onMarkerClick(any())
            }
        }
    }

    @Test
    fun `GIVEN null marker, WHEN marker was clicked, THEN assert no reaction`() {
        // Given

        // When
        val result = mapController.onMarkerClick(null)

        // Then
        assertFalse(result)
    }

    @Test
    fun `GIVEN mock marker, WHEN marker was clicked, THEN assert show info and camera focused`() {
        // Given
        val vehicle = createFakeVehicle()
        mapController.setVehicles(listOf(vehicle))
        val latLngSlot = slot<LatLng>()
        val mockOldSelectedMarker = mockk<Marker>(relaxUnitFun = true)
        mapController.selectedMarker = mockOldSelectedMarker

        // When
        val result = mapController.onMarkerClick(mockMarker)

        // Then
        assertTrue(result)
        assertEquals(mockMarker, mapController.selectedMarker)
        verify {
            mockOnVehicleSelected.invoke(vehicle)
            mockOldSelectedMarker.setIcon(mockNormalIcon)
            mockMarker.setIcon(mockSelectedIcon)
            CameraUpdateFactory.newLatLngZoom(
                capture(latLngSlot),
                VehicleMapController.DEFAULT_SELECTED_ZOOM
            )
            mockGoogleMap.animateCamera(any())
        }
    }

    companion object {
        const val DRAWABLE_EXT =
            "me.porge.android.cars.vehicles.layers.presentation.extensions.DrawableExtKt"
    }
}