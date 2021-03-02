package hu.laszloba.fortnightly.model

data class ArticlePresentationModel(
    override val title: String,
    override val urlToImage: String,
    val description: String
) : BaseNewsPresentationModel
