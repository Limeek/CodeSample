package ru.limeek.codesample.domain.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat

@Parcelize
data class GithubRepo(
    val id: Long,
    val name: String,
    val owner: RepoOwner?,
    val fork: Boolean,
    @SerializedName("created_at") private val createdAt: String,
    val language: String,
    val description: String,
    val archived: Boolean,
    @SerializedName("stargazers_count") val stars: Int
) : Parcelable{
    val creationDate: DateTime
        get() {
            val isoFormatter = ISODateTimeFormat.dateTimeParser()
            return DateTime.parse(createdAt, isoFormatter)
        }
}