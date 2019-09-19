package me.porge.android.cars.vehicles.layers.data

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import me.porge.android.cars.tests.unit.BaseTest
import me.porge.android.cars.vehicles.layers.data.remote.VehiclesRemoteSource
import me.porge.android.cars.vehicles.tests.createFakeVehicle
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class VehiclesRepositoryTest : BaseTest() {

    private val mockVehiclesRemoteSource =
        mockk<VehiclesRemoteSource> { coEvery { getVehicles() } returns FAKE_VEHICLES }

    private val repository = VehiclesRepository(
        testDispatcherProvider,
        mockVehiclesRemoteSource
    )

    @Test
    fun `GIVEN no info, WHEN vehicles in region requested, THEN assert request`() {
        runBlocking {
            // Given

            // When
            val result = repository.getVehicles()

            // Then
            assertEquals(FAKE_VEHICLES, result)

            coVerify(exactly = 1) {
                testDispatcherProvider.io
                mockVehiclesRemoteSource.getVehicles()
            }
        }
    }

    companion object {
        private val FAKE_VEHICLES = (1..5).map { createFakeVehicle(it) }
    }
}