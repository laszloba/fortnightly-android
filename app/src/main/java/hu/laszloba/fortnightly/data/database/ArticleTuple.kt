package hu.laszloba.fortnightly.data.database

data class ArticleTuple(
    override val title: String,
    override val urlToImage: String?,
    val description: String?
) : BaseNewsTuple
