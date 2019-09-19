package me.porge.android.cars.vehicles.layers.presentation.views

import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_vehicles.*
import me.porge.android.cars.core.layers.presentation.views.BaseActivity
import me.porge.android.cars.core.layers.presentation.views.bottomsheet.BottomSheetController
import me.porge.android.cars.vehicles.R
import me.porge.android.cars.vehicles.layers.presentation.VehiclesViewModel
import me.porge.android.cars.vehicles.layers.presentation.VehiclesViewState
import org.koin.androidx.viewmodel.ext.android.viewModel

class VehiclesActivity :
    BaseActivity<VehiclesViewState, VehiclesViewModel>(),
    BottomSheetController {

    override val viewModel: VehiclesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicles)

        window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            statusBarColor = Color.TRANSPARENT
        }

        BottomSheetBehavior.from(bottomSheetVehicles).isHideable = false

        viewModel
            .getLiveViewState()
            .observeViewState()
    }

    override fun bind(viewState: VehiclesViewState) {
    }

    override fun setBottomSheetExpanded(isExpanded: Boolean) {
        BottomSheetBehavior.from(bottomSheetVehicles).state = when {
            isExpanded -> BottomSheetBehavior.STATE_EXPANDED
            else -> BottomSheetBehavior.STATE_COLLAPSED
        }
    }
}