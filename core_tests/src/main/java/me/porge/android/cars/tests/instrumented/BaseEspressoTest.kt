package me.porge.android.cars.tests.instrumented

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import me.porge.android.cars.tests.instrumented.matchers.DrawableMatcher.withDrawable
import org.hamcrest.CoreMatchers.allOf

abstract class BaseEspressoTest<T : Activity> {

    abstract val activityRule: ActivityTestRule<T>

    fun assertViewDisplayedWithText(@IdRes viewId: Int, text: String) {
        onView(withId(viewId)).check(matches(allOf(isDisplayed(), withText(text))))
    }

    fun assertViewDisplayedWithDrawable(
        @IdRes viewId: Int,
        @DrawableRes drawableId: Int
    ) {
        onView(withId(viewId))
            .check(matches(allOf(isDisplayed(), withDrawable(drawableId))))
    }

    fun runOnActivity(block: T.() -> Unit) {
        with(activityRule.activity) {
            runOnUiThread {
                block()
            }
        }
    }
}