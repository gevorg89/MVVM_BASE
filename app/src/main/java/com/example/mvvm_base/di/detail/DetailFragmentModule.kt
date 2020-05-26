package com.example.mvvm_base.di.detail

import com.example.presentation.common.ui.questiondetail.QuestionDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DetailFragmentModule {

    @ContributesAndroidInjector
    abstract fun detailFragment(): QuestionDetailFragment
}