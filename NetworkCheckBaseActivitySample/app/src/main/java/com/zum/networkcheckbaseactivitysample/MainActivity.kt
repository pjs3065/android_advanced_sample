package com.zum.networkcheckbaseactivitysample

import android.os.Bundle
import com.zum.networkcheckbaseactivitysample.common.activity.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
