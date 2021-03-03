package hu.laszloba.fortnightly

import hu.laszloba.fortnightly.data.api.ArticleApiModel
import hu.laszloba.fortnightly.data.database.ArticleDataModel
import hu.laszloba.fortnightly.data.database.ArticleTuple
import hu.laszloba.fortnightly.data.database.NewsListItemTuple
import hu.laszloba.fortnightly.data.mapper.api.todatabase.NewsListItemApiModelToDataModelMapper
import hu.laszloba.fortnightly.data.mapper.database.topresentation.ArticleTupleToPresentationModelMapper
import hu.laszloba.fortnightly.data.mapper.database.topresentation.NewsListItemTupleToPresentationModelMapper
import hu.laszloba.fortnightly.model.ArticlePresentationModel
import hu.laszloba.fortnightly.model.LargeNewsListItemPresentationModel
import hu.laszloba.fortnightly.model.SmallNewsListItemPresentationModel
import org.junit.Assert
import org.junit.Test
import java.util.Calendar

class MapperTest {

    @Test
    fun newsListItemApiModelToDataModelMapper_map_success() {
        val calendar = Calendar.getInstance()
            .apply {
                set(Calendar.YEAR, 2020)
                set(Calendar.MONTH, Calendar.JULY)
                set(Calendar.DAY_OF_MONTH, 4)
            }

        val mapper = NewsListItemApiModelToDataModelMapper()

        Assert.assertEquals(
            ArticleDataModel(
                title = "title",
                urlToImage = "https://url.to.image/",
                description = "content",
                publishedAt = calendar.time
            ),
            mapper.map(
                ArticleApiModel(
                    title = "title",
                    urlToImage = "https://url.to.image/",
                    content = "content",
                    publishedAt = calendar.time
                )
            )
        )
    }

    @Test
    fun newsListItemTupleToPresentationModelMapper_mapToLargeListItem_success() {
        val calendar = Calendar.getInstance()
        val mapper = NewsListItemTupleToPresentationModelMapper()

        Assert.assertEquals(
            LargeNewsListItemPresentationModel(
                id = 1,
                title = "title",
                urlToImage = "https://url.to.image/",
                hoursAgo = 1
            ),
            mapper.mapToLargeListItem(
                NewsListItemTuple(
                    id = 1,
                    title = "title",
                    urlToImage = "https://url.to.image/",
                    publishedAt = calendar.time
                )
            )
        )
    }

    @Test
    fun newsListItemTupleToPresentationModelMapper_mapToSmallListItem_success() {
        val calendar = Calendar.getInstance()
        val mapper = NewsListItemTupleToPresentationModelMapper()

        Assert.assertEquals(
            SmallNewsListItemPresentationModel(
                id = 1,
                title = "title",
                urlToImage = "https://url.to.image/",
                hoursAgo = 1
            ),
            mapper.mapToSmallListItem(
                NewsListItemTuple(
                    id = 1,
                    title = "title",
                    urlToImage = "https://url.to.image/",
                    publishedAt = calendar.time
                )
            )
        )
    }

    @Test
    fun newsListItemTupleToPresentationModelMapper_hoursAgo_success() {
        val calendar = Calendar.getInstance()
            .apply {
                add(Calendar.HOUR, -3)
            }
        val mapper = NewsListItemTupleToPresentationModelMapper()

        Assert.assertEquals(
            SmallNewsListItemPresentationModel(
                id = 1,
                title = "title",
                urlToImage = "https://url.to.image/",
                hoursAgo = 3
            ),
            mapper.mapToSmallListItem(
                NewsListItemTuple(
                    id = 1,
                    title = "title",
                    urlToImage = "https://url.to.image/",
                    publishedAt = calendar.time
                )
            )
        )
    }

    @Test
    fun articleTupleToPresentationModelMapper_map_success() {
        val mapper = ArticleTupleToPresentationModelMapper()

        Assert.assertEquals(
            ArticlePresentationModel(
                title = "title",
                urlToImage = "https://url.to.image/",
                description = "content"
            ),
            mapper.map(
                ArticleTuple(
                    title = "title",
                    urlToImage = "https://url.to.image/",
                    description = "content"
                )
            )
        )
    }
}
