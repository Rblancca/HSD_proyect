package com.hsd.contest.spain.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.hsd.contest.domain.entities.Routes
import com.hsd.contest.spain.R
import com.hsd.contest.spain.databinding.HomeFragmentBinding
import com.hsd.contest.spain.view.MenuActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var binding: HomeFragmentBinding? = null
    private val viewModel by viewModel<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        setObserver()
        analytics()
        return binding?.root
    }

    private fun analytics() {
        val bundle = Bundle()
        bundle.putString("screen_name", "Home)")
        (activity as MenuActivity).instance?.onEvent("screen_Home", bundle)
    }

    private fun setObserver() {
        viewModel.routes.observe(viewLifecycleOwner, { list ->
            initRecycler(list)
        })
        viewModel.errorCategories.observe(viewLifecycleOwner, {
            binding?.root?.let { root ->
                Snackbar.make(
                    root,
                    R.string.error_alert,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun initRecycler(list: Routes) {
        binding?.homeList?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = HomeAdapter(list.resources) { route ->
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToRouteDetailActivity(
                        route
                    )
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.homeList?.adapter = null
        binding = null
    }
}