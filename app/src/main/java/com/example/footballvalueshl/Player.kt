package com.example.footballvalueshl

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Player(val name:String?=null, val picture:String?=null, val price:String?=null) :Serializable, Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Player> {
        override fun createFromParcel(parcel: Parcel): Player {
            return Player(parcel)
        }

        override fun newArray(size: Int): Array<Player?> {
            return arrayOfNulls(size)
        }
    }
}
