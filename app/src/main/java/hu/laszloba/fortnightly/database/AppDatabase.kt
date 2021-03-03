package hu.laszloba.fortnightly.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hu.laszloba.fortnightly.BuildConfig
import hu.laszloba.fortnightly.data.database.ArticleDataModel
import hu.laszloba.fortnightly.data.database.DateConverter

@Database(
    entities = [
        ArticleDataModel::class
    ],
    version = BuildConfig.DATABASE_VERSION
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao
}
