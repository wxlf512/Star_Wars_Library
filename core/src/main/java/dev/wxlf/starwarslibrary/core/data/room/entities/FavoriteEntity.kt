package dev.wxlf.starwarslibrary.core.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.wxlf.starwarslibrary.core.data.room.entities.FavoriteEntity.Companion.FAVORITE_TABLE

@Entity(tableName = FAVORITE_TABLE)
data class FavoriteEntity(
    @PrimaryKey
    @ColumnInfo("url") val url: String,
    @ColumnInfo("type") val type: String
) {
    companion object {
        const val FAVORITE_TABLE = "favorite_table"
    }
}
