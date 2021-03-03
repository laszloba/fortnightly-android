package hu.laszloba.fortnightly.data.database

import androidx.room.Entity
import java.util.Date

@Entity(
    tableName = "Article"
)
data class ArticleDataModel(
    val title: String,
    val urlToImage: String?,
    val description: String?,
    val publishedAt: Date
) : BaseDataModel()
