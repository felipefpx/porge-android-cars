package me.porge.android.cars.core.layers.presentation.views.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import me.porge.android.cars.core.layers.presentation.viewstates.BaseViewState
import me.porge.android.cars.tests.fakes.FakeView
import me.porge.android.cars.tests.fakes.FakeViewState
import org.junit.Rule
import org.junit.Test

class BaseViewTest {
    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    @Test
    fun `GIVEN fake view, WHEN observing view state, THEN assert observer`() {
        // Given
        val fakeView = spyk<FakeView>()
        val viewStateLiveData: MutableLiveData<BaseViewState> = mockk(relaxUnitFun = true)

        // When
        val observer =
            with(fakeView) { viewStateLiveData.observeViewState() }

        // Then
        viewStateLiveData.postValue(FakeViewState)
        observer.onChanged(FakeViewState)

        verify(exactly = 1) {
            viewStateLiveData.observe(fakeView, observer)
            fakeView.bind(FakeViewState)
        }
    }
}