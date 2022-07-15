package com.atharva.findfalcone.ui.base

import androidx.lifecycle.ViewModel
import com.atharva.findfalcone.utils.network.NetworkHelper
import com.atharva.findfalcone.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel(
    protected val schedulerProvider: SchedulerProvider,
    protected val compositeDisposable: CompositeDisposable,
    protected val networkHelper: NetworkHelper
) : ViewModel() {

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    abstract fun onCreate()
    protected fun checkInternetConnection(): Boolean = networkHelper.isNetworkConnected()

}