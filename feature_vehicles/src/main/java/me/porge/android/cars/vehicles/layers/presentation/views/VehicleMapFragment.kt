package me.porge.android.cars.vehicles.layers.presentation.views

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.core.content.ContextCompat
import androidx.test.espresso.idling.CountingIdlingResource
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import me.porge.android.cars.core.di.KoinInjector
import me.porge.android.cars.core.layers.presentation.views.BaseFragment
import me.porge.android.cars.vehicles.R
import me.porge.android.cars.vehicles.layers.presentation.VehiclesViewModel
import me.porge.android.cars.vehicles.layers.presentation.VehiclesViewState
import me.porge.android.cars.vehicles.layers.presentation.extensions.toBitmapDescriptor
import me.porge.android.cars.vehicles.layers.presentation.map.VehicleMapController
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.get
import org.koin.core.parameter.parametersOf


class VehicleMapFragment : BaseFragment<VehiclesViewState, VehiclesViewModel>(),
    OnMapReadyCallback {

    override val viewModel: VehiclesViewModel by sharedViewModel()
    private val countingIdlingResources by inject<CountingIdlingResource>()

    private val mapFragment
        get() = childFragmentManager.findFragmentById(R.id.fragmentMapVehicleMap) as? SupportMapFragment

    @VisibleForTesting
    var mapController: VehicleMapController? = null

    @VisibleForTesting
    val selectedMarkerIcon
        get() = ContextCompat
            .getDrawable(requireContext(), R.drawable.ic_marker_selected)?.toBitmapDescriptor()

    @VisibleForTesting
    val normalMarkerIcon
        get() = ContextCompat
            .getDrawable(requireContext(), R.drawable.ic_marker_normal)?.toBitmapDescriptor()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_vehicle_map, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        countingIdlingResources.increment()
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.setOnMapLoadedCallback {
            mapController = KoinInjector.get<VehicleMapController> {
                parametersOf(
                    selectedMarkerIcon,
                    normalMarkerIcon,
                    googleMap,
                    viewModel::onVehicleSelected
                )
            }

            with(viewModel) {
                getLiveViewState().observeViewState()
                onMapReady()
                countingIdlingResources.decrement()
            }
        }
    }

    override fun bind(viewState: VehiclesViewState) {
        when (viewState) {
            is VehiclesViewState.VehicleFocused ->
                mapController?.focusOnVehicle(viewState.selectedVehicle)

            is VehiclesViewState.Error ->
                showError(viewState.errorMsgResId, viewState.errorPositiveActionResId)

            else ->
                mapController?.setVehicles(viewState.vehicles)
        }
    }

    private fun showError(@StringRes errorMsgId: Int, @StringRes errorPositiveActionResId: Int) {
        AlertDialog
            .Builder(requireContext())
            .setMessage(errorMsgId)
            .setPositiveButton(errorPositiveActionResId) { _, _ -> viewModel.onMapReady() }
            .setCancelable(false)
            .show()
    }
}