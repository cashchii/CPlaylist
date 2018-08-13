package com.cashchii.production.cplaylist.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by Panc. on 8/11/2018 AD.
 */
@Entity
data class ItemModel(@PrimaryKey(autoGenerate = true) var id: Int = 0,
                     @SerializedName("title") var title: String? = null,
                     @SerializedName("link") var link: String? = null,
                     @SerializedName("thumb") var thumbImg: String? = null,
                     var isPlaying: Boolean = false,
                     var isEnding: Boolean = true,
                     var currentMins: Int = 0) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(link)
        parcel.writeString(thumbImg)
        parcel.writeByte(if (isPlaying) 1 else 0)
        parcel.writeByte(if (isEnding) 1 else 0)
        parcel.writeInt(currentMins)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItemModel> {
        override fun createFromParcel(parcel: Parcel): ItemModel {
            return ItemModel(parcel)
        }

        override fun newArray(size: Int): Array<ItemModel?> {
            return arrayOfNulls(size)
        }
    }
}