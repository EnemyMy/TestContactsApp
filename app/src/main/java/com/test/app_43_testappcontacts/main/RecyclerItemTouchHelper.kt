package com.test.app_43_testappcontacts.main

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class RecyclerItemTouchHelper (private val listener: RecyclerItemTouchHelperListener, dragDirs: Int, swipeDirs: Int): ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = true

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val foreground = (viewHolder as MainFragmentRecyclerAdapter.RecyclerHolder).binding.mainRecyclerItemForeground
        getDefaultUIUtil().onDraw(c, recyclerView, foreground, dX, dY, actionState, isCurrentlyActive)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        val foreground = (viewHolder as MainFragmentRecyclerAdapter.RecyclerHolder).binding.mainRecyclerItemForeground
        getDefaultUIUtil().clearView(foreground)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (viewHolder == null)
            return
        val foreground = (viewHolder as MainFragmentRecyclerAdapter.RecyclerHolder).binding.mainRecyclerItemForeground
        getDefaultUIUtil().onSelected(foreground)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        listener.onSwiped(viewHolder, direction, viewHolder.adapterPosition)
    }

    override fun onChildDrawOver(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder?,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val foreground = (viewHolder as MainFragmentRecyclerAdapter.RecyclerHolder).binding.mainRecyclerItemForeground
        getDefaultUIUtil().onDrawOver(c, recyclerView, foreground, dX, dY, actionState, isCurrentlyActive)
    }

    interface RecyclerItemTouchHelperListener {
        fun onSwiped(
            viewHolder: RecyclerView.ViewHolder?,
            direction: Int,
            position: Int
        )
    }
}