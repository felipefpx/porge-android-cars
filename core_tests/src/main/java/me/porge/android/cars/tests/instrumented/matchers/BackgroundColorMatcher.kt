package me.porge.android.cars.tests.instrumented.matchers

import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

class BackgroundColorMatcher(
    @ColorRes private val colorResId: Int
) : BoundedMatcher<View, View>(View::class.java) {

    override fun describeTo(description: Description?) {
        description?.appendText("View background colorResId to be $colorResId")
    }

    override fun matchesSafely(item: View?): Boolean {
        val backgroundColor = item?.background as ColorDrawable
        val colorDrawable = ColorDrawable(ContextCompat.getColor(item.context, colorResId))
        return colorDrawable.color == backgroundColor.color
    }

    companion object {
        fun hasBackgroundColor(@ColorRes color: Int): Matcher<View> = BackgroundColorMatcher(color)
    }
}