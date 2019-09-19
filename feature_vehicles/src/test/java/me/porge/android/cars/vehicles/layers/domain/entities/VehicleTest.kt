package me.porge.android.cars.vehicles.layers.domain.entities

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class VehicleTest {

    @Test
    fun `GIVEN vehicles with same id, WHEN asserting they are same, THEN assert true`() {
        // Given
        val vehicle1 = Vehicle(
            id = "abc",
            modelName = "Model",
            name = "Name",
            licensePlate = "Plate",
            latitude = 12.123,
            longitude = 15.123
        )
        val vehicle2 = Vehicle(
            id = vehicle1.id,
            modelName = "Model",
            name = "Name",
            licensePlate = "Plate",
            latitude = 12.123,
            longitude = 15.123
        )

        // When / Then
        assertTrue(vehicle1.isSameItem(vehicle2))
    }

    @Test
    fun `GIVEN vehicles with different ids, WHEN asserting they are same, THEN assert true`() {
        // Given
        val vehicle1 = Vehicle(
            id = "abc",
            modelName = "Model",
            name = "Name",
            licensePlate = "Plate",
            latitude = 12.123,
            longitude = 15.123
        )
        val vehicle2 = Vehicle(
            id = vehicle1.id + "1a",
            modelName = "Model",
            name = "Name",
            licensePlate = "Plate",
            latitude = 12.123,
            longitude = 15.123
        )

        // When / Then
        assertFalse(vehicle1.isSameItem(vehicle2))
    }
}