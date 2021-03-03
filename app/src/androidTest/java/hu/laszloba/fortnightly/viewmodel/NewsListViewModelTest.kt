package hu.laszloba.fortnightly.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import hu.laszloba.fortnightly.api.NewsService
import hu.laszloba.fortnightly.data.api.TopHeadlinesApiModel
import hu.laszloba.fortnightly.data.database.ArticleDataModel
import hu.laszloba.fortnightly.data.database.ArticleTuple
import hu.laszloba.fortnightly.data.database.NewsListItemTuple
import hu.laszloba.fortnightly.data.mapper.api.todatabase.NewsListItemApiModelToDataModelMapper
import hu.laszloba.fortnightly.data.mapper.database.topresentation.NewsListItemTupleToPresentationModelMapper
import hu.laszloba.fortnightly.database.NewsDao
import hu.laszloba.fortnightly.model.LargeNewsListItemPresentationModel
import hu.laszloba.fortnightly.model.SmallNewsListItemPresentationModel
import hu.laszloba.fortnightly.ui.list.Error
import hu.laszloba.fortnightly.ui.list.Loading
import hu.laszloba.fortnightly.ui.list.NewsListLoaded
import hu.laszloba.fortnightly.ui.list.NewsListViewModel
import hu.laszloba.fortnightly.utilities.getValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import retrofit2.Response
import java.util.Calendar
import javax.inject.Inject

@HiltAndroidTest
class NewsListViewModelTest {

    private val hiltRule = HiltAndroidRule(this)
    private val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: NewsListViewModel

    @get:Rule
    val rule: RuleChain = RuleChain
        .outerRule(hiltRule)
        .around(instantTaskExecutorRule)

    @Inject
    lateinit var newsListItemApiModelToDataModelMapper: NewsListItemApiModelToDataModelMapper

    @Inject
    lateinit var newsListItemTupleToPresentationModelMapper: NewsListItemTupleToPresentationModelMapper

    private val newsService = object : NewsService {
        override suspend fun getTopHeadlines(): Response<TopHeadlinesApiModel> =
            Response.success(
                TopHeadlinesApiModel(
                    status = "ok",
                    message = null,
                    articles = listOf()
                )
            )
    }

    private val newsDao = object : NewsDao {
        override suspend fun getNews(): List<NewsListItemTuple> =
            listOf(
                NewsListItemTuple(
                    id = 1L,
                    title = "title1",
                    urlToImage = "https://url.to.image/1/",
                    publishedAt = Calendar.getInstance().time
                ),
                NewsListItemTuple(
                    id = 2L,
                    title = "title2",
                    urlToImage = "https://url.to.image/2/",
                    publishedAt = Calendar.getInstance()
                        .apply {
                            add(Calendar.HOUR, -2)
                        }.time
                ),
                NewsListItemTuple(
                    id = 3L,
                    title = "title3",
                    urlToImage = "https://url.to.image/3/",
                    publishedAt = Calendar.getInstance()
                        .apply {
                            add(Calendar.HOUR, -3)
                        }.time
                )
            )

        override suspend fun getArticle(articleId: Long): ArticleTuple {
            throw UnsupportedOperationException()
        }

        override suspend fun insertArticles(articles: List<ArticleDataModel>) {
            // no-op
        }

        override suspend fun clearArticles() {
            // no-op
        }
    }

    @Before
    fun setUp() = runBlocking {
        hiltRule.inject()

        viewModel = NewsListViewModel(
            newsService,
            newsDao,
            newsListItemApiModelToDataModelMapper,
            newsListItemTupleToPresentationModelMapper
        )
    }

    @Test
    @Throws(InterruptedException::class)
    fun viewState_defaultValue_success() = runBlocking {
        Assert.assertEquals(
            Loading,
            getValue(viewModel.viewState)
        )
    }

    @Test
    @Throws(InterruptedException::class)
    fun loadNewsList_success() = runBlocking {
        viewModel.loadNewsList()
        delay(500L)

        Assert.assertEquals(
            NewsListLoaded(
                listOf(
                    LargeNewsListItemPresentationModel(
                        id = 1L,
                        title = "title1",
                        urlToImage = "https://url.to.image/1/",
                        hoursAgo = 1
                    ),
                    SmallNewsListItemPresentationModel(
                        id = 2L,
                        title = "title2",
                        urlToImage = "https://url.to.image/2/",
                        hoursAgo = 2
                    ),
                    SmallNewsListItemPresentationModel(
                        id = 3L,
                        title = "title3",
                        urlToImage = "https://url.to.image/3/",
                        hoursAgo = 3
                    )
                )
            ),
            getValue(viewModel.viewState)
        )
    }

    @Test
    @Throws(InterruptedException::class)
    fun loadNewsList_badResponse_error() = runBlocking {
        val badStatusCodeNewsService = object : NewsService {
            override suspend fun getTopHeadlines(): Response<TopHeadlinesApiModel> =
                Response.error(
                    404,
                    ResponseBody.create(
                        MediaType.parse("application/json"),
                        "{\"message\":\"Not found\"}"
                    )
                )
        }

        viewModel = NewsListViewModel(
            badStatusCodeNewsService,
            newsDao,
            newsListItemApiModelToDataModelMapper,
            newsListItemTupleToPresentationModelMapper
        )

        viewModel.loadNewsList()

        delay(500L)

        Assert.assertEquals(
            Error("Bad response: HTTP 404"),
            getValue(viewModel.viewState)
        )
    }

    @Test
    @Throws(InterruptedException::class)
    fun loadNewsList_emptyBody_error() = runBlocking {
        val emptyBodyNewsService = object : NewsService {
            override suspend fun getTopHeadlines(): Response<TopHeadlinesApiModel> =
                Response.success(null)
        }

        viewModel = NewsListViewModel(
            emptyBodyNewsService,
            newsDao,
            newsListItemApiModelToDataModelMapper,
            newsListItemTupleToPresentationModelMapper
        )

        viewModel.loadNewsList()

        delay(500L)

        Assert.assertEquals(
            Error("Response body is empty"),
            getValue(viewModel.viewState)
        )
    }

    @Test
    @Throws(InterruptedException::class)
    fun loadNewsList_badApiStatusMessage_error() = runBlocking {
        val badApiStatusMessageNewsService = object : NewsService {
            override suspend fun getTopHeadlines(): Response<TopHeadlinesApiModel> =
                Response.success(
                    TopHeadlinesApiModel(
                        status = "error",
                        message = "something happened",
                        articles = listOf()
                    )
                )
        }

        viewModel = NewsListViewModel(
            badApiStatusMessageNewsService,
            newsDao,
            newsListItemApiModelToDataModelMapper,
            newsListItemTupleToPresentationModelMapper
        )

        viewModel.loadNewsList()

        delay(500L)

        Assert.assertEquals(
            Error("Bad API status: error, message: something happened"),
            getValue(viewModel.viewState)
        )
    }
}
