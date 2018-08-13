package com.cashchii.production.cplaylist.playlist.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.util.Log
import com.cashchii.production.cplaylist.common.Constant
import com.cashchii.production.cplaylist.database.DBHelper
import com.cashchii.production.cplaylist.model.ItemModel
import com.cashchii.production.cplaylist.model.PlaylistModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Panc. on 8/11/2018 AD.
 */
class PlaylistViewModel : ViewModel() {
    fun getPlaylist(context: Context): LiveData<PlaylistModel> {
        val mutableLiveData = MutableLiveData<PlaylistModel>()
        Single.fromCallable { DBHelper.getDB(context).itemDao().getPlaylistModel() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mutableLiveData.value = it
                }, {
                    Log.d(Constant.TAG, "getPlaylist failed with ${it.message}")
                })
        return mutableLiveData
    }

    fun getVideoItem(context: Context, linkUrl: String): LiveData<ItemModel> {
        val mutableLiveData = MutableLiveData<ItemModel>()
        Single.fromCallable { DBHelper.getDB(context).itemDao().getVideoItem(linkUrl) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mutableLiveData.value = it
                }, {
                    Log.d(Constant.TAG, "getVideoItem failed with ${it.message}")
                })
        return mutableLiveData
    }

    fun updateVideoItem(context: Context, itemModel: ItemModel) {
        Single.fromCallable { DBHelper.getDB(context).itemDao().updateVideoItem(itemModel) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(Constant.TAG, "updateVideoItem success")
                }, {
                    Log.d(Constant.TAG, "getVideoItem failed with ${it.message}")
                })
    }
}