package com.zum.recyclerviewdraganddrop.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.zum.recyclerviewdraganddrop.R

class MainDividerItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val mDivider: Drawable by lazy{
        ContextCompat.getDrawable(context,
            R.drawable.line_divider
        )!!
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for(index in 0 until childCount){
            val child = parent.getChildAt(index)
            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider.intrinsicHeight

            mDivider.run {
                bounds = Rect(left, top, right, bottom)
                mDivider.draw(c)
            }
        }
    }

}