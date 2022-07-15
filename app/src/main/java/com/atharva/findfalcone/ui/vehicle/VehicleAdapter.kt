package com.atharva.findfalcone.ui.vehicle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.atharva.findfalcone.R
import com.atharva.findfalcone.data.model.Vehicle
import com.atharva.findfalcone.databinding.ItemVehicalBinding
import com.atharva.findfalcone.utils.listner.RecyclerRowClick

class VehicleAdapter(
    private val items: ArrayList<Vehicle>,
    private val clickListner: RecyclerRowClick
) : RecyclerView.Adapter<VehicleAdapter.ViewHolder>() {
    companion object {
        const val TAG = "PlanetAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: ItemVehicalBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_vehical, parent, false)
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

    class ViewHolder(val binding: ItemVehicalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Vehicle) {
            binding.model = data
            binding.executePendingBindings()
        }
    }

}