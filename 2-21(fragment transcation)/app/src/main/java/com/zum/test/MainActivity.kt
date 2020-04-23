package com.zum.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val bundleKey = "number"
    private var mNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_add.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .add(container_fragment.id, MainFragment.shared(mNumber++), mNumber.toString())
                .commit()
        }

        btn_remove.setOnClickListener {
            if (mNumber == 0) {
                return@setOnClickListener
            }

            supportFragmentManager.findFragmentByTag(mNumber.toString())?.let { fragment ->
                supportFragmentManager.beginTransaction()
                    .remove(fragment)
                    .commitAllowingStateLoss()

                mNumber--
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(bundleKey,mNumber)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        mNumber = savedInstanceState.getInt(bundleKey)
    }
}
