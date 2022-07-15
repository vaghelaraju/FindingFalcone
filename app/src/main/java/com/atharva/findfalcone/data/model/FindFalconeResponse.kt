package com.atharva.findfalcone.data.model

import com.squareup.moshi.Json
import java.io.Serializable

data class FindFalconeResponse(
    @Json(name = "planet_name") val planet_name: String?,
    val status: String?,
    val error: String?
) : Serializable