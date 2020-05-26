package com.example.mvvm_base.di

import com.example.mvvm_base.di.home.HomeFragmentModule
import com.example.presentation.common.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [HomeFragmentModule::class])
abstract class MainModule {

    //@PerActivity
    @ContributesAndroidInjector
    abstract fun get(): MainActivity
}