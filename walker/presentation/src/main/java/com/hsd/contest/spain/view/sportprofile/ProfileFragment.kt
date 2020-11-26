package com.hsd.contest.spain.view.sportprofile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hsd.contest.spain.databinding.ProfileFragmentBinding
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
        HiHealthSetup.login(requireActivity(),viewModel)
        viewModel.onStart()
        return binding?.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        HiHealthSetup.onActivityResult(requestCode, resultCode, data)
    }
}