package hu.laszloba.fortnightly.model

sealed class NewsListItemPresentationModel : BaseNewsListItemPresentationModel

data class LargeNewsListItemPresentationModel(
    override val id: Long,
    override val title: String,
    override val urlToImage: String,
    override val hoursAgo: Int
) : NewsListItemPresentationModel()

data class SmallNewsListItemPresentationModel(
    override val id: Long,
    override val title: String,
    override val urlToImage: String,
    override val hoursAgo: Int
) : NewsListItemPresentationModel()

private interface BaseNewsListItemPresentationModel : BaseNewsPresentationModel {
    val id: Long
    override val title: String
    override val urlToImage: String
    val hoursAgo: Int
}
