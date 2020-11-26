package com.hsd.contest.spain.view.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hsd.contest.domain.entities.WeatherNextDays
import com.hsd.contest.spain.databinding.ItemWeatherBinding

class WeatherAdapter(
    private val listDays: List<WeatherNextDays>,
) :
    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    private lateinit var binding: ItemWeatherBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        binding = ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(listDays[position])
    }

    override fun getItemCount(): Int = listDays.size

    inner class WeatherViewHolder(private val bindingVH: ItemWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(day: WeatherNextDays) {
            bindingVH.weatherDate.text = day.date
            bindingVH.weatherTemperature.text =
                StringBuilder(day.temperatureMax.toString()).append("º / ")
                    .append(day.temperatureMin).append("º")
        }
    }
}