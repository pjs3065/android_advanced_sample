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

        //추가 기능
        btn_add.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .add(container_fragment.id, MainFragment.shared(mNumber++), mNumber.toString())
                .commit()
        }

        //삭제 기능
        btn_remove.setOnClickListener {
            if (mNumber == 0) {
                return@setOnClickListener
            }

            //tag에 해당되는 프래그먼트를 가져와서 remove 시키기
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
        //상태값 저장
        outState.putInt(bundleKey,mNumber)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        //상태값 복원
        mNumber = savedInstanceState.getInt(bundleKey)
    }
}
