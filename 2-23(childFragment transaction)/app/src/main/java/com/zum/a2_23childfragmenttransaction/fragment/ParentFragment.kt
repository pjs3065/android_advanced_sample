package com.zum.a2_23childfragmenttransaction.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zum.a2_23childfragmenttransaction.R
import kotlinx.android.synthetic.main.fragment_parent.*

class ParentFragment : Fragment() {
    private var mNumber = 0
    private var fragmentList = mutableListOf<Pair<Fragment, String>>()

    companion object {

        private const val bundleKey = "bundleKey"

        //프래그먼트 객체 생성
        fun shared(): ParentFragment {
            return ParentFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("debugTest","onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("debugTest","onCreateView")
        //프래그먼트 뷰 전개
        return layoutInflater.inflate(R.layout.fragment_parent, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("debugTest","onViewCreated")

        //추가 기능
        btn_add.setOnClickListener {
            childFragmentManager.beginTransaction()
                .add(container_fragment.id, ChildFragment.shared(++mNumber), mNumber.toString())
                .commit()
        }

        //삭제 기능
        btn_remove.setOnClickListener {
            if (mNumber == 0) {
                return@setOnClickListener
            }

            //tag에 해당되는 프래그먼트를 가져와서 remove 시키기
            childFragmentManager.findFragmentByTag((mNumber).toString())?.let { fragment ->
                childFragmentManager.beginTransaction()
                    .remove(fragment)
                    .commitAllowingStateLoss()

                mNumber--
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("debugTest","onActivityCreated")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        Log.d("debugTest","onSaveInstanceState")

        outState.putInt(bundleKey,mNumber)

        fragmentList.clear()

        childFragmentManager.fragments.forEach { fragment ->
            fragmentList.add(Pair(fragment!!, fragment.tag!!))
        }
    }


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        Log.d("debugTest","onViewStateRestored")

        savedInstanceState?.let{
            mNumber = it.getInt(bundleKey, 0)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("debugTest","onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("debugTest","onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("debugTest","onPause")

    }

    override fun onStop() {
        super.onStop()
        Log.d("debugTest","onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("debugTest","onDestroy")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("debugTest","onDestroyView")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("debugTest","onDetach")
    }
}