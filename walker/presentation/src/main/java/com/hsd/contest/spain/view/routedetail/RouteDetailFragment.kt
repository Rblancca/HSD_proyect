package com.hsd.contest.spain.view.routedetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.hsd.contest.domain.URL_IMAGE
import com.hsd.contest.spain.R
import com.hsd.contest.spain.databinding.RouteDetailFragmentBinding
import com.huawei.hms.ads.AdParam


class RouteDetailFragment : Fragment() {
    private var binding: RouteDetailFragmentBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RouteDetailFragmentBinding.inflate(inflater, container, false)
        initUI()
        initBanner()
        return binding?.root
    }

    private fun initBanner() {
        val adParam = AdParam.Builder().build()
        binding?.hwBannerView?.loadAd(adParam)
    }

    private fun initUI() {
        val route = (activity as RouteDetailActivity).args.routeInfo
        binding?.apply {
            Glide.with(requireContext())
                .load(URL_IMAGE + route.id + ".JPG")
                .placeholder(R.drawable.placeholder)
                .into(routeDetailImage)
            routeDetailTitle.text = route.name
            routeDetailDifValue.text = route.difficulty
            routeDetailDistanceValue.text = StringBuilder(route.distance.toString()).append("m")
            routeDetailTimeValue.text = route.duration
            routeDetailMunicipValue.text = route.municipality
            routeDetailPlaceValue.setOnClickListener {
                findNavController().navigate(
                    RouteDetailFragmentDirections.actionRouteDetailFragmentToMapFragment(
                        route.latitude.toString(),
                        route.longitude.toString()
                    )
                )
            }
            routeDetailMoreInfo.setOnClickListener { openUrl(route.info) }
        }

    }

    private fun openUrl(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }
}