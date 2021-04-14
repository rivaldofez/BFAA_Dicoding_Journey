package com.rivaldofez.cubihub.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
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
):Parcelable