package me.porge.android.cars.vehicles.layers.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.fragment_vehicle_list.*
import me.porge.android.cars.core.layers.presentation.views.BaseFragment
import me.porge.android.cars.core.layers.presentation.views.bottomsheet.BottomSheetController
import me.porge.android.cars.core.layers.presentation.views.extensions.makeVisibleIf
import me.porge.android.cars.core.layers.presentation.views.listable.ListableBinder
import me.porge.android.cars.core.layers.presentation.views.listable.attachListableAdapter
import me.porge.android.cars.core.layers.presentation.views.listable.listableAdapter
import me.porge.android.cars.vehicles.R
import me.porge.android.cars.vehicles.di.VEHICLE_BINDERS
import me.porge.android.cars.vehicles.layers.domain.entities.Vehicle
import me.porge.android.cars.vehicles.layers.presentation.VehiclesViewModel
import me.porge.android.cars.vehicles.layers.presentation.VehiclesViewState
import me.porge.android.cars.vehicles.layers.presentation.extensions.scrollToTop
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.qualifier.named


class VehicleListFragment : BaseFragment<VehiclesViewState, VehiclesViewModel>() {

    override val viewModel: VehiclesViewModel by sharedViewModel()

    private val listableBinders: Map<Int, ListableBinder<*>> by inject(named(VEHICLE_BINDERS))

    private val bottomSheetController
        get() = requireActivity() as? BottomSheetController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_vehicle_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerVehicleList.apply {
            attachListableAdapter(listableBinders) { itemClicked ->
                viewModel.onVehicleSelected(itemClicked as Vehicle)
                bottomSheetController?.setBottomSheetExpanded(false)
            }
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        viewModel
            .getLiveViewState()
            .observeViewState()
    }

    override fun bind(viewState: VehiclesViewState) {
        with(viewState) {
            progressBarVehicleList.makeVisibleIf(isLoading)

            if (this !is VehiclesViewState.Error)
                recyclerVehicleList.listableAdapter?.setItems(vehicles)

            if (this is VehiclesViewState.VehicleFocused)
                recyclerVehicleList.scrollToTop()
        }
    }
}