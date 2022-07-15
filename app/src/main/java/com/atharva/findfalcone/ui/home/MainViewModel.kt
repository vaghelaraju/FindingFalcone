package com.atharva.findfalcone.ui.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.atharva.findfalcone.R
import com.atharva.findfalcone.data.model.*
import com.atharva.findfalcone.data.repository.HomeRepository
import com.atharva.findfalcone.ui.base.BaseViewModel
import com.atharva.findfalcone.utils.extentions.alert
import com.atharva.findfalcone.utils.extentions.log
import com.atharva.findfalcone.utils.network.NetworkHelper
import com.atharva.findfalcone.utils.rx.SchedulerProvider
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import java.lang.NullPointerException

class MainViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    val homeRepository: HomeRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    companion object {
        const val TAG = "MainViewModel"
    }

    var planets: MutableLiveData<Planets>? =
        MutableLiveData<Planets>()
    var vehicles: MutableLiveData<Vehicles>? =
        MutableLiveData<Vehicles>()
    var tokenObserver: MutableLiveData<String>? =
        MutableLiveData<String>()
    var findFalconeObserver: MutableLiveData<FindFalconeResponse>? =
        MutableLiveData<FindFalconeResponse>()

    var mPlanetList: MutableLiveData<ArrayList<Planets.PlanetsItem>> = MutableLiveData()
    var mResetPlanetList: MutableLiveData<ArrayList<Planets.PlanetsItem>> = MutableLiveData()
    var mVehicleList: MutableLiveData<ArrayList<Vehicle>> = MutableLiveData()
    var mResetVehicleList: MutableLiveData<ArrayList<Vehicles.VehicleItem>> = MutableLiveData()
    private var mTempVehicleList: ArrayList<Vehicles.VehicleItem> = ArrayList()
    val selectedPlanet: MutableLiveData<Planets.PlanetsItem>? = MutableLiveData()
    val selectedPlanetPosition: MutableLiveData<Int>? = MutableLiveData(-1)
    val timeTaken: MutableLiveData<Int>? = MutableLiveData(0)
    val isFindFalconeEnable: MutableLiveData<Boolean>? = MutableLiveData(false)
    val isShowProcessBar: MutableLiveData<Boolean>? = MutableLiveData(false)


    override fun onCreate() {

    }

    fun fetchPlanetList() {
        if (checkInternetConnection()) {
            isShowProcessBar?.postValue(true)
            compositeDisposable.addAll(
                homeRepository.fetchPlanetList(planets)
                    .subscribeOn(schedulerProvider.io())
                    .subscribe(
                        {
                            setPlanetList(it)
                            isShowProcessBar?.postValue(false)
                        },
                        {
                            isShowProcessBar?.postValue(false)
                        }
                    )

            )
        }
    }

    private fun setPlanetList(it: Planets?) {
        if (it != null) {
            mPlanetList.postValue(it!!)
            val jsonElements = Gson().toJsonTree(it)
            val list = Gson().fromJson(jsonElements, Planets::class.java)
            mResetPlanetList.postValue(list)
        }
    }

    fun fetchVehicleList() {
        if (checkInternetConnection()) {
            isShowProcessBar?.postValue(true)
            compositeDisposable.addAll(
                homeRepository.fetchVehicleList(vehicles)
                    .subscribeOn(schedulerProvider.io())
                    .subscribe(
                        {
                            setVehicleData(it)
                            isShowProcessBar?.postValue(false)
                        },
                        {
                            isShowProcessBar?.postValue(false)
                        }
                    )

            )
        }
    }

    private fun setVehicleData(it: Vehicles?) {
        var list: ArrayList<Vehicle> = ArrayList()
        for (item in it!!) {
            log(MainActivity.TAG, "name:" + item.name)
            log(MainActivity.TAG, "Dis: " + item.maxDistance)
            list.add(Vehicle(item.maxDistance, item.name, item.speed, item.totalNo))
        }
        mVehicleList.postValue(list)
        val jsonElements = Gson().toJsonTree(it)
        val vehicleList = Gson().fromJson(jsonElements, Vehicles::class.java)
        mResetVehicleList.postValue(vehicleList)
    }

    fun fetchToken() {
        if (checkInternetConnection()) {
            isShowProcessBar?.postValue(true)
            compositeDisposable.addAll(
                homeRepository.fetchToken(tokenObserver)
                    .subscribeOn(schedulerProvider.io())
                    .subscribe(
                        {
                            isShowProcessBar?.postValue(false)
                        },
                        {
                            isShowProcessBar?.postValue(false)
                        }
                    )

            )
        }
    }

    fun fetchFindingFalcone(
        token: String?,

        ) {
        val filteredPlanet = mPlanetList.value?.filter { it?.vehicle != null }
        var planetsList = filteredPlanet?.map { it?.name!! }
        var vehiclesList = filteredPlanet?.map { it?.vehicle?.name!! }

        if (checkInternetConnection()) {
            FindApiRequest(token!!, planetsList!!, vehiclesList!!)
            isShowProcessBar?.postValue(true)
            compositeDisposable.addAll(
                homeRepository.fetchFindingFalcone(
                    FindApiRequest(
                        token!!,
                        planetsList,
                        vehiclesList
                    )
                )
                    .subscribeOn(schedulerProvider.io())
                    .subscribe(
                        {
                            findFalconeObserver?.postValue(it)
                            isShowProcessBar?.postValue(false)
                        },
                        {
                            isShowProcessBar?.postValue(false)
                        }
                    )

            )
        }
    }

    fun checkValidation(context: Context): Boolean {
        val selectedPlanet = mPlanetList.value!!.filter { it != null && it.vehicle != null }.size
        if (selectedPlanet == 4) {
            return true
        } else {
            context.alert(context.getString(R.string.please_select_exactly_4_planet))
            return false
        }
    }

    fun performPlanetClick(context: Context, pos: Int, flag: Int): Boolean {
        selectedPlanet?.postValue(mPlanetList!!.value!![pos]!!)
        selectedPlanetPosition!!.postValue(pos)
        val count = mPlanetList!!.value!!.filter { it != null && it.vehicle != null }.size
        log(MainActivity.TAG, "Selected planet" + count)
        if (count == 4 && mPlanetList!!.value!![pos] != null && mPlanetList!!.value!![pos]!!.vehicle == null) {
            log(MainActivity.TAG, "Please reset filter" + count)
            val msg = String.format(
                context.getString(R.string.to_select_this_planet_please_reset_filter),
                mPlanetList!!.value!![pos]!!.name
            )
            context.alert(msg)
            return false
        } else if (mPlanetList!!.value!![pos] != null && mPlanetList!!.value!![pos]!!.vehicle != null) {

            var mVehicle = mPlanetList!!.value!![pos]!!.vehicle
            for (index in mVehicleList.value!!.indices) {
                if (mVehicle?.name == mVehicleList.value!![index].name) {
                    var vehicle = mVehicleList!!.value!![index]!!
                    vehicle.totalNo = vehicle.totalNo!! + 1
                    mVehicleList.value!![index] = vehicle
                }
            }
            return true
        } else {
            log(MainActivity.TAG, "can select" + count)
            return true
        }
    }

    fun updatePlanetDetail(vehicleIndex: Int) {
        val selectedPlanet = selectedPlanet!!.value
        val selectedPlanetPosition = selectedPlanetPosition!!.value
        selectedPlanet!!.vehicle = mVehicleList.value!![vehicleIndex]
        mPlanetList!!.value!!.set(selectedPlanetPosition!!, selectedPlanet)
        var timeTake: Int = 0
        var selectedPlanetCount = 0
        for (item in mPlanetList!!.value!!) {
            if (item!!.vehicle != null) {
                try {
                    val time: Int
                    val distance = item.distance!!
                    val speed = item.vehicle!!.speed!!
                    time = distance / speed
                    timeTake += timeTake + time
                } catch (e: NullPointerException) {
                } catch (e: Exception) {

                }
                selectedPlanetCount++
            }
        }
        if (selectedPlanetCount == 4) isFindFalconeEnable!!.postValue(true)
        timeTaken?.postValue(timeTake)
    }

    fun resetData() {
        isFindFalconeEnable!!.postValue(false)
        timeTaken?.postValue(0)
        var jsonPlanetList = Gson().toJsonTree(mResetPlanetList.value)
        val tempPlanetList = Gson().fromJson(jsonPlanetList, Planets::class.java)
        mPlanetList.postValue(tempPlanetList)
        var jsonElement1 = Gson().toJsonTree(mResetPlanetList.value)
        mPlanetList.postValue(Gson().fromJson(jsonElement1, Planets::class.java))
        var jsonVehicleList = Gson().toJsonTree(mResetVehicleList.value)
        mTempVehicleList = ArrayList()
        mTempVehicleList.clear()
        mTempVehicleList.addAll(Gson().fromJson(jsonVehicleList, Vehicles::class.java))
        var list: ArrayList<Vehicle> = ArrayList()
        for (item in mTempVehicleList!!) {
            log(MainActivity.TAG, "name:" + item.name)
            log(MainActivity.TAG, "Dis: " + item.maxDistance)
            list.add(Vehicle(item.maxDistance, item.name, item.speed, item.totalNo))
        }
        mVehicleList.postValue(list)
    }
}