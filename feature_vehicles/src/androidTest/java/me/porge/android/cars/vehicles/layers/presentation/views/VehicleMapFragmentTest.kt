package me.porge.android.cars.vehicles.layers.presentation.views

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModel
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import kotlinx.android.synthetic.main.activity_vehicles.*
import me.porge.android.cars.tests.instrumented.BaseEspressoTest
import me.porge.android.cars.tests.instrumented.koin.countingIdlingResource
import me.porge.android.cars.tests.instrumented.rules.KoinActivityRule
import me.porge.android.cars.tests.unit.extensions.invokeHiddenMethod
import me.porge.android.cars.vehicles.di.featVehiclesModule
import me.porge.android.cars.vehicles.layers.domain.entities.Vehicle
import me.porge.android.cars.vehicles.layers.presentation.VehiclesViewState
import me.porge.android.cars.vehicles.test.R
import me.porge.android.cars.vehicles.tests.createFakeVehicle
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest


@LargeTest
class VehicleMapFragmentTest : BaseEspressoTest<VehiclesActivity>(), KoinTest {

    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    @get:Rule
    override val activityRule =
        KoinActivityRule(
            VehiclesActivity::class.java,
            modules = listOf(featVehiclesModule)
        )

    private val fragmentVehicleMap
        get() = activityRule.activity.fragmentVehiclesMap as VehicleMapFragment

    @Before
    fun onSetup() {
        IdlingRegistry.getInstance().register(countingIdlingResource)
    }

    @After
    fun onDestroy() {
        IdlingRegistry.getInstance().unregister(countingIdlingResource)
    }

    @Test
    fun gIVEN_no_info__WHEN_bind_empty_vehicle_view_state__THEN_assert_it() {
        // Given
        resetViewState()

        // When
        runOnActivity { viewModel.setViewState(VehiclesViewState.Empty) }

        // Then
        assertVehicleList(emptyList())
    }

    @Test
    fun gIVEN_empty_vehicle_list__WHEN_bind_loading_vehicle_view_state__THEN_assert_it() {
        // Given
        resetViewState()

        // When
        runOnActivity {
            viewModel.setViewState(VehiclesViewState.Loading())
        }

        // Then
        assertVehicleList(emptyList())
    }

    @Test
    fun gIVEN_fake_vehicles__WHEN_bind_error_view_state__THEN_assert_vehicles() {
        // Given
        resetViewState()
        val errorText = "An error occurred. Please, check your Internet connection and try again…"
        val errorActionText = "OK"
        val fakeVehicleList = (1..10).map { createFakeVehicle(it) }

        runOnActivity {
            // When
            fragmentVehicleMap.apply {
                bind(VehiclesViewState.Vehicles(fakeVehicleList))

                bind(
                    VehiclesViewState.Error(
                        R.string.vehicles_error,
                        R.string.vehicles_error_action
                    )
                )
            }
        }

        // Then
        assertVehicleList(fakeVehicleList)

        onView(withText(errorText))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))

        onView(withText(errorActionText))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
            .perform(click())
    }

    @Test
    fun gIVEN_fake_vehicle_list__WHEN_bind_loading_view_state__THEN_assert_it() {
        // Given
        resetViewState()
        val fakeVehicleList = (1..10).map { createFakeVehicle(it) }

        // When
        runOnActivity { viewModel.setViewState(VehiclesViewState.Loading(fakeVehicleList)) }

        // Then
        assertVehicleList(fakeVehicleList)
    }

    @Test
    fun gIVEN_fake_vehicle_list__WHEN_bind_vehicles_view_state__THEN_assert_it() {
        // Given
        resetViewState()
        val fakeVehicleList = (1..10).map { createFakeVehicle(it) }

        // When
        runOnActivity { viewModel.setViewState(VehiclesViewState.Vehicles(fakeVehicleList)) }

        // Then
        assertVehicleList(fakeVehicleList)
    }

    @Test
    fun gIVEN_fake_vehicle_list__WHEN_bind_vehicle_focused_view_state__THEN_assert_it() {
        // Given
        resetViewState()
        val fakeVehicleList = (1..10).map { createFakeVehicle(it) }
        val focusedFakeVehicle = fakeVehicleList[3]

        // When
        runOnActivity {
            viewModel.setViewState(
                VehiclesViewState.VehicleFocused(fakeVehicleList, focusedFakeVehicle)
            )
        }

        // Then
        assertVehicleList(fakeVehicleList)
    }

    private fun assertVehicleList(vehicleList: List<Vehicle>) {
        Espresso.onIdle()
        assertNotNull(fragmentVehicleMap.mapController)
        assertTrue(
            fragmentVehicleMap.mapController!!.markers.keys.none { vehicle ->
                vehicleList.none { vehicle.id == it.id }
            }
        )
    }

    private fun resetViewState() {
        runOnActivity {
            viewModel.also {
                it.onResetViewState()
                invokeHiddenMethod<ViewModel>(viewModel, "onCleared")
            }
        }
    }
}