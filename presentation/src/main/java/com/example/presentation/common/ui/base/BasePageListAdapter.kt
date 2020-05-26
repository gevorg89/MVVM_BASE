package com.example.presentation.common.ui.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject

abstract class BasePageListAdapter<T, VH : BaseViewHolder<T>, D : BaseDiffUtil<T>>(
    private val diffUtil: BaseDiffUtil<T> = BaseDiffUtil()
) : PagedListAdapter<T, VH>(diffUtil) {
    val onItemClickSubject = PublishSubject.create<T>()
    val itemClickEvent: Observable<T> = onItemClickSubject

    private lateinit var context: Context
    private fun layoutInflater(): LayoutInflater = LayoutInflater.from(context)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    abstract fun getHolder(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): VH

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return getHolder(layoutInflater(), parent, viewType)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        getItem(position)?.let {
            holder.onBind(it)
        }
    }
}