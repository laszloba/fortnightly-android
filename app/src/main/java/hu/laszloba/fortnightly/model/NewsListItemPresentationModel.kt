package hu.laszloba.fortnightly.model

sealed class NewsListItemPresentationModel : BaseNewsListItemPresentationModel

data class LargeNewsListItemPresentationModel(
    override val id: Long,
    override val title: String,
    override val urlToImage: String,
    override val timeAgo: String
) : NewsListItemPresentationModel()

data class SmallNewsListItemPresentationModel(
    override val id: Long,
    override val title: String,
    override val urlToImage: String,
    override val timeAgo: String
) : NewsListItemPresentationModel()

private interface BaseNewsListItemPresentationModel : BaseNewsPresentationModel {
    val id: Long
    override val title: String
    override val urlToImage: String
    val timeAgo: String
}
