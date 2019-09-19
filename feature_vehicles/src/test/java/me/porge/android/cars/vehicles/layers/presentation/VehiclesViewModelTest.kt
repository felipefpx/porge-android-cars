package me.porge.android.cars.vehicles.layers.presentation

import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.porge.android.cars.core.DispatcherProvider
import me.porge.android.cars.core.di.coreArchModule
import me.porge.android.cars.tests.unit.BaseTest
import me.porge.android.cars.tests.unit.TestDispatcherProvider
import me.porge.android.cars.tests.unit.extensions.blockingObserve
import me.porge.android.cars.tests.unit.extensions.startKoinForTests
import me.porge.android.cars.vehicles.R
import me.porge.android.cars.vehicles.di.featVehiclesModule
import me.porge.android.cars.vehicles.layers.domain.VehiclesUseCases
import me.porge.android.cars.vehicles.tests.createFakeVehicle
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class VehiclesViewModelTest : BaseTest() {

    private val mockVehiclesUseCases: VehiclesUseCases = mockk {
        coEvery { getVehicles() } returns FAKE_VEHICLES
    }

    private val viewModel by lazy {
        spyk(VehiclesViewModel(mockVehiclesUseCases))
    }

    override fun onStartKoin() {
        startKoinForTests(listOf(coreArchModule, featVehiclesModule)) {
            single {
                TestDispatcherProvider(coroutinesRule.dispatcher) as DispatcherProvider
            }
        }
    }

    @After
    fun afterTestCase() {
        viewModel.onResetViewState()
    }

    @Test
    fun `GIVEN view model with no changes, WHEN getting default view state, THEN assert empty`() {
        // Given

        // When
        val currState = viewModel.getLiveViewState().blockingObserve()

        // Then
        assertEquals(VehiclesViewState.Empty, currState)
    }

    @Test
    fun `GIVEN vehicle list, WHEN map moved, THEN assert vehicles view state`() {
        checkInfoLoadingAfterMapIsReady()

        // Other assertions
        val vehicles =
            (viewModel.getLiveViewState().blockingObserve() as VehiclesViewState.Vehicles).vehicles

        assertEquals(FAKE_VEHICLES, vehicles)
    }

    @Test
    fun `GIVEN fake invalid vehicle, WHEN selected vehicle, THEN assert view state`() {
        // Given
        val vehicle = createFakeVehicle(1)

        // When
        viewModel.onVehicleSelected(vehicle)

        // Then
        verify(exactly = 0) {
            viewModel.setViewState(any())
        }
    }

    @Test
    fun `GIVEN fake vehicles, WHEN selected valid vehicle, THEN assert view state`() {
        // Given
        val viewStateSlot = slot<VehiclesViewState>()
        val vehicles = (1..5).map { createFakeVehicle(it) }
        val selectedVehicle = vehicles[2]
        val initialState = VehiclesViewState.Vehicles(listOf(selectedVehicle))
        viewModel.setViewState(initialState)
        excludeRecords { viewModel.setViewState(initialState) }

        // When
        viewModel.onVehicleSelected(selectedVehicle)

        // Then
        verify(exactly = 1) {
            viewModel.setViewState(capture(viewStateSlot))
        }

        val vehicleFocusedViewState = viewStateSlot.captured as VehiclesViewState.VehicleFocused
        val updatedSelectedVehicle = selectedVehicle.copy(isSelected = true)
        assertEquals(
            updatedSelectedVehicle,
            vehicleFocusedViewState.selectedVehicle
        )

        assertEquals(
            updatedSelectedVehicle,
            vehicleFocusedViewState.vehicles.first()
        )

        assertEquals(
            initialState.vehicles.map { it.copy(isSelected = it.id == updatedSelectedVehicle.id) },
            vehicleFocusedViewState.vehicles
        )
    }


    @Test
    fun `GIVEN no vehicles, WHEN map moved, THEN assert empty view state`() {
        // Given
        coEvery {
            mockVehiclesUseCases.getVehicles()
        } returns emptyList()

        checkInfoLoadingAfterMapIsReady()

        // Other assertions
        assertEquals(VehiclesViewState.Empty, viewModel.getLiveViewState().blockingObserve())
    }


    @Test
    fun `GIVEN error, WHEN map moved, THEN assert error view state`() {
        // Given
        val expectedError = NullPointerException()
        coEvery {
            mockVehiclesUseCases.getVehicles()
        } throws expectedError

        checkInfoLoadingAfterMapIsReady()

        // Other assertions
        val errorMsgId =
            (viewModel.getLiveViewState().blockingObserve() as VehiclesViewState.Error).errorMsgResId

        assertEquals(R.string.vehicles_error, errorMsgId)
    }

    private fun checkInfoLoadingAfterMapIsReady() {
        // Given
        val newStateSlot = slot<VehiclesViewState>()
        val fakeInitialVehicles = listOf(createFakeVehicle(1))
        viewModel.setViewState(VehiclesViewState.Vehicles(fakeInitialVehicles))
        coroutinesRule.pause()

        // When
        viewModel.onMapReady()

        // Then
        verify {
            viewModel.setViewState(capture(newStateSlot))
        }

        assertEquals(
            fakeInitialVehicles,
            (newStateSlot.captured as VehiclesViewState.Loading).vehicles
        )

        coroutinesRule.resume()
        coVerify {
            mockVehiclesUseCases.getVehicles()
        }
    }

    companion object {
        private val FAKE_VEHICLES = (0..5).map { createFakeVehicle(it) }
    }
}