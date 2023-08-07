package dev.wxlf.starwarslibrary.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.wxlf.starwarslibrary.core.R
import dev.wxlf.starwarslibrary.core.data.room.FavoritesDao
import dev.wxlf.starwarslibrary.core.data.room.SWDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun provideSWDatabase(@ApplicationContext context: Context): SWDatabase =
        Room.databaseBuilder(
            context = context,
            SWDatabase::class.java,
            context.getString(R.string.sw_database)
        ).build()

    @Provides
    @Singleton
    fun provideFavoritesDao(swDatabase: SWDatabase): FavoritesDao = swDatabase.favoritesDao()
}