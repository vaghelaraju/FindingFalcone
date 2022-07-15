package com.atharva.findfalcone.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.atharva.findfalcone.R
import com.atharva.findfalcone.data.model.Planets
import com.atharva.findfalcone.databinding.ItemPlanetBinding
import com.atharva.findfalcone.utils.extentions.loadVehicleImage
import com.atharva.findfalcone.utils.listner.RecyclerRowClick

class PlanetAdapter(
    private val items: ArrayList<Planets.PlanetsItem>?,
    private val clickListner: RecyclerRowClick
) : RecyclerView.Adapter<PlanetAdapter.ViewHolder>() {
    companion object {
        const val TAG = "PlanetAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: ItemPlanetBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_planet, parent, false)
        return ViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int = items!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items!![position]!!)

        holder.binding.root.setOnClickListener {
            if (clickListner != null) {
                clickListner.rowClick(position, 0)
            }
        }
    }

    class ViewHolder(val binding: ItemPlanetBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Planets.PlanetsItem) {
            binding.model = data
            if (data.vehicle != null) {
                loadVehicleImage(binding.imageViewVehicle, data.vehicle!!.name!!)
            } else {
                binding.imageViewVehicle.setBackgroundResource(0)
            }
            binding.executePendingBindings()
        }
    }

}