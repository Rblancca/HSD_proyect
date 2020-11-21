package com.hsd.contest.spain.view.routedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hsd.contest.spain.databinding.RouteDetailFragmentBinding

class RouteDetailFragment : Fragment() {
    private var binding: RouteDetailFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RouteDetailFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }
}