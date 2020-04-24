package com.zum.a2_23childfragmenttransaction.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zum.a2_23childfragmenttransaction.R
import kotlinx.android.synthetic.main.fragment_child.*

class ChildFragment: Fragment() {

    private var mNumber = 0

    companion object{
        private const val bundleKey = "number"

        //프래그먼트 객체 생성
        fun shared(no:Int): ChildFragment {
            val mainFragment =
                ChildFragment()
            val bundle = Bundle()

            bundle.putInt(bundleKey, no)
            mainFragment.arguments = bundle

            return mainFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //번들 데이터 초기화
        mNumber = arguments!!.getInt(bundleKey,0)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //프래그먼트 뷰 전개
        return layoutInflater.inflate(R.layout.fragment_child, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_name_fragment.text = "$mNumber 번째 fragment를 생성했습니다."
    }
}