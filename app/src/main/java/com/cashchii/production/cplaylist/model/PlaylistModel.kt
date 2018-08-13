package com.cashchii.production.cplaylist.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by Panc. on 8/11/2018 AD.
 */
@Entity(tableName = "playlist")
data class PlaylistModel(@PrimaryKey(autoGenerate = true) var id: Int = 0,
                         @SerializedName("playlists") var playlists: MutableList<PlaylistItem>? = null) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.createTypedArrayList(PlaylistItem)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeTypedList(playlists)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlaylistModel> {
        override fun createFromParcel(parcel: Parcel): PlaylistModel {
            return PlaylistModel(parcel)
        }

        override fun newArray(size: Int): Array<PlaylistModel?> {
            return arrayOfNulls(size)
        }
    }

}