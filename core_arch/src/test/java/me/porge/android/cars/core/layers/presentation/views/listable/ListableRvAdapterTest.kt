package me.porge.android.cars.core.layers.presentation.views.listable

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import io.mockk.*
import me.porge.android.cars.tests.fakes.FakeListable
import me.porge.android.cars.tests.fakes.FakeListableBinder
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ListableRvAdapterTest {

    private val mockOnItemClicked: OnListableItemClicked = mockk()

    private val mockView = mockk<View>()
    private val spyListableBinder = spyk(FakeListableBinder(mockView))

    private val mockViewGroup = mockk<ViewGroup> {
        every { context } returns mockk()
    }

    private val mockViewHolder = mockk<ListableRvAdapter.ViewHolder> {
        every { rootView } returns mockView
        every { itemViewType } returns FakeListable.typeIdentifier()
    }

    private val mockkDiffResult: DiffUtil.DiffResult = mockk(relaxUnitFun = true)

    private val adapter by lazy {
        spyk(
            ListableRvAdapter(
                binders = mapOf(FakeListable.typeIdentifier() to spyListableBinder),
                onItemClicked = mockOnItemClicked
            ).apply { data.add(FakeListable) }
        )
    }

    @Before
    fun onSetup() {
        mockkStatic(DiffUtil::class)

        every {
            DiffUtil.calculateDiff(any(), any())
        } returns mockkDiffResult
    }

    @Test
    fun `GIVEN valid view type, WHEN creating view holder, THEN assert view holder`() {
        // Given

        // When
        val result = adapter.onCreateViewHolder(mockViewGroup, FakeListable.typeIdentifier())

        // Then
        assertEquals(mockView, result.rootView)
        verify(exactly = 1) {
            spyListableBinder.buildView(any())
        }
    }

    @Test
    fun `GIVEN invalid view type, WHEN creating view holder, THEN assert view holder`() {
        // Given

        // When
        val result = adapter.onCreateViewHolder(mockViewGroup, -1)

        // Then
        assertNotEquals(mockView, result.rootView)
        verify(exactly = 0) {
            spyListableBinder.buildView(any())
        }
    }

    @Test
    fun `GIVEN fake view holder and valid position, WHEN binding view holder, THEN assert bind`() {
        // Given

        // When
        adapter.onBindViewHolder(mockViewHolder, 0)

        // Then
        verify(exactly = 1) {
            spyListableBinder.safeBind(mockView, FakeListable, mockOnItemClicked)
        }
    }

    @Test
    fun `GIVEN fake view holder and invalid position, WHEN binding view holder, THEN assert nothing`() {
        // Given

        // When
        adapter.onBindViewHolder(mockViewHolder, -1)

        // Then
        verify(exactly = 0) {
            spyListableBinder.safeBind(mockView, FakeListable, mockOnItemClicked)
        }
    }

    @Test
    fun `GIVEN fake data set and valid position, WHEN getting item view type, THEN assert success`() {
        // Given

        // When
        val result = adapter.getItemViewType(0)

        // Then
        assertEquals(FakeListable.typeIdentifier(), result)
    }

    @Test
    fun `GIVEN fake data set and invalid position, WHEN getting item view type, THEN assert invalid`() {
        // Given

        // When
        val result = adapter.getItemViewType(5)

        // Then
        assertEquals(-1, result)
    }

    @Test
    fun `GIVEN fake data set, WHEN getting item count, THEN assert one`() {
        // Given

        // When
        val result = adapter.itemCount

        // Then
        assertEquals(1, result)
    }

    @Test
    fun `GIVEN fake items, WHEN setting in adapter, THEN assert items set`() {
        // Given
        adapter.data.apply {
            clear()

            object : Listable {
                override fun typeIdentifier(): Int = FakeListable.typeIdentifier() - 1
                override fun isSameItem(otherListable: Listable?) = this === otherListable
            }.let(::add)
        }

        val fakeItems = listOf(FakeListable, FakeListable)
        val diffCallbackSlot = slot<ListableDiffCallback>()

        val oldData = adapter.data.toList()

        // When
        adapter.setItems(fakeItems)

        // Then
        val newData = adapter.data
        assertEquals(2, newData.size)
        assertEquals(fakeItems[0], newData[0])
        assertEquals(fakeItems[1], newData[1])

        verify(exactly = 1) {
            DiffUtil.calculateDiff(capture(diffCallbackSlot), true)
            mockkDiffResult.dispatchUpdatesTo(adapter)
        }

        val diffCallback = diffCallbackSlot.captured
        assertArrayEquals(oldData.toTypedArray(), diffCallback.oldList.toTypedArray())
        assertArrayEquals(newData.toTypedArray(), diffCallback.newList.toTypedArray())
    }
}