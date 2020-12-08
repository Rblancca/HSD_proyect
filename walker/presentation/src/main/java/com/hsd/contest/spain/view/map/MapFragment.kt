package com.hsd.contest.spain.view.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.hsd.contest.spain.databinding.MapFragmentBinding
import com.huawei.hms.maps.CameraUpdateFactory
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.MapView
import com.huawei.hms.maps.OnMapReadyCallback
import com.huawei.hms.maps.model.LatLng
import com.huawei.hms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback {

    private var binding: MapFragmentBinding? = null

    private val args: MapFragmentArgs by navArgs()

    private var hMap: HuaweiMap? = null

    private var mMapView: MapView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MapFragmentBinding.inflate(inflater, container, false)
        mMapView = binding?.mapView
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle =
                savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY)
        }
        mMapView?.apply {
            onCreate(mapViewBundle)
            getMapAsync(this@MapFragment)
        }
        return binding?.root
    }

    companion object {
        private const val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
    }

    override fun onMapReady(p0: HuaweiMap?) {
        val latLong =
            LatLng(
                args.latitude.toDouble(),
                args.longitude.toDouble()
            )
        p0 ?: return
        with(p0) {
            moveCamera(CameraUpdateFactory.newLatLngZoom(latLong, 13f))
            addMarker(MarkerOptions().position(latLong))
        }
        hMap = p0
        hMap?.isMyLocationEnabled = true
    }

    override fun onStart() {
        super.onStart()
        mMapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mMapView?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView?.onDestroy()
    }

    override fun onPause() {
        mMapView?.onPause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        mMapView?.onResume()
    }
}
