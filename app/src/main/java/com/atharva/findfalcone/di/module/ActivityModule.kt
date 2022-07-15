package com.atharva.findfalcone.di.module

import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.atharva.findfalcone.data.repository.HomeRepository
import com.atharva.findfalcone.ui.base.BaseActivity
import com.atharva.findfalcone.ui.home.MainViewModel
import com.atharva.findfalcone.ui.status.DisplayStatusViewModel
import com.atharva.findfalcone.ui.vehicle.VehicleViewModel
import com.atharva.findfalcone.utils.ViewModelProviderFactory
import com.atharva.findfalcone.utils.network.NetworkHelper
import com.atharva.findfalcone.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class ActivityModule(private val activity: BaseActivity<*>) {

    @Provides
    fun provideLinearLayoutManager(): LinearLayoutManager = LinearLayoutManager(activity)

    @Provides
    fun provideMainViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        homeRepository: HomeRepository
    ): MainViewModel = ViewModelProviders.of(
        activity, ViewModelProviderFactory(MainViewModel::class) {
            MainViewModel(schedulerProvider, compositeDisposable, networkHelper, homeRepository)
        }).get(MainViewModel::class.java)

    @Provides
    fun provideVehicleViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        homeRepository: HomeRepository
    ): VehicleViewModel = ViewModelProviders.of(
        activity, ViewModelProviderFactory(VehicleViewModel::class) {
            VehicleViewModel(schedulerProvider, compositeDisposable, networkHelper, homeRepository)
        }).get(VehicleViewModel::class.java)

    @Provides
    fun provideDisplayStatusViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        homeRepository: HomeRepository
    ): DisplayStatusViewModel = ViewModelProviders.of(
        activity, ViewModelProviderFactory(DisplayStatusViewModel::class) {
            DisplayStatusViewModel(
                schedulerProvider,
                compositeDisposable,
                networkHelper,
                homeRepository
            )
        }).get(DisplayStatusViewModel::class.java)
}