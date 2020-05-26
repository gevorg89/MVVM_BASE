package com.example.presentation.common.ui.base

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

open class BaseDiffUtil<T> : DiffUtil.ItemCallback<T>() {

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem

}