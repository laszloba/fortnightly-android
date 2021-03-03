package hu.laszloba.fortnightly.data.mapper.database.topresentation

import hu.laszloba.fortnightly.data.database.NewsListItemTuple
import hu.laszloba.fortnightly.model.LargeNewsListItemPresentationModel
import hu.laszloba.fortnightly.model.NewsListItemPresentationModel
import hu.laszloba.fortnightly.model.SmallNewsListItemPresentationModel
import java.util.Date
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.round

class NewsListItemTupleToPresentationModelMapper @Inject constructor() :
    TupleToPresentationModelMapper<NewsListItemTuple, NewsListItemPresentationModel>() {

    companion object {
        private const val ONE_HOUR_IN_MILLISECONDS = 3_600_000.0
    }

    @Deprecated(
        message = "Use custom mapper method",
        level = DeprecationLevel.ERROR
    )
    override fun map(model: NewsListItemTuple): NewsListItemPresentationModel {
        throw UnsupportedOperationException()
    }

    fun mapToLargeListItem(model: NewsListItemTuple) =
        LargeNewsListItemPresentationModel(
            id = model.id,
            title = model.title,
            urlToImage = model.urlToImage,
            hoursAgo = getHoursAgo(model.publishedAt)
        )

    fun mapToSmallListItem(model: NewsListItemTuple) =
        SmallNewsListItemPresentationModel(
            id = model.id,
            title = model.title,
            urlToImage = model.urlToImage,
            hoursAgo = getHoursAgo(model.publishedAt)
        )

    private fun getHoursAgo(date: Date): Int {
        val timeAgoInMillis = Date().time - date.time

        return max(1.0, round(timeAgoInMillis / ONE_HOUR_IN_MILLISECONDS)).toInt()
    }
}
