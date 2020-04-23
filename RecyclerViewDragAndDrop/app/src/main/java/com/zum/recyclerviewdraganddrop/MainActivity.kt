package com.zum.recyclerviewdraganddrop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.zum.recyclerviewdraganddrop.adapter.ItemAdapter
import com.zum.recyclerviewdraganddrop.databinding.ActivityMainBinding
import com.zum.recyclerviewdraganddrop.util.ItemMoveCallback

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding:ActivityMainBinding

    private val itemList = mutableListOf("1", "2", "3", "4", "5", "6", "7", "8")

    private val mAdapter by lazy {
        ItemAdapter()
    }

    private val itemMoveCallBack by lazy {
        ItemMoveCallback()
    }

    private val mLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        with(mBinding){
            layoutManager = mLayoutManager
            adapter = mAdapter
        }

        //어댑터 아이템 초기화
        mAdapter.setItems(itemList)

        //드래그 앤 드롭, 스와이프 설정
        itemMoveCallBack.addAdapter(mAdapter)

        val touchHelper = ItemTouchHelper(itemMoveCallBack)
        touchHelper.attachToRecyclerView(mBinding.recyclerViewMain)
    }
}
