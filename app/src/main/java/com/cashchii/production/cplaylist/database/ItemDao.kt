package com.cashchii.production.cplaylist.database

import android.arch.persistence.room.*
import com.cashchii.production.cplaylist.model.ItemModel
import com.cashchii.production.cplaylist.model.PlaylistModel

/**
 * Created by Panc. on 8/11/2018 AD.
 */
@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListItem(itemModel: MutableList<ItemModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlaylist(playlistModel: PlaylistModel)

    @Query("SELECT * FROM playlist")
    fun getPlaylistModel(): PlaylistModel

    @Query("SELECT * FROM ItemModel WHERE link = :linkUrl")
    fun getVideoItem(linkUrl: String): ItemModel

    @Update
    fun updateVideoItem(itemModel: ItemModel)
}