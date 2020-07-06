package ru.limeek.codesample.domain.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RepoOwner(val login: String,
                     @SerializedName("avatar_url") val avatarUrl: String): Parcelable