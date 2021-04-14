package com.rivaldofez.cubihub.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class UserSearch(
    val login: String,
    val type: String,
    val html_url: String,
    val avatar_url: String,
    val id: Int,
    val node_id: String?,
    val gravatar_id: String?,
    val url: String?,
    val followers_url: String?,
    val following_url: String?,
    val gists_url: String?,
    val starred_url: String?,
    val subscriptions_url: String?,
    val organizations_url: String?,
    val repos_url: String?,
    val events_url: String?,
    val received_events_url: String?,
    val site_admin: Boolean?,
    val score:Int?,
):Parcelable