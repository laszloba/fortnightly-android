package hu.laszloba.fortnightly.model

interface BaseNewsPresentationModel : BasePresentationModel {
    val title: String
    val urlToImage: String?
}
