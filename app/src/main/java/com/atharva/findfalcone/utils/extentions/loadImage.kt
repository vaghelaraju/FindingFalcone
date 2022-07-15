package com.atharva.findfalcone.utils.extentions

import android.widget.*
import androidx.databinding.BindingAdapter
import com.atharva.findfalcone.R

@BindingAdapter("loadImage")
fun loadImage(view: ImageView, name: String) {
    when (name) {
        "Donlon" -> view.setBackgroundResource(R.drawable.donlon)
        "Enchai" -> view.setBackgroundResource(R.drawable.enchai)
        "Jebing" -> view.setBackgroundResource(R.drawable.jebing)
        "Sapir" -> view.setBackgroundResource(R.drawable.sapir)
        "Lerbin" -> view.setBackgroundResource(R.drawable.lerbin)
        "Pingasor" -> view.setBackgroundResource(R.drawable.pingasor)
        else -> view.setBackgroundResource(R.mipmap.ic_launcher)
    }
}

@BindingAdapter("loadVehicleImage")
fun loadVehicleImage(view: ImageView, name: String) {
    when (name) {
        "Space pod" -> view.setBackgroundResource(R.drawable.space_pod)
        "Space rocket" -> view.setBackgroundResource(R.drawable.rocket)
        "Space shuttle" -> view.setBackgroundResource(R.drawable.space_shuttle)
        "Space ship" -> view.setBackgroundResource(R.drawable.space_ship)

        else -> view.setBackgroundResource(R.mipmap.ic_launcher)
    }
}




