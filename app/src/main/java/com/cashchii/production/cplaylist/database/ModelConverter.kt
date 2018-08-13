package com.cashchii.production.cplaylist.database

import android.arch.persistence.room.TypeConverter
import com.cashchii.production.cplaylist.model.ItemModel
import com.cashchii.production.cplaylist.model.PlaylistItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by Panc. on 8/11/2018 AD.
 */
class ModelConverter {
    @TypeConverter
    fun stringToItem(json: String): ItemModel {
        return if (json == "null") {
            ItemModel()
        } else {
            val gson = Gson()
            val type = object : TypeToken<ItemModel>() {}.type
            gson.fromJson(json, type)
        }
    }

    @TypeConverter
    fun itemToString(itemModel: ItemModel?): String {
        return if (itemModel == null) {
            "null"
        } else {
            val gson = Gson()
            val type = object : TypeToken<ItemModel>() {}.type
            gson.toJson(itemModel, type)
        }
    }

    @TypeConverter
    fun stringToListItem(json: String): MutableList<ItemModel> {
        return if (json == "null") {
            mutableListOf()
        } else {
            val gson = Gson()
            val type = object : TypeToken<MutableList<ItemModel>>() {}.type
            gson.fromJson(json, type)
        }
    }

    @TypeConverter
    fun listItemToString(lists: MutableList<ItemModel>?): String {
        return if (lists == null) {
            "null"
        } else {
            val gson = Gson()
            val type = object : TypeToken<MutableList<ItemModel>>() {}.type
            gson.toJson(lists, type)
        }
    }

    @TypeConverter
    fun stringToListPlaylistItem(json: String): MutableList<PlaylistItem> {
        return if (json == "null") {
            mutableListOf()
        } else {
            val gson = Gson()
            val type = object : TypeToken<MutableList<PlaylistItem>>() {}.type
            gson.fromJson(json, type)
        }
    }

    @TypeConverter
    fun listPlaylistItemToString(lists: MutableList<PlaylistItem>?): String {
        return if (lists == null) {
            "null"
        } else {
            val gson = Gson()
            val type = object : TypeToken<MutableList<PlaylistItem>>() {}.type
            gson.toJson(lists, type)
        }
    }
}