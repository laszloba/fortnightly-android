package hu.laszloba.fortnightly.data.database

import java.util.Date

data class NewsListItemTuple(
    val id: Long,
    override val title: String,
    override val urlToImage: String?,
    val publishedAt: Date
) : BaseNewsTuple
