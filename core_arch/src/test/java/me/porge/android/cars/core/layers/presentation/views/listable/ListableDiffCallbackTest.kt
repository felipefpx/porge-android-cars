package me.porge.android.cars.core.layers.presentation.views.listable

import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.max

class ListableDiffCallbackTest {

    @Test
    fun `GIVEN fake lists, WHEN checked list sizes, THEN assert success`() {
        // Given
        val oldList = listOf(
            Dog("Bob", 1),
            Dog("Mike", 2)
        )
        val newList = listOf(
            Dog("Bobs", 10)
        )

        val diffCallback = ListableDiffCallback(oldList = oldList, newList = newList)

        // When / Then
        assertEquals(oldList.size, diffCallback.oldListSize)
        assertEquals(newList.size, diffCallback.newListSize)
    }

    @Test
    fun `GIVEN fake lists, WHEN checking items are same, THEN assert success`() {
        // Given
        val oldList = listOf(
            Dog("Bob", 1),
            Dog("Mike", 2)
        )
        val newList = listOf(
            Dog("Bobs", 10),
            Dog("Mike", 2),
            Dog("Bob", 1)
        )

        val diffCallback = ListableDiffCallback(oldList = oldList, newList = newList)
        val maxSize = max(oldList.size, newList.size)

        // When / Then
        val expectedResult = arrayOf(false, true, false)
        val results = (0 until maxSize).map { diffCallback.areItemsTheSame(it, it) }.toTypedArray()

        assertArrayEquals(expectedResult, results)
    }

    @Test
    fun `GIVEN fake lists, WHEN checking contents are same, THEN assert success`() {
        // Given
        val oldList = listOf(
            Dog("Bob", 1),
            Dog("Mike", 2)
        )
        val newList = listOf(
            Dog("Bob", 10),
            Dog("Mike", 2),
            Dog("Larry", 3)
        )

        val diffCallback = ListableDiffCallback(oldList = oldList, newList = newList)
        val maxSize = max(oldList.size, newList.size)

        // When / Then
        val expectedResult = arrayOf(false, true, false)
        val results = (0 until maxSize).map { diffCallback.areContentsTheSame(it, it) }.toTypedArray()

        assertArrayEquals(expectedResult, results)
    }

    data class Dog(
        val name: String,
        val age: Int
    ) : Listable {
        override fun typeIdentifier(): Int = 1
        override fun isSameItem(otherListable: Listable?): Boolean =
            (otherListable as? Dog)?.name == name
    }
}