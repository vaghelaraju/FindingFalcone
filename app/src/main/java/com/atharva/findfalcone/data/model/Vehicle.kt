package com.atharva.findfalcone.data.model

import java.io.Serializable

data class Vehicle(
    val maxDistance: Int? = 0,
    val name: String? = "",
    val speed: Int? = 0,
    var totalNo: Int? = 0
) :
    Serializable