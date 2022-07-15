package com.atharva.findfalcone.ui.vehicle

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.atharva.findfalcone.R
import com.atharva.findfalcone.data.model.Vehicle
import com.atharva.findfalcone.data.repository.HomeRepository
import com.atharva.findfalcone.ui.base.BaseViewModel
import com.atharva.findfalcone.utils.extentions.alert
import com.atharva.findfalcone.utils.extentions.log
import com.atharva.findfalcone.utils.network.NetworkHelper
import com.atharva.findfalcone.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable


class VehicleViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    val homeRepository: HomeRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    val planetDistance: MutableLiveData<Int> = MutableLiveData()
    val planetName: MutableLiveData<String> = MutableLiveData()
    var mVehicleList: MutableLiveData<ArrayList<Vehicle>> = MutableLiveData()

    override fun onCreate() {

    }

    fun performVehicleClick(context: Context, pos: Int, flag: Int): Boolean {
        if (mVehicleList!!.value!![pos]!!.totalNo!! == 0) {
            val msg = String.format(
                context.getString(R.string.vehicle_not_available),
                mVehicleList!!.value!![pos]!!.name
            )
            context.alert(msg)
            return false
        } else if (planetDistance.value!! <= mVehicleList!!.value!![pos]!!.maxDistance!!) {
            log(VehicleActivity.TAG, "can go")
            var vehicle = mVehicleList!!.value!![pos]!!
            vehicle.totalNo = vehicle.totalNo!! - 1
            mVehicleList.value!![pos] = vehicle
            return true
        } else {
            context.alert(context.getString(R.string.please_select_proper_vehical))
            log(VehicleActivity.TAG, "can not go")
            return false
        }
    }


}