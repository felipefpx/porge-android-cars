package me.porge.android.cars.core.layers.presentation.views.extensions

import android.text.Html
import android.text.Spanned
import android.widget.TextView
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import me.porge.android.cars.tests.unit.BaseTest
import org.junit.Before
import org.junit.Test

class TextViewExtKtTest : BaseTest(useMockkAnnotations = true) {

    @MockK
    private lateinit var spanned: Spanned

    @Before
    fun onSetup() {
        mockkStatic(Html::class)

        every {
            Html.fromHtml(any(), any())
        } returns spanned
    }

    @Test
    fun `GIVEN fake text view and html, WHEN setting html text, THEN assert success`() {
        // Given
        val mockTextView = mockk<TextView>(relaxUnitFun = true)
        val fakeHtml = "<b>Test</b>"

        // When
        mockTextView.setHtmlText(fakeHtml)

        // Then
        verify {
            Html.fromHtml(any(), Html.FROM_HTML_MODE_LEGACY)
            mockTextView.text = spanned
        }
    }
}