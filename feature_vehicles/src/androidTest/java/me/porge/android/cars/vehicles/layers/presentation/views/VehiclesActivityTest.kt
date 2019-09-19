package me.porge.android.cars.vehicles.layers.presentation.views

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_vehicles.*
import me.porge.android.cars.tests.instrumented.BaseEspressoTest
import me.porge.android.cars.tests.instrumented.rules.KoinActivityRule
import me.porge.android.cars.vehicles.R
import me.porge.android.cars.vehicles.di.featVehiclesModule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@LargeTest
class VehiclesActivityTest : BaseEspressoTest<VehiclesActivity>() {

    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    @get:Rule
    override val activityRule =
        KoinActivityRule(
            VehiclesActivity::class.java,
            modules = listOf(featVehiclesModule)
        )

    @Test
    fun gIVEN_no_info__WHEN_activity_started__THEN_bottom_sheet_is_collapsed() {
        // Given

        // When

        // Then
        with(activityRule.activity) {
            assertEquals(
                BottomSheetBehavior.STATE_COLLAPSED,
                BottomSheetBehavior.from(bottomSheetVehicles).state
            )
        }
    }

    @Test
    fun gIVEN_no_info__WHEN_activity_started__THEN_check_vehicle_map_and_list_fragments_attached() {
        // Given

        // When

        // Then
        onView(withId(R.id.fragmentVehiclesMap)).check(matches(isDisplayed()))
        onView(withId(R.id.fragmentVehiclesList)).check(matches(isDisplayed()))
    }
}