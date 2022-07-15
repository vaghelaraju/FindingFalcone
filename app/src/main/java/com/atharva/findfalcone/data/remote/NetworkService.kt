package com.atharva.findfalcone.data.remote


import com.atharva.findfalcone.data.model.*
import io.reactivex.Single
import org.json.JSONObject
import retrofit2.http.*
import javax.inject.Singleton

@Singleton
interface NetworkService {

    @GET("${Endpoints.PLANETS}")
    fun fetchPlanetList(
    ): Single<Planets>

    @GET("${Endpoints.VEHICLES}")
    fun fetchVehicleList(
    ): Single<Vehicles>

    @Headers("Accept: application/json")
    @POST("${Endpoints.TOKEN}")
    fun fetchToken(
    ): Single<Token>

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("${Endpoints.FIND}")
    fun fetchFindingFalcone(@Body body: FindApiRequest): Single<FindFalconeResponse>

}