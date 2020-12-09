package com.hsd.contest.spain.view.sportprofile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hsd.contest.spain.databinding.ProfileFragmentBinding
import com.hsd.contest.spain.view.MenuActivity
import com.hsd.contest.spain.view.sportprofile.serviceshuawei.HiHealthSetup
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    private var binding: ProfileFragmentBinding? = null
    private val viewModel by viewModel<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfileFragmentBinding.inflate(inflater, container, false)
        HiHealthSetup.login(requireActivity(), viewModel)
        analytics()
        viewModel.totalSteps.observe(viewLifecycleOwner, { steps ->
            steps?.let {
                binding?.profileStepsValue?.text = it
                binding?.profileProgress?.progress = it.toFloat()
            }
        })
        viewModel.strideFrequency.observe(viewLifecycleOwner, {
            binding?.profileStepsFreqValue?.text = it
        })
        viewModel.calorie.observe(viewLifecycleOwner, {
            binding?.profileCaloriesValue?.text = it
        })
        return binding?.root
    }

    private fun analytics() {
        val bundle = Bundle()
        bundle.putString("screen_name", "Profile)")
        (activity as MenuActivity).instance?.onEvent("screen_Profile", bundle)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        HiHealthSetup.onActivityResult(requestCode, data)
    }
}