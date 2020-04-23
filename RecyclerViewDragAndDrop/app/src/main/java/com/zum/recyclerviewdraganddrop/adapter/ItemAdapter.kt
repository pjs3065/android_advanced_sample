package com.zum.recyclerviewdraganddrop.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zum.recyclerviewdraganddrop.holder.ItemHolder
import com.zum.recyclerviewdraganddrop.util.ItemMoveCallback
import java.util.*

class ItemAdapter : RecyclerView.Adapter<ItemHolder>(),
    ItemMoveCallback.ItemTouchHelperAdapter {

    private var items = mutableListOf<String>()

    fun setItems(items:MutableList<String>){
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.shared(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun onItemMove(fromPos: Int, targetPos: Int) {
        if(fromPos < targetPos){
            for(index in fromPos until targetPos){
                Collections.swap(items, index, index + 1)
            }
        }

        else{
            for(index in fromPos downTo targetPos){
                Collections.swap(items, index, index - 1)
            }
        }
        notifyItemMoved(fromPos, targetPos)
    }

    override fun onItemDismiss(pos: Int) {
        items.removeAt(pos)
        notifyItemRemoved(pos)
    }

    override fun getItemCount(): Int = items.size
}