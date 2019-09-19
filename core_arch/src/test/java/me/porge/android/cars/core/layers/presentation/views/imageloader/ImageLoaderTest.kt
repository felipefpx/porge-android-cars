package me.porge.android.cars.core.layers.presentation.views.imageloader

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import me.porge.android.cars.tests.unit.BaseTest
import org.junit.Before
import org.junit.Test

class ImageLoaderTest : BaseTest(useMockkAnnotations = true) {

    @MockK
    private lateinit var mockRequestManager: RequestManager

    @MockK
    private lateinit var mockRequestBuilder: RequestBuilder<Drawable>

    private val imageLoader = ImageLoader()

    @Before
    fun onSetup() {
        mockkStatic(Glide::class)
        every {
            Glide.with(any<Context>())
        } returns mockRequestManager

        every {
            mockRequestManager.load(any<String>())
        } returns mockRequestBuilder

        every {
            mockRequestBuilder.centerCrop()
        } returns mockRequestBuilder

        every {
            mockRequestBuilder.placeholder(any<Int>())
        } returns mockRequestBuilder

        every {
            mockRequestBuilder.into(any())
        } returns mockk()
    }

    @Test
    fun `GIVEN fake image url, WHEN requested to load it, THEN assert success`() {
        // Given
        val mockContext = mockk<Context>()
        val mockImageView = mockk<ImageView> {
            every { context } returns mockContext
        }
        val fakeUrl = "http://www.porge.me/icon.png"
        val fakePlaceholderId = 10

        // When
        imageLoader.loadImageFromUrl(mockImageView, fakeUrl, fakePlaceholderId)

        // Then
        verify {
            mockImageView.context
            Glide.with(mockContext)
            mockRequestManager.load(fakeUrl)
            with(mockRequestBuilder) {
                centerCrop()
                placeholder(fakePlaceholderId)
                into(mockImageView)
            }
        }
    }
}