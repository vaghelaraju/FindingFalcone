package com.atharva.findfalcone.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Token(
    @SerialName("token")
    val token: String? = ""
)