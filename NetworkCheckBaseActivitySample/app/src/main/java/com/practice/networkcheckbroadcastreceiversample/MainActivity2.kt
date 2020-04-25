package com.practice.networkcheckbroadcastreceiversample

import com.practice.networkcheckbroadcastreceiversample.common.activity.BaseActivity
import com.practice.networkcheckbroadcastreceiversample.databinding.ActivityMain2Binding

class MainActivity2 : BaseActivity<ActivityMain2Binding>() {

    override val layoutId: Int
        get() = R.layout.activity_main2

    override fun onAfterBinding(mBinding: ActivityMain2Binding) {
        with(mBinding){
        }
    }
}