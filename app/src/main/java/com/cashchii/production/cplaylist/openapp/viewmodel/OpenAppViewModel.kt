package com.cashchii.production.cplaylist.openapp.viewmodel

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.Intent
import android.util.Log
import com.cashchii.production.cplaylist.common.Constant
import com.cashchii.production.cplaylist.database.DBHelper
import com.cashchii.production.cplaylist.manager.HttpManager
import com.cashchii.production.cplaylist.model.PlaylistItem
import com.cashchii.production.cplaylist.model.PlaylistModel
import com.cashchii.production.cplaylist.openapp.MainActivity
import com.cashchii.production.cplaylist.playlist.view.activity.PlaylistActivity
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Panc. on 8/11/2018 AD.
 */
class OpenAppViewModel : ViewModel() {

    fun loadingPlaylist(context: Context) {
        HttpManager(context) {}.service.getPlaylist()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.body()?.let { playListModel ->
                        insertPlaylist(context, playListModel)
                    }
                }, {
                    Log.d(Constant.TAG, "failed with ${it.message}")
                })
    }

    private fun insertPlaylist(context: Context, playlistModel: PlaylistModel) {
        Single.fromCallable { DBHelper.getDB(context).itemDao().insertPlaylist(playlistModel) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    insertItem(context, playlistModel.playlists)
                }, { Log.d(Constant.TAG, "insertPlaylist failed with ${it.message}") })

    }

    private fun insertItem(context: Context, playlists: MutableList<PlaylistItem>?) {
        val size = playlists!!.size
        for ((index, item) in playlists!!.iterator().withIndex()) {
            Single.fromCallable { DBHelper.getDB(context).itemDao().insertListItem(item.listItem!!) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (index == (size - 1)) {
                            Log.d(Constant.TAG, "Complete")
                            context.startActivity(Intent(context, PlaylistActivity::class.java))
                            (context as MainActivity).finish()
                        }
                    }, {
                        Log.d(Constant.TAG, "failed with ${it.message}")
                    })
        }
    }
}