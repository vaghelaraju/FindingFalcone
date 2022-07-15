package com.atharva.findfalcone

import android.app.Application
import com.atharva.findfalcone.di.component.ApplicationComponent
import com.atharva.findfalcone.di.component.DaggerApplicationComponent
import com.atharva.findfalcone.di.module.ApplicationModule

class FEProblemApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }

    fun setComponent(applicationComponent: ApplicationComponent) {
        this.applicationComponent = applicationComponent
    }
}
