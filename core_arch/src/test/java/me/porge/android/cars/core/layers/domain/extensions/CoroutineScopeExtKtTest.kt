package me.porge.android.cars.core.layers.domain.extensions

import io.mockk.Called
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import me.porge.android.cars.tests.unit.BaseTest
import org.junit.Test

class CoroutineScopeExtKtTest : BaseTest() {

    @ExperimentalCoroutinesApi
    @Test
    fun `GIVEN fake request block, WHEN launched request, THEN assert success`() {
        // Given
        val result = "Test"
        val requestBlock = suspend { result }
        val onSuccess = spyk({ _: String -> })
        val onFailure = spyk({ _: Throwable -> })

        // When
        runBlocking {
            launchRequest(
                requestBlock = requestBlock,
                onSuccess = onSuccess,
                onFailure = onFailure
            )
        }

        // Then
        verify(exactly = 1) {
            onSuccess(result)
            onFailure wasNot Called
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `GIVEN fake request block, WHEN launched request, THEN assert failure`() {
        // Given
        val error: Throwable = NullPointerException()
        val requestBlock = suspend { throw error }
        val onSuccess = spyk({ _: String -> })
        val onFailure = spyk({ _: Throwable -> })

        // When
        runBlocking {
            launchRequest(
                requestBlock = requestBlock,
                onSuccess = onSuccess,
                onFailure = onFailure
            )
        }

        // Then
        verify(exactly = 1) {
            onSuccess wasNot Called
            onFailure(error)
        }
    }
}