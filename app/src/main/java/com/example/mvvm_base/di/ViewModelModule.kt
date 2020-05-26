package com.example.mvvm_base.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.presentation.common.ui.main.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ir.hosseinabbasi.mvvm.di.qualifier.ViewModelKey

/**
 * Created by Dr.jacky on 9/10/2018.
 */
@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel
}