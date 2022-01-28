package com.csi.sample.paging.di

import android.content.Context
import androidx.room.Room
import com.csi.sample.paging.data.local.db.PagingDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): PagingDatabase {
        return Room.databaseBuilder(context, PagingDatabase::class.java, "paging.db").build()
    }
}