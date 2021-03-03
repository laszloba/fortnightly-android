package hu.laszloba.fortnightly.data.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopHeadlinesApiModel(
    @field:Json(name = "status")
    val status: String,
    @field:Json(name = "message")
    val message: String?,
    @field:Json(name = "articles")
    val articles: List<ArticleApiModel>
) : BaseApiModel
