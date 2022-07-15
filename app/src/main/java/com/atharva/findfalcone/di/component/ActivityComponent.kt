package com.atharva.findfalcone.di.component

import com.atharva.findfalcone.di.ActivityScope
import com.atharva.findfalcone.di.module.ActivityModule
import com.atharva.findfalcone.ui.home.MainActivity
import com.atharva.findfalcone.ui.status.DisplayStatusActivity
import com.atharva.findfalcone.ui.vehicle.VehicleActivity

import dagger.Component



@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ActivityModule::class]
)
interface ActivityComponent {

    fun inject(activity: MainActivity)
    fun inject(activity: VehicleActivity)
    fun inject(activity: DisplayStatusActivity)


}