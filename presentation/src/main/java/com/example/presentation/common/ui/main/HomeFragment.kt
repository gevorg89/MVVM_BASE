package com.example.presentation.common.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.domain.common.ResultState
import com.example.domain.entity.Question
import com.example.presentation.R
import com.example.presentation.common.common.extension.applyIoScheduler
import com.example.presentation.common.common.extension.observe
import com.example.presentation.common.ui.base.BaseFragment
import com.example.presentation.common.ui.utils.LeftRightSpacingItemDecoration
import com.example.presentation.common.ui.utils.TopBottomSpacingItemDecoration
import kotlinx.android.synthetic.main.layout_home.*
import javax.inject.Inject

class HomeFragment : BaseFragment() {

    @Inject
    lateinit var viewModel: HomeViewModel
    override fun layout() = R.layout.layout_home

    private val adapter: QuestionAdapter by lazy {
        QuestionAdapter().apply {
            itemClickEvent.applyIoScheduler().subscribe { it ->
                showDetail(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.invalidate()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observe(viewModel.questionsLiveData, ::showQuestions)
        observe(viewModel.networkErrors, ::networkError)
        viewModel.getQuestions()
    }


    private fun initView() {
        recycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        recycler.addItemDecoration(LeftRightSpacingItemDecoration(50))
        recycler.addItemDecoration(TopBottomSpacingItemDecoration(50))
        recycler.adapter = adapter

        swipeToRefresh.setOnRefreshListener {
            viewModel.invalidate()
        }
    }

    private fun networkError(throwable: Throwable?) {
        Toast.makeText(activity, throwable?.message, Toast.LENGTH_SHORT).show()
    }

    private fun showDetail(question: Question?) {
        question?.let {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToQuestionDetailFragment())
        }
    }

    private fun showQuestions(questions: ResultState<PagedList<Question>>) {
        when (questions) {
            is ResultState.Success -> {
                adapter.submitList(questions.data)
            }
            is ResultState.Error -> {
                Toast.makeText(activity, questions.throwable.message, Toast.LENGTH_SHORT).show()
                adapter.submitList(questions.data)
            }
            is ResultState.Loading -> {
                adapter.submitList(questions.data)
            }
        }
        swipeToRefresh.isRefreshing = false
    }
}