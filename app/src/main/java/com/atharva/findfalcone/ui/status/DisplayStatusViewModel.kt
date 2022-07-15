package com.atharva.findfalcone.ui.status

import com.atharva.findfalcone.data.repository.HomeRepository
import com.atharva.findfalcone.ui.base.BaseViewModel
import com.atharva.findfalcone.utils.network.NetworkHelper
import com.atharva.findfalcone.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class DisplayStatusViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    val homeRepository: HomeRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {
    override fun onCreate() {

    }
}