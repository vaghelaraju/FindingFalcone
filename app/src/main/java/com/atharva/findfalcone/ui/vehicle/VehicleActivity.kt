package com.atharva.findfalcone.ui.vehicle

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.atharva.findfalcone.data.model.Vehicle
import com.atharva.findfalcone.databinding.ActivityVehicleBinding
import com.atharva.findfalcone.di.component.ActivityComponent
import com.atharva.findfalcone.ui.base.BaseActivity
import com.atharva.findfalcone.utils.constant.Constants
import com.atharva.findfalcone.utils.listner.RecyclerRowClick

class VehicleActivity : BaseActivity<VehicleViewModel>(), RecyclerRowClick {

    companion object {
        const val TAG = "VehicleActivity"
    }

    private lateinit var mBinding: ActivityVehicleBinding
    private lateinit var mVehicleAdapter: VehicleAdapter

    override fun provideLayoutView(): View {
        mBinding = ActivityVehicleBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        val distance = intent.getIntExtra(Constants.DISTANCE, -1)
        val name = intent.getStringExtra(Constants.Name)!!
        viewModel.planetDistance.postValue(distance)
        viewModel.planetName.postValue(name)
        val bundle = intent.getBundleExtra(Constants.BUNDLE)
        viewModel.mVehicleList.postValue(bundle!!.getSerializable(Constants.EXTRA_VEHICLE_LIST) as ArrayList<Vehicle>)

    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.mVehicleList.observe(this) {
            mVehicleAdapter = VehicleAdapter(it, this)
            mBinding.recyclerViewPlanets.adapter = mVehicleAdapter
        }
    }

    override fun rowClick(pos: Int, flag: Int) {
        val isVehicleSelected = viewModel.performVehicleClick(this, pos, flag)
        if (isVehicleSelected) {
            selectVehicle(pos)
        }
    }

    private fun selectVehicle(pos: Int) {
        val intent = Intent(this, VehicleActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable(Constants.EXTRA_VEHICLE_LIST, viewModel.mVehicleList.value)
        intent.putExtra(Constants.BUNDLE, bundle)
        intent.putExtra(Constants.POSITION, pos)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}