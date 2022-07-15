package com.atharva.findfalcone.di.component

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.atharva.findfalcone.FEProblemApplication
import com.atharva.findfalcone.data.remote.NetworkService
import com.atharva.findfalcone.di.ApplicationContext
import com.atharva.findfalcone.data.repository.HomeRepository
import com.atharva.findfalcone.di.module.ApplicationModule
import com.atharva.findfalcone.utils.network.NetworkHelper
import com.atharva.findfalcone.utils.rx.SchedulerProvider
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton


@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(app: FEProblemApplication)

    fun getApplication(): Application

    @ApplicationContext
    fun getContext(): Context

    fun getNetworkService(): NetworkService

    fun getSharedPreferences(): SharedPreferences

    fun getNetworkHelper(): NetworkHelper

    fun getUserRepository(): HomeRepository

    fun getSchedulerProvider(): SchedulerProvider

    fun getCompositeDisposable(): CompositeDisposable
}