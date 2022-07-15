package com.atharva.findfalcone.data.model


import com.google.gson.annotations.SerializedName

class Vehicles : ArrayList<Vehicles.VehicleItem>() {
    class VehicleItem(
        @SerializedName("max_distance")
        val maxDistance: Int? = 0,
        @SerializedName("name")
        val name: String? = "",
        @SerializedName("speed")
        val speed: Int? = 0,
        @SerializedName("total_no")
        val totalNo: Int? = 0
    )
}