package com.hsd.contest.spain.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hsd.contest.domain.URL_IMAGE
import com.hsd.contest.domain.entities.RouteInfo
import com.hsd.contest.spain.R
import com.hsd.contest.spain.databinding.ItemHomeBinding

class HomeAdapter(
    private val listRoutes: List<RouteInfo>,
    private val itemSelected: (RouteInfo) -> Unit
) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private lateinit var binding: ItemHomeBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(listRoutes[position])
    }

    override fun getItemCount(): Int = listRoutes.size

    inner class HomeViewHolder(private val bindingVH: ItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(route: RouteInfo) {
            bindingVH.itemHomeContainer.setOnClickListener { itemSelected.invoke(route) }
            bindingVH.itemHomeTitle.text = route.name
            Glide.with(bindingVH.root.context)
                .load(URL_IMAGE + route.id + ".JPG")
                .placeholder(R.drawable.placeholder)
                .into(bindingVH.itemHomeImage)
            bindingVH.itemHomeDif.text = "dificultad: " + route.difficulty
        }
    }
}