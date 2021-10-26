package com.example.connectchat.models

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize

@SuppressLint("ParcelCreator")
@VersionedParcelize
class User (val uid: String, val username: String) : Parcelable {
    constructor(): this("","")

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("Not yet implemented")
    }
}