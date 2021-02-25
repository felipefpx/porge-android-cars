package me.porge.android.cars.core.layers.presentation.views.extensions

import android.content.Context
import android.widget.ImageView
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import me.porge.android.cars.core.di.coreArchModule
import me.porge.android.cars.core.layers.presentation.views.imageloader.ImageLoader
import me.porge.android.cars.tests.unit.BaseTest
import me.porge.android.cars.tests.unit.extensions.startKoinForTests
import org.junit.Before
import org.junit.Test

class ImageViewExtKtTest : BaseTest(useMockkAnnotations = true) {

    @MockK
    private lateinit var mockImageLoader: ImageLoader

    @Before
    fun onSetup() {
        startKoinForTests(listOf(coreArchModule)) {
            single {
                mockImageLoader
            }
        }
    }

    @Test
    fun `GIVEN fake image url, WHEN requested to load it, THEN assert success`() {
        // Given
        val mockContext = mockk<Context>()
        val mockImageView = mockk<ImageView> {
            every { context } returns mockContext
        }
        val fakeUrl = "https://www.porge.me/icon.png"
        val fakePlaceholderId = 10

        // When
        mockImageView.loadFromUrl(fakeUrl, fakePlaceholderId)

        // Then
        verify {
            mockImageLoader.loadImageFromUrl(mockImageView, fakeUrl, fakePlaceholderId)
        }
    }
}