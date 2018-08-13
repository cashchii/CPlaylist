package com.cashchii.production.cplaylist.manager

import com.cashchii.production.cplaylist.model.PlaylistModel
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Panc. on 8/11/2018 AD.
 */
interface ApiService {
    @GET("playlists")
    fun getPlaylist(): Single<Response<PlaylistModel>>

}