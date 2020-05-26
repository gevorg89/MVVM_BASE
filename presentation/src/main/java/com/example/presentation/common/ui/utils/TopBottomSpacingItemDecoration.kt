package com.example.presentation.common.ui.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TopBottomSpacingItemDecoration(
    private val spacing: Int
) :
    RecyclerView.ItemDecoration() {

    private var linearLayoutManager: LinearLayoutManager? = null

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (linearLayoutManager == null) {
            linearLayoutManager = parent.layoutManager as LinearLayoutManager
        }
        linearLayoutManager?.let {
            outRect.top = spacing
            outRect.bottom = spacing
        }

    }
}