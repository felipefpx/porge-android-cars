package me.porge.android.cars.vehicles.layers.presentation.views

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModel
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import kotlinx.android.synthetic.main.activity_vehicles.*
import kotlinx.android.synthetic.main.fragment_vehicle_list.*
import me.porge.android.cars.core.layers.presentation.views.listable.ListableRvAdapter
import me.porge.android.cars.tests.instrumented.BaseEspressoTest
import me.porge.android.cars.tests.instrumented.koin.testImageLoader
import me.porge.android.cars.tests.instrumented.matchers.RecyclerViewMatcher.withRecyclerView
import me.porge.android.cars.tests.instrumented.rules.KoinActivityRule
import me.porge.android.cars.tests.unit.extensions.invokeHiddenMethod
import me.porge.android.cars.vehicles.di.featVehiclesModule
import me.porge.android.cars.vehicles.layers.domain.entities.Vehicle
import me.porge.android.cars.vehicles.layers.presentation.VehiclesViewState
import me.porge.android.cars.vehicles.test.R
import me.porge.android.cars.vehicles.tests.createFakeVehicle
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@LargeTest
class VehicleListFragmentTest : BaseEspressoTest<VehiclesActivity>() {

    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    @get:Rule
    override val activityRule =
        KoinActivityRule(
            VehiclesActivity::class.java,
            modules = listOf(featVehiclesModule)
        )

    private val fragmentVehicleList
        get() = activityRule.activity.fragmentVehiclesList as VehicleListFragment

    @Before
    fun onSetup() {
        testImageLoader.successDrawableResId = R.drawable.ic_vehicle_fake_image
    }

    @Test
    fun gIVEN_no_info__WHEN_bind_empty_vehicle_view_state__THEN_assert_it() {
        // Given
        resetViewState()

        // When
        runOnActivity { fragmentVehicleList.bind(VehiclesViewState.Empty) }

        // Then
        onView(withId(R.id.progressBarVehicleList)).check(matches(not(isDisplayed())))
        assertVehicleList(emptyList())
    }

    @Test
    fun gIVEN_empty_vehicle_list__WHEN_bind_loading_vehicle_view_state__THEN_assert_it() {
        // Given
        resetViewState()

        // When
        runOnActivity { fragmentVehicleList.bind(VehiclesViewState.Loading()) }

        // Then
        onView(withId(R.id.progressBarVehicleList)).check(matches(isDisplayed()))
        assertVehicleList(emptyList())
    }

    @Test
    fun gIVEN_fake_vehicles__WHEN_bind_error_view_state__THEN_assert_vehicles() {
        // Given
        resetViewState()
        val fakeVehicleList = (1..10).map { createFakeVehicle(it) }

        // When
        runOnActivity {
            fragmentVehicleList.apply {
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
        onView(withId(R.id.progressBarVehicleList)).check(matches(not(isDisplayed())))
        assertVehicleList(fakeVehicleList)
    }

    @Test
    fun gIVEN_fake_vehicle_list__WHEN_bind_loading_view_state__THEN_assert_it() {
        // Given
        resetViewState()
        val fakeVehicleList = (1..10).map { createFakeVehicle(it) }

        // When
        runOnActivity { fragmentVehicleList.bind(VehiclesViewState.Loading(fakeVehicleList)) }

        // Then
        onView(withId(R.id.progressBarVehicleList)).check(matches(isDisplayed()))
        assertVehicleList(fakeVehicleList)
    }

    @Test
    fun gIVEN_fake_vehicle_list__WHEN_bind_vehicles_view_state__THEN_assert_it() {
        // Given
        resetViewState()
        val fakeVehicleList = (1..10).map { createFakeVehicle(it) }

        // When
        runOnActivity { fragmentVehicleList.bind(VehiclesViewState.Vehicles(fakeVehicleList)) }

        // Then
        onView(withId(R.id.progressBarVehicleList)).check(matches(not(isDisplayed())))
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
            fragmentVehicleList.bind(
                VehiclesViewState.VehicleFocused(fakeVehicleList, focusedFakeVehicle)
            )
        }

        // Then
        onView(withId(R.id.progressBarVehicleList)).check(matches(not(isDisplayed())))
        assertVehicleList(fakeVehicleList)
    }

    private fun assertVehicleList(vehicleList: List<Vehicle>) {
        assertEquals(
            vehicleList.size,
            fragmentVehicleList.recyclerVehicleList.adapter!!.itemCount
        )
        vehicleList.forEachIndexed { index, vehicle ->
            onView(withId(R.id.recyclerVehicleList))
                .perform(scrollToPosition<ListableRvAdapter.ViewHolder>(index))

            onView(
                withRecyclerView(R.id.recyclerVehicleList)
                    .atPositionOnView(index, R.id.textVehicleItemTitle)
            ).check(
                matches(withText(String.format("%s (%s)", vehicle.modelName, vehicle.licensePlate)))
            )

            onView(
                withRecyclerView(R.id.recyclerVehicleList)
                    .atPositionOnView(index, R.id.textVehicleItemSubtitle)
            ).check(matches(withText(String.format("Driver: %s", vehicle.name))))
        }
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