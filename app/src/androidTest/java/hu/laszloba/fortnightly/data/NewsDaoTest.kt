package hu.laszloba.fortnightly.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import hu.laszloba.fortnightly.data.database.ArticleDataModel
import hu.laszloba.fortnightly.data.database.ArticleTuple
import hu.laszloba.fortnightly.data.database.NewsListItemTuple
import hu.laszloba.fortnightly.database.AppDatabase
import hu.laszloba.fortnightly.database.NewsDao
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Calendar

@RunWith(AndroidJUnit4::class)
class NewsDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var newsDao: NewsDao

    private val calendar1 = Calendar.getInstance()
        .apply {
            set(Calendar.YEAR, 2020)
            set(Calendar.MONTH, Calendar.JULY)
            set(Calendar.DAY_OF_MONTH, 4)
        }

    private val calendar2 = Calendar.getInstance()
        .apply {
            set(Calendar.YEAR, 2020)
            set(Calendar.MONTH, Calendar.JULY)
            set(Calendar.DAY_OF_MONTH, 3)
        }

    private val calendar3 = Calendar.getInstance()
        .apply {
            set(Calendar.YEAR, 2020)
            set(Calendar.MONTH, Calendar.JULY)
            set(Calendar.DAY_OF_MONTH, 1)
        }

    private val article1 =
        ArticleDataModel("title1", "https://url.to.image/1/", "content1", calendar1.time)
    private val article2 =
        ArticleDataModel("title2", "https://url.to.image/2/", "content2", calendar2.time)
    private val article3 =
        ArticleDataModel("title3", "https://url.to.image/3/", "content3", calendar3.time)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        newsDao = database.newsDao()

        // Insert news in non-date order to test that results are sorted by published date
        newsDao.insertArticles(listOf(article3, article1, article2))
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun getNews_orderedByPublishedAtDescending_success() = runBlocking {
        val newsList = newsDao.getNews()

        Assert.assertThat(newsList[0], equalTo(getListItemTuple(2, article1)))
        Assert.assertThat(newsList[1], equalTo(getListItemTuple(3, article2)))
        Assert.assertThat(newsList[2], equalTo(getListItemTuple(1, article3)))
    }

    private fun getListItemTuple(articleId: Long, model: ArticleDataModel) =
        NewsListItemTuple(
            id = articleId,
            title = model.title,
            urlToImage = model.urlToImage,
            publishedAt = model.publishedAt
        )

    @Test
    fun getArticle_success() = runBlocking {
        Assert.assertThat(
            newsDao.getArticle(1L),
            equalTo(getArticleTuple(article3))
        )
    }

    private fun getArticleTuple(model: ArticleDataModel) =
        ArticleTuple(
            title = model.title,
            urlToImage = model.urlToImage,
            description = model.description
        )

    @Test
    fun clearArticles_success() = runBlocking {
        newsDao.clearArticles()

        Assert.assertThat(newsDao.getNews().size, equalTo(0))
    }
}
