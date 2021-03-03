package hu.laszloba.fortnightly.data.database

interface BaseNewsTuple : BaseTuple {
    val title: String
    val urlToImage: String?
}
