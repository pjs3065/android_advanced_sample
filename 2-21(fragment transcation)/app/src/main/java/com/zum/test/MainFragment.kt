package com.zum.test

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment: Fragment() {

    private var mNumber = 0

    companion object{
        private const val bundleKey = "number"

        fun shared(no:Int):MainFragment{
            val mainFragment = MainFragment()
            val bundle = Bundle()

            bundle.putInt(bundleKey, no)
            mainFragment.arguments = bundle

            return mainFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("debugTest","onCreate")
        mNumber = arguments!!.getInt(bundleKey,0)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("debugTest","onCreateView")
        return layoutInflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("debugTest","onViewCreated")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("debugTest","onActivityCreated")
        tv_name_fragment.text = "$mNumber 번째 fragment를 생성했습니다."
    }
}