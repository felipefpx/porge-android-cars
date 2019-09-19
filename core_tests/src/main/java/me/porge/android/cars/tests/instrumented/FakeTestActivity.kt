package me.porge.android.cars.tests.instrumented

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_fake.*
import me.porge.android.cars.R
import me.porge.android.cars.core.layers.presentation.views.bottomsheet.BottomSheetController

open class FakeTestActivity : AppCompatActivity(), BottomSheetController {

    var bottomSheetIsExpanded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fake)
    }

    fun buildUi(block: FakeTestActivity.(container: ViewGroup) -> Unit) {
        runOnUiThread { block(frameFakeContainer) }
    }

    override fun setBottomSheetExpanded(isExpanded: Boolean) {
        this.bottomSheetIsExpanded = isExpanded
    }
}