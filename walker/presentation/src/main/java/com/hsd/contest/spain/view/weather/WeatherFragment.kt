package com.hsd.contest.spain.view.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.hsd.contest.domain.entities.ListMunicipality
import com.hsd.contest.domain.entities.ListProvinces
import com.hsd.contest.domain.entities.Province
import com.hsd.contest.spain.databinding.WeatherFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class WeatherFragment : Fragment() {
    private var binding: WeatherFragmentBinding? = null
    private val viewModel by viewModel<WeatherViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WeatherFragmentBinding.inflate(inflater, container, false)
        setObserver()
        return binding?.root
    }

    private fun setObserver() {
        viewModel.provinces.observe(viewLifecycleOwner, { list ->
            initProvinceSpinner(list)
        })
        viewModel.municipalities.observe(viewLifecycleOwner, { list ->
            initMunicipalitySpinner(list)
        })
    }

    private fun initMunicipalitySpinner(list: ListMunicipality) {
        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            list.list
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding?.weatheSpinnerMunicipality?.adapter = adapter
        }
        binding?.weatheSpinnerMunicipality?.onItemSelectedListener = (object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //viewModel.getMunicipality((binding?.weatheSpinner?.selectedItem as Province).code)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //
            }

        })
    }

    private fun initProvinceSpinner(list: ListProvinces) {
        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            list.provinces
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding?.weatheSpinner?.adapter = adapter
        }
        binding?.weatheSpinner?.onItemSelectedListener = (object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.getListMunicipality((binding?.weatheSpinner?.selectedItem as Province).code)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //
            }

        })

    }
}