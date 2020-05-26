package com.example.mvvm_base.di

import com.example.mvvm_base.MyApplication
import com.example.mvvm_base.di.detail.DetailModule
import com.example.mvvm_base.di.home.HomeModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ViewModelModule::class,
    AppModule::class,
    MainModule::class,
    HomeModule::class,
    DetailModule::class
])
interface AppComponent : AndroidInjector<MyApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MyApplication>()
}