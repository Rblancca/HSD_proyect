package com.hsd.contest.spain.view.routedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.hsd.contest.domain.URL_IMAGE
import com.hsd.contest.spain.R
import com.hsd.contest.spain.databinding.RouteDetailFragmentBinding

class RouteDetailFragment : Fragment() {
    private var binding: RouteDetailFragmentBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RouteDetailFragmentBinding.inflate(inflater, container, false)
        initUI()
        return binding?.root
    }

    private fun initUI(){
        binding?.routeDetailImage?.let {
            Glide.with(requireContext())
                .load(URL_IMAGE + (activity as RouteDetailActivity).args.routeInfo.id + ".JPG")
                .placeholder(R.drawable.placeholder)
                .into(it)
        }
    }
}