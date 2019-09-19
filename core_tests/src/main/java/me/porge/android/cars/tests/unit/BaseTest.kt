package me.porge.android.cars.tests.unit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.mockk.spyk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.test.AutoCloseKoinTest
open class BaseTest(
    private val useMockkAnnotations: Boolean = false
) : AutoCloseKoinTest() {

    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    @get:Rule
    @ExperimentalCoroutinesApi
    val coroutinesRule = TestCoroutineRule()

    @ExperimentalCoroutinesApi
    val testDispatcherProvider =
        spyk(TestDispatcherProvider(coroutinesRule.dispatcher))


    @Before
    fun onSetupBaseTest() {
        if (useMockkAnnotations)
            MockKAnnotations.init(this, relaxUnitFun = true)


        onStartKoin()
    }

    open fun onStartKoin() {
    }

    @After
    @ExperimentalCoroutinesApi
    fun destroyMocks() {
        testDispatcherProvider.dispatcher.cancel()
        unmockkAll()
    }
}