package com.zum.recyclerviewdraganddrop.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zum.recyclerviewdraganddrop.util.MainDividerItemDecoration

@BindingAdapter("adapter", "manager", requireAll = true)
fun setAdapter(
    recyclerView: RecyclerView,
    adapter: RecyclerView.Adapter<*>,
    manager: LinearLayoutManager
) {
    recyclerView.adapter = adapter
    recyclerView.layoutManager = manager
}
@BindingAdapter("decoration")
fun setItemDecoration(view:RecyclerView, direction:Int?){
    view.addItemDecoration(
        MainDividerItemDecoration(
            view.context
        )
    )
}