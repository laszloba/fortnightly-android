package hu.laszloba.fortnightly.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import hu.laszloba.fortnightly.data.database.ArticleDataModel
import hu.laszloba.fortnightly.data.database.ArticleTuple
import hu.laszloba.fortnightly.data.database.NewsListItemTuple

@Dao
interface NewsDao {

    @Query("SELECT id, title, urlToImage, publishedAt FROM ARTICLE ORDER BY publishedAt DESC")
    suspend fun getNews(): List<NewsListItemTuple>

    @Query("SELECT title, urlToImage, description FROM Article WHERE id = :articleId")
    suspend fun getArticle(articleId: Long): ArticleTuple?

    @Insert
    suspend fun insertArticles(articles: List<ArticleDataModel>)

    @Query("DELETE FROM Article")
    suspend fun clearArticles()
}
