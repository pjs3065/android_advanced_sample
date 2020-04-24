package com.zum.a2_23childfragmenttransaction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.zum.a2_23childfragmenttransaction.databinding.ActivityMainBinding
import com.zum.a2_23childfragmenttransaction.fragment.ParentFragment

class MainActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityMainBinding

    private val parentFragment by lazy {
        ParentFragment.shared()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //왼쪽에 붙이기
        onAttachedFragment(mBinding.containerLeftParentFragment.id)

        //프래그먼트를 왼쪽으로 이동하는 버튼
        mBinding.btnMoveLeft.setOnClickListener {
            supportFragmentManager.findFragmentById(mBinding.containerLeftParentFragment.id)
                ?: run {
                    onDetachedFragment()
                    onAttachedFragment(mBinding.containerLeftParentFragment.id)
                }
        }

        //프래그먼트를 오른쪽으로 이동하는 버튼
        mBinding.btnMoveRight.setOnClickListener {
            supportFragmentManager.findFragmentById(mBinding.containerRightParentFragment.id)
                ?: run {
                    onDetachedFragment()
                    onAttachedFragment(mBinding.containerRightParentFragment.id)
                }
        }
    }

    private fun onAttachedFragment(resId:Int){
        supportFragmentManager.findFragmentById(resId)?.let{
            supportFragmentManager.beginTransaction()
                .attach(parentFragment)
                .commitAllowingStateLoss()
        }?:run {
            supportFragmentManager.beginTransaction()
                .add(resId, parentFragment)
                .commitAllowingStateLoss()
        }
    }

    private fun onDetachedFragment() {
            supportFragmentManager.beginTransaction()
                .detach(parentFragment)
                .commitAllowingStateLoss()
    }
}
