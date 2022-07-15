package com.atharva.findfalcone.ui.status

import android.os.Bundle
import android.view.View
import com.atharva.findfalcone.R
import com.atharva.findfalcone.data.model.FindFalconeResponse
import com.atharva.findfalcone.databinding.ActivityDisplayStatusBinding
import com.atharva.findfalcone.di.component.ActivityComponent
import com.atharva.findfalcone.ui.base.BaseActivity
import com.atharva.findfalcone.utils.constant.Constants
import com.atharva.findfalcone.utils.extentions.alert
import com.atharva.findfalcone.utils.extentions.log

class DisplayStatusActivity : BaseActivity<DisplayStatusViewModel>() {

    companion object {
        const val TAG = "DisplayStatusActivity"
    }

    private lateinit var mBinding: ActivityDisplayStatusBinding

    override fun provideLayoutView(): View {
        mBinding = ActivityDisplayStatusBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        val bundle = intent.getBundleExtra(Constants.BUNDLE)
        var findFalconeRes =
            bundle!!.getSerializable(Constants.FIND_FALCONE_RESPONSE) as FindFalconeResponse
        if (findFalconeRes.status.equals("success")) {
            val msg = String.format(
                getString(R.string.msg_success),
                findFalconeRes.planet_name
            )
            mBinding.textViewStatus.text = msg
            mBinding.textViewStatus.setTextColor(getResources().getColor(R.color.colorSuccess))
        } else if (findFalconeRes.status.equals("false")) {
            mBinding.textViewStatus.text = getString(R.string.msg_failure)
            mBinding.textViewStatus.setTextColor(getResources().getColor(R.color.colorError))
        }else {
            alert( findFalconeRes?.error!!)
        }
        log(TAG, findFalconeRes.status!!)
    }

    override fun setupObservers() {
        super.setupObservers()

    }

}