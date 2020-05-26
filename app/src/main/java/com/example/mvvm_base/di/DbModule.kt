package com.example.mvvm_base.di

import android.content.Context
import androidx.room.Room
import com.example.data.db.MyDatabase
import dagger.Module
import dagger.Provides
import ir.hosseinabbasi.mvvm.di.qualifier.ApplicationContext
import javax.inject.Singleton

@Module
class DbModule {

    @Singleton
    @Provides
    fun provideMyDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, MyDatabase::class.java, "mydatabase")
            .build()

    @Singleton
    @Provides
    fun provideQuestionDao(myDatabase: MyDatabase) = myDatabase.questionDao()
}