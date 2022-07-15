package com.atharva.findfalcone.data.model

import com.squareup.moshi.Json

data class FindApiRequest(
    val token: String,
    @Json(name = "planet_names") val planet_names: List<String?>,
    @Json(name = "vehicle_names") val vehicle_names: List<String>
)