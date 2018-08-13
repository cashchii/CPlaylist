package com.cashchii.production.cplaylist.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by Panc. on 8/11/2018 AD.
 */
data class PlaylistItem(@SerializedName("list_title") var title: String? = null,
                        @SerializedName("list_items") var listItem: MutableList<ItemModel>? = null) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.createTypedArrayList(ItemModel)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeTypedList(listItem)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlaylistItem> {
        override fun createFromParcel(parcel: Parcel): PlaylistItem {
            return PlaylistItem(parcel)
        }

        override fun newArray(size: Int): Array<PlaylistItem?> {
            return arrayOfNulls(size)
        }
    }
}