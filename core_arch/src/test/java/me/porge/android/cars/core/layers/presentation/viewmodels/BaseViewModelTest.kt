package me.porge.android.cars.core.layers.presentation.viewmodels

import me.porge.android.cars.core.di.coreArchModule
import me.porge.android.cars.tests.fakes.FakeDefaultViewState
import me.porge.android.cars.tests.fakes.FakeViewState
import me.porge.android.cars.tests.fakes.createFakeViewModel
import me.porge.android.cars.tests.unit.BaseTest
import me.porge.android.cars.tests.unit.extensions.blockingObserve
import me.porge.android.cars.tests.unit.extensions.startKoinForTests
import org.junit.Assert.assertEquals
import org.junit.Test

class BaseViewModelTest : BaseTest() {

    override fun onStartKoin() {
        startKoinForTests(listOf(coreArchModule))
    }

    @Test
    fun `GIVEN view model with no changes, WHEN getting live view state, THEN assert default`() {
        // Given
        val viewModel = createFakeViewModel()

        // When
        val result = viewModel.getLiveViewState().blockingObserve()

        // Then
        assertEquals(FakeDefaultViewState, result)
    }

    @Test
    fun `GIVEN fake view state, WHEN setting new view state, THEN assert state`() {
        // Given
        val viewModel = createFakeViewModel()

        // When
        viewModel.setViewState(FakeViewState)

        // Then
        val result = viewModel.getLiveViewState().blockingObserve()
        assertEquals(FakeViewState, result)
    }

    @Test
    fun `GIVEN fake view state, WHEN reset view state, THEN assert default state`() {
        // Given
        val viewModel = createFakeViewModel().apply { setViewState(FakeViewState) }

        // When
        viewModel.onResetViewState()

        // Then
        val result = viewModel.getLiveViewState().blockingObserve()
        assertEquals(FakeDefaultViewState, result)
    }
}