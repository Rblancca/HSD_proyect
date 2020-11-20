package com.hsd.contest.spain.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hsd.contest.spain.databinding.WeatherFragmentBinding

class WeatherFragment : Fragment() {
    private var binding: WeatherFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WeatherFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

}