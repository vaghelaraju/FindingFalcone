package com.atharva.findfalcone.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.atharva.findfalcone.R
import com.atharva.findfalcone.data.model.FindFalconeResponse
import com.atharva.findfalcone.data.model.Vehicle
import com.atharva.findfalcone.databinding.ActivityMainBinding
import com.atharva.findfalcone.di.component.ActivityComponent
import com.atharva.findfalcone.ui.base.BaseActivity
import com.atharva.findfalcone.ui.status.DisplayStatusActivity
import com.atharva.findfalcone.ui.vehicle.VehicleActivity
import com.atharva.findfalcone.utils.constant.Constants
import com.atharva.findfalcone.utils.listner.RecyclerRowClick
import java.io.Serializable

class MainActivity : BaseActivity<MainViewModel>(), RecyclerRowClick {

    companion object {
        const val TAG = "MainActivity"
    }

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mPlanetAdapter: PlanetAdapter

    override fun provideLayoutView(): View {
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        mBinding.buttonFindFalcone.setOnClickListener {
            if (viewModel.checkValidation(this))
                fetchTokenToFindingFalcone()
        }
        mBinding.textViewReset.setOnClickListener {
            viewModel.resetData()
            setAdapter()
        }
        fetchData()
    }

    private fun fetchTokenToFindingFalcone() {
        viewModel.fetchToken()
    }

    private fun fetchData() {
        viewModel.fetchPlanetList()
        viewModel.fetchVehicleList()
    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.timeTaken?.observe(this) {
            mBinding.textViewTimeTaken.text = getString(R.string.time_taken) + " " + it
        }
        viewModel.isShowProcessBar?.observe(this) {
            mBinding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
        viewModel.mPlanetList?.observe(this) {
            setAdapter()
        }
        viewModel.tokenObserver?.observe(this) {
            getFindingFalcone(it)
        }
        viewModel.findFalconeObserver?.observe(this) {
            if (it != null) {
                openDisplayActivity(it)
            }
        }
    }

    private fun openDisplayActivity(it: FindFalconeResponse) {
        val intent = Intent(this, DisplayStatusActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable(Constants.FIND_FALCONE_RESPONSE, it as Serializable)
        intent.putExtra(Constants.BUNDLE, bundle)
        startActivity(intent)
    }

    private fun setAdapter() {
        val list = viewModel.mPlanetList.value
        mPlanetAdapter = PlanetAdapter(list, this)
        mBinding.recyclerViewPlanets.adapter = mPlanetAdapter
    }

    private fun getFindingFalcone(token: String?) {
        viewModel.fetchFindingFalcone(token)
    }

    override fun rowClick(pos: Int, flag: Int) {
        if (viewModel.performPlanetClick(this, pos, flag)) {
            val intent = Intent(this, VehicleActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable(
                Constants.EXTRA_VEHICLE_LIST,
                viewModel.mVehicleList.value as Serializable
            )
            intent.putExtra(Constants.BUNDLE, bundle)
            intent.putExtra(Constants.DISTANCE, viewModel.mPlanetList!!.value!![pos]!!.distance)
            intent.putExtra(Constants.Name, viewModel.mPlanetList!!.value!![pos]!!.name)
            resultLauncher.launch(intent)
        }
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                data.let {
                    val vehicleIndex = data!!.getIntExtra(Constants.POSITION, 0)
                    val bundle = data!!.getBundleExtra(Constants.BUNDLE)
                    val list =
                        bundle!!.getSerializable(Constants.EXTRA_VEHICLE_LIST) as ArrayList<Vehicle>
                    viewModel.mVehicleList.postValue(list)
                    updatePlanetDetail(vehicleIndex)
                }
            }
        }

    private fun updatePlanetDetail(vehicleIndex: Int) {
        viewModel.updatePlanetDetail(vehicleIndex)
        mPlanetAdapter.notifyDataSetChanged()
    }
}