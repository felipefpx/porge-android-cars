package me.porge.android.cars.vehicles.layers.presentation.extensions

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import io.mockk.*
import me.porge.android.cars.tests.unit.BaseTest
import org.junit.Test

class DrawableExtKtTest : BaseTest() {

    @Test
    fun `GIVEN mock drawable, WHEN converting it to bitmap descriptor, THEN assert success`() {
        // Given
        val fakeWidth = 1
        val fakeHeight = 2
        val canvasSlot = slot<Canvas>()
        val mockBitmap = mockk<Bitmap>()
        val mockBitmapDescriptor = mockk<BitmapDescriptor>()

        val mockDrawable = mockk<Drawable>(relaxUnitFun = true) {
            every { intrinsicWidth } returns fakeWidth
            every { intrinsicHeight } returns fakeHeight
        }

        mockkConstructor(Canvas::class)

        mockkStatic(Bitmap::class)
        every { Bitmap.createBitmap(any(), any(), any()) } returns mockBitmap

        mockkStatic(BitmapDescriptorFactory::class)
        every { BitmapDescriptorFactory.fromBitmap(any()) } returns mockBitmapDescriptor

        // When
        mockDrawable.toBitmapDescriptor()

        // Then
        verify(exactly = 1) {
            mockDrawable.setBounds(0, 0, fakeWidth, fakeHeight)
            Bitmap.createBitmap(fakeWidth, fakeHeight, Bitmap.Config.ARGB_8888)
            mockDrawable.draw(capture(canvasSlot))
            BitmapDescriptorFactory.fromBitmap(mockBitmap)
        }
    }
}