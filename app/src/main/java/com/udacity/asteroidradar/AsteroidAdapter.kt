package com.udacity.asteroidradar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.ListItemAsteroidBinding


class AsteroidAdapter(private val clickListener: AsteroidListener): ListAdapter<Asteroid,
        AsteroidAdapter.AsteroidViewHolder>(DiffCallback) {

    class AsteroidViewHolder private constructor(private val binding: ListItemAsteroidBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(asteroid:Asteroid, listener: AsteroidListener){
            binding.data = asteroid
            binding.listener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): AsteroidViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemAsteroidBinding.inflate(layoutInflater, parent,false)
                return AsteroidViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid = getItem(position)
        holder.bind(asteroid, clickListener)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id === newItem.id
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem == newItem
        }
    }

    class AsteroidListener(val clickListener: (asteroid: Asteroid) -> Unit ) {
        fun onClick(asteroid: Asteroid) = clickListener(asteroid)
    }
}
