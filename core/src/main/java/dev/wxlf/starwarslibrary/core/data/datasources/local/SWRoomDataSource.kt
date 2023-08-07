package dev.wxlf.starwarslibrary.core.data.datasources.local

import dev.wxlf.starwarslibrary.core.data.room.FavoritesDao
import dev.wxlf.starwarslibrary.core.data.room.entities.FavoriteEntity
import dev.wxlf.starwarslibrary.core.util.SearchType

class SWRoomDataSource(private val favoritesDao: FavoritesDao) : SWLocalDataSource {
    override suspend fun addToFavorites(favoriteEntity: FavoriteEntity) =
        favoritesDao.insertFavorite(favoriteEntity)

    override suspend fun deleteFromFavorites(favoriteEntity: FavoriteEntity) =
        favoritesDao.deleteFavorite(favoriteEntity)

    override suspend fun loadFavorites(type: SearchType): List<FavoriteEntity> =
        favoritesDao.loadFavorites(type)
}