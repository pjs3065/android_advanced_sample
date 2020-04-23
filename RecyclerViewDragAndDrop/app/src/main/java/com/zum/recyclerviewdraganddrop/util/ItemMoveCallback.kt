package com.zum.recyclerviewdraganddrop.util

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ItemMoveCallback: ItemTouchHelper.Callback() {

    private var adapter: ItemTouchHelperAdapter? = null

    fun addAdapter(adapter: ItemTouchHelperAdapter){
        this.adapter = adapter
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
//        val flagDrag = ItemTouchHelper.START or ItemTouchHelper.END
//        val flagSwipe = ItemTouchHelper.UP or ItemTouchHelper.DOWN

        //드래그 위와 아래로 가능
        val flagDrag = ItemTouchHelper.UP or ItemTouchHelper.DOWN

        //스와이프는 왼쪽과 오른쪽 가능
        val flagSwipe = ItemTouchHelper.START or ItemTouchHelper.END

        //드래그 앤 스와이프 사용
        return makeMovementFlags(flagDrag, flagSwipe)

        // 스와이프 액션 중지
//        return makeMovementFlags(flagDrag, 0)

        // 드래그 액션 중지
//        return makeMovementFlags(0, flagSwipe)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        //adapter 의 onItemMove() 를 호출한다.
        adapter?.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        //adapter 의 onItemDismiss() 를 호출한다.
        adapter?.onItemDismiss(viewHolder.adapterPosition)
    }

    //롱터치 허용
    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    //스와이프 사용 허용
    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    interface ItemTouchHelperAdapter{
        fun onItemMove(fromPos:Int, targetPos:Int)
        fun onItemDismiss(pos:Int)
    }
}