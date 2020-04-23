package com.zum.recyclerviewdraganddrop.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zum.recyclerviewdraganddrop.R
import kotlinx.android.synthetic.main.holder_item.view.*

class ItemHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    companion object {
        fun shared(parent: ViewGroup): ItemHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.holder_item, parent, false)
            return ItemHolder(view)
        }
    }

    fun onBind(str: String) {
        view.tv_name_cell.text = str
    }
}