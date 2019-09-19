package me.porge.android.cars.vehicles.layers.presentation.binders

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import me.porge.android.cars.tests.instrumented.BaseEspressoTest
import me.porge.android.cars.tests.instrumented.FakeTestActivity
import me.porge.android.cars.tests.instrumented.koin.testImageLoader
import me.porge.android.cars.tests.instrumented.matchers.BackgroundColorMatcher.Companion.hasBackgroundColor
import me.porge.android.cars.tests.instrumented.matchers.DrawableMatcher.withDrawable
import me.porge.android.cars.tests.instrumented.rules.KoinActivityRule
import me.porge.android.cars.vehicles.di.featVehiclesModule
import me.porge.android.cars.vehicles.test.R
import me.porge.android.cars.vehicles.tests.createFakeVehicle
import org.hamcrest.core.AllOf.allOf
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


@LargeTest
class VehicleBinderTest : BaseEspressoTest<FakeTestActivity>() {

    @get:Rule
    override val activityRule =
        KoinActivityRule(
            FakeTestActivity::class.java,
            modules = listOf(featVehiclesModule)
        )

    @Before
    fun onSetup() {
        testImageLoader.successDrawableResId = R.drawable.ic_vehicle_fake_image
    }

    @Test
    fun gIVEN_selected_vehicle_with_img__WHEN_create_view_bind_vehicle_requested__THEN_assert_success() {
        // Given
        val vehicle = createFakeVehicle(1).copy(isSelected = true)
        val countDownLatch = CountDownLatch(1)
        testImageLoader.successUrl = vehicle.carImageUrl!!

        // When
        activityRule.activity.buildUi { container ->
            val view = VehicleBinder.buildView(container).also(container::addView)
            VehicleBinder.safeBind(view, vehicle) { countDownLatch.countDown() }
            view.performClick()
        }

        // Then
        countDownLatch.await(1, TimeUnit.SECONDS)
        assertEquals(0, countDownLatch.count)

        assertViewDisplayedWithText(
            viewId = R.id.textVehicleItemTitle,
            text = String.format("%s (%s)", vehicle.modelName, vehicle.licensePlate)
        )

        assertViewDisplayedWithText(
            viewId = R.id.textVehicleItemSubtitle,
            text = String.format("Driver: %s", vehicle.name)
        )

        onView(withId(R.id.constraintVehicleItemRootView))
            .check(matches(allOf(isDisplayed(), hasBackgroundColor(R.color.orange))))

        onView(withId(R.id.textVehicleItemTitle))
            .check(matches(allOf(isDisplayed(), hasTextColor(android.R.color.black))))

        onView(withId(R.id.textVehicleItemSubtitle))
            .check(matches(allOf(isDisplayed(), hasTextColor(android.R.color.black))))

        onView(withId(R.id.imageVehicleItemIcon))
            .check(matches(withDrawable(R.drawable.ic_vehicle_fake_image)))
    }

    @Test
    fun gIVEN_not_selected_vehicle__WHEN_create_view_and_bind_vehicle_requested__THEN_assert_success() {
        // Given
        val vehicle = createFakeVehicle(1).copy(isSelected = false)
        val countDownLatch = CountDownLatch(1)
        testImageLoader.successUrl = ""
        // When
        activityRule.activity.buildUi { container ->
            val view = VehicleBinder.buildView(container).also(container::addView)
            VehicleBinder.safeBind(view, vehicle) { countDownLatch.countDown() }
            view.performClick()
        }

        // Then
        countDownLatch.await(1, TimeUnit.SECONDS)
        assertEquals(0, countDownLatch.count)

        assertViewDisplayedWithText(
            viewId = R.id.textVehicleItemTitle,
            text = String.format("%s (%s)", vehicle.modelName, vehicle.licensePlate)
        )

        assertViewDisplayedWithText(
            viewId = R.id.textVehicleItemSubtitle,
            text = String.format("Driver: %s", vehicle.name)
        )

        onView(withId(R.id.constraintVehicleItemRootView))
            .check(matches(allOf(isDisplayed(), hasBackgroundColor(android.R.color.white))))

        onView(withId(R.id.textVehicleItemTitle))
            .check(matches(allOf(isDisplayed(), hasTextColor(R.color.orange))))

        onView(withId(R.id.textVehicleItemSubtitle))
            .check(matches(allOf(isDisplayed(), hasTextColor(R.color.gray_dark))))

        onView(withId(R.id.imageVehicleItemIcon))
            .check(matches(withDrawable(R.drawable.ic_vehicle_placeholder)))
    }
}