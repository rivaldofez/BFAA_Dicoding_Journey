package com.rivaldofez.cubihub.model

import android.os.Parcel
import android.os.Parcelable

data class User(
    var username: String?,
    var fullname:String?,
    var location:String?,
    var address:String?,
    var biography:String?,
    var phone:String?,
    var email:String?,
    var avatar:String?,
    var num_repository:Int?,
    var num_follower:Int?,
    var num_following:Int?,
    var num_likes:Int?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(username)
        parcel.writeString(fullname)
        parcel.writeString(location)
        parcel.writeString(address)
        parcel.writeString(biography)
        parcel.writeString(phone)
        parcel.writeString(email)
        parcel.writeString(avatar)
        parcel.writeValue(num_repository)
        parcel.writeValue(num_follower)
        parcel.writeValue(num_following)
        parcel.writeValue(num_likes)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}