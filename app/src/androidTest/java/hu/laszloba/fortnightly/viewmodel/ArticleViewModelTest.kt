package hu.laszloba.fortnightly.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import hu.laszloba.fortnightly.data.database.ArticleDataModel
import hu.laszloba.fortnightly.data.database.ArticleTuple
import hu.laszloba.fortnightly.data.database.NewsListItemTuple
import hu.laszloba.fortnightly.data.mapper.database.topresentation.ArticleTupleToPresentationModelMapper
import hu.laszloba.fortnightly.database.NewsDao
import hu.laszloba.fortnightly.model.ArticlePresentationModel
import hu.laszloba.fortnightly.ui.article.ArticleLoaded
import hu.laszloba.fortnightly.ui.article.ArticleViewModel
import hu.laszloba.fortnightly.ui.article.Loading
import hu.laszloba.fortnightly.utilities.getValue
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import javax.inject.Inject

@HiltAndroidTest
class ArticleViewModelTest {

    private val hiltRule = HiltAndroidRule(this)
    private val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ArticleViewModel

    @get:Rule
    val rule = RuleChain
        .outerRule(hiltRule)
        .around(instantTaskExecutorRule)

    private val newsDao = object : NewsDao {
        override suspend fun getNews(): List<NewsListItemTuple> {
            throw UnsupportedOperationException()
        }

        override suspend fun getArticle(articleId: Long): ArticleTuple =
            ArticleTuple(
                title = "title1",
                urlToImage = "https://url.to.image/1/",
                description = "content1"
            )

        override suspend fun insertArticles(articles: List<ArticleDataModel>) {
            throw UnsupportedOperationException()
        }

        override suspend fun clearArticles() {
            throw UnsupportedOperationException()
        }
    }

    @Inject
    lateinit var articleTupleToPresentationModelMapper: ArticleTupleToPresentationModelMapper

    @Before
    fun setUp() = runBlocking {
        hiltRule.inject()

        viewModel = ArticleViewModel(newsDao, articleTupleToPresentationModelMapper)
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
    fun loadArticle_success() = runBlocking {
        viewModel.loadArticle(1L)

        Assert.assertEquals(
            ArticleLoaded(
                ArticlePresentationModel(
                    title = "title1",
                    urlToImage = "https://url.to.image/1/",
                    description = "content1"
                )
            ),
            getValue(viewModel.viewState)
        )
    }
}
