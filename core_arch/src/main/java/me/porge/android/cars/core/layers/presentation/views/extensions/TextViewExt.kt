package me.porge.android.cars.core.layers.presentation.views.extensions

import android.text.Html
import android.widget.TextView

fun TextView.setHtmlText(html: String) {
    text = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
}