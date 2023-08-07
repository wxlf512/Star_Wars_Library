package dev.wxlf.starwarslibrary.core.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.wxlf.starwarslibrary.core.data.room.entities.FavoriteEntity

@Database(
    entities = [FavoriteEntity::class],
    version = 1,
    exportSchema = true
)
abstract class SWDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}