package hu.laszloba.fortnightly.data.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class ArticleApiModel(
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "content")
    val content: String?,
    @field:Json(name = "urlToImage")
    val urlToImage: String?,
    @field:Json(name = "publishedAt")
    val publishedAt: Date
) : BaseApiModel
