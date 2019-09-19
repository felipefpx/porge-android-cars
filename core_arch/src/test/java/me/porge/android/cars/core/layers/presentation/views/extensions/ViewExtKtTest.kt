package me.porge.android.cars.core.layers.presentation.views.extensions

import android.view.View
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import me.porge.android.cars.tests.unit.BaseTest
import org.junit.Before
import org.junit.Test

class ViewExtKtTest : BaseTest(useMockkAnnotations = true) {

    @MockK
    private lateinit var mockView: View

    @Before
    fun onSetup() {
        every {
            mockView.visibility
        } returns View.VISIBLE
    }

    @Test
    fun `GIVEN a view, WHEN making it gone, THEN assert gone`() {
        // Given

        // When
        mockView.makeGoneIf(true)

        // Then
        verify(exactly = 1) {
            mockView.visibility = View.GONE
        }
    }

    @Test
    fun `GIVEN a view, WHEN making it not gone, THEN assert gone`() {
        // Given

        // When
        mockView.makeGoneIf(false)

        // Then
        verify(exactly = 1) {
            mockView.visibility = View.VISIBLE
        }
    }

    @Test
    fun `GIVEN a view, WHEN making it invisible, THEN assert gone`() {
        // Given

        // When
        mockView.makeInvisibleIf(true)

        // Then
        verify(exactly = 1) {
            mockView.visibility = View.INVISIBLE
        }
    }

    @Test
    fun `GIVEN a view, WHEN making it not invisible, THEN assert gone`() {
        // Given

        // When
        mockView.makeInvisibleIf(false)

        // Then
        verify(exactly = 1) {
            mockView.visibility = View.VISIBLE
        }
    }

    @Test
    fun `GIVEN a view, WHEN making it visible, THEN assert gone`() {
        // Given

        // When
        mockView.makeVisibleIf(true)

        // Then
        verify(exactly = 1) {
            mockView.visibility = View.VISIBLE
        }
    }

    @Test
    fun `GIVEN a view, WHEN making it not visible, THEN assert gone`() {
        // Given

        // When
        mockView.makeVisibleIf(false)

        // Then
        verify(exactly = 1) {
            mockView.visibility = View.GONE
        }
    }
}