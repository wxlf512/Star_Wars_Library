package dev.wxlf.starwarslibrary.core.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import dev.wxlf.starwarslibrary.core.data.room.entities.FavoriteEntity
import dev.wxlf.starwarslibrary.core.util.SearchType

@Dao
interface FavoritesDao {

    @Insert(FavoriteEntity::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(favoriteEntity: FavoriteEntity)

    @Delete(FavoriteEntity::class)
    suspend fun deleteFavorite(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM FAVORITE_TABLE WHERE type = :type")
    suspend fun loadFavorites(type: String): List<FavoriteEntity>

    @Transaction
    suspend fun loadFavorites(type: SearchType): List<FavoriteEntity> {
        return loadFavorites(type.name)
    }
}