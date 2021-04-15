package com.rivaldofez.cubihub.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailUser(
    val login: String,
    val name: String,
    val type: String,
    val id: Int,
    val public_repos: Int,
    val followers: Int,
    val following: Int,
    val following_url: String,
    val followers_url: String,
    val avatar_url: String,
    val html_url: String?,
    val location: String,
    val company: String?,
    val node_id: String?,
    val gravatar_id: String?,
    val url: String?,
    val gists_url: String?,
    val starred_url: String?,
    val subscriptions_url: String?,
    val organizations_url: String?,
    val events_url: String?,
    val received_events_url: String?,
    val site_admin: Boolean?,
    val blog: String?,
    val email: String?,
    val hireable: String?,
    val bio: String?,
    val twitter_username: String?,
    val public_gists: Int?,
    val repos_url: String?,
    val updated_at: String?,
    val created_at: String?,
) : Parcelable