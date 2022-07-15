package com.atharva.findfalcone.data.repository

import androidx.lifecycle.MutableLiveData
import com.atharva.findfalcone.data.model.*
import com.atharva.findfalcone.data.remote.NetworkService
import com.atharva.findfalcone.utils.extentions.log
import com.google.gson.GsonBuilder


import io.reactivex.Single

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(
    private val networkService: NetworkService,
) {

    fun fetchPlanetList(
        liveData: MutableLiveData<Planets>?,
    ): Single<Planets> =
        networkService.fetchPlanetList()
            .map {
                val response = GsonBuilder().create()
                    .fromJson(
                        GsonBuilder().create().toJson(it),
                        Planets::class.java
                    )
                liveData!!.postValue(response)
                it

            }


    fun fetchVehicleList(
        liveData: MutableLiveData<Vehicles>?,
    ): Single<Vehicles> =
        networkService.fetchVehicleList()
            .map {
                val response = GsonBuilder().create()
                    .fromJson(
                        GsonBuilder().create().toJson(it),
                        Vehicles::class.java
                    )
                liveData!!.postValue(response)
                it

            }

    fun fetchToken(
        liveData: MutableLiveData<String>?,
    ): Single<Token> =
        networkService.fetchToken()
            .map {
                liveData!!.postValue(it.token)
                it

            }

    fun fetchFindingFalcone(
        findApiRequest: FindApiRequest,
    ): Single<FindFalconeResponse> =
        networkService.fetchFindingFalcone(findApiRequest)
            .map {
                it

            }
}