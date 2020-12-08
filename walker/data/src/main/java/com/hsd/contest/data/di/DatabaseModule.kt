package com.hsd.contest.data.di

import androidx.room.Room
import com.hsd.contest.data.database.AppDatabase
import com.hsd.contest.data.database.SportDatabase
import com.hsd.contest.data.database.SportDatabaseImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

class DatabaseModule {

    fun getModule() =  module{

        single { Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "quotes_database").build() }

        single { get<AppDatabase>().sportDao() }
        single { get<AppDatabase>().timeSeqDataDao() }

        single<SportDatabase> { SportDatabaseImpl(get()) }

    }
}