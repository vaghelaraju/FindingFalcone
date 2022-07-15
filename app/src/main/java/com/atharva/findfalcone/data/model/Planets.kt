package com.atharva.findfalcone.data.model


import com.google.gson.annotations.SerializedName

class Planets : ArrayList<Planets.PlanetsItem>() {
    class PlanetsItem(
        @SerializedName("distance")
        val distance: Int? = 0,
        @SerializedName("name")
        val name: String? = "",
        var vehicle: Vehicle? = null
    ) {
        override fun toString(): String {
            return name.toString()
        }

        override fun equals(other: Any?): Boolean {
            return name!!.equals((other as PlanetsItem).name)
        }
    }
}