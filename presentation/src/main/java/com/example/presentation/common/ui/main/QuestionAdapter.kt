package com.example.presentation.common.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.domain.entity.Question
import com.example.presentation.R
import com.example.presentation.common.ui.base.BaseDiffUtil
import com.example.presentation.common.ui.base.BasePageListAdapter
import com.example.presentation.common.ui.base.BaseViewHolder
import com.example.presentation.databinding.QuestionItemBinding

class QuestionAdapter :
    BasePageListAdapter<Question, QuestionAdapter.QuestionHolder, BaseDiffUtil<Question>>() {

    override fun getHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): QuestionHolder {
        return QuestionHolder(
            DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context),
                R.layout.question_item,
                parent,
                false
            ) as QuestionItemBinding
        )
    }

    inner class QuestionHolder(private val questionItemBinding: QuestionItemBinding) :
        BaseViewHolder<Question>(questionItemBinding.root) {
        override fun onBind(item: Question?) {
            with(questionItemBinding) {
                question = item
                item?.let {
                    root.setOnClickListener {
                        onItemClickSubject.onNext(item)
                    }
                }
                executePendingBindings()
            }
        }
    }
}