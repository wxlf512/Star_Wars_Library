package dev.wxlf.starwarslibrary.core.data.datasources.local

import dev.wxlf.starwarslibrary.core.data.room.entities.FavoriteEntity
import dev.wxlf.starwarslibrary.core.util.SearchType

interface SWLocalDataSource {

    suspend fun addToFavorites(favoriteEntity: FavoriteEntity)
    suspend fun deleteFromFavorites(favoriteEntity: FavoriteEntity)
    suspend fun loadFavorites(type: SearchType): List<FavoriteEntity>

}