package hu.laszloba.fortnightly.data.mapper.database.topresentation

import hu.laszloba.fortnightly.data.database.ArticleTuple
import hu.laszloba.fortnightly.model.ArticlePresentationModel
import javax.inject.Inject

class ArticleTupleToPresentationModelMapper @Inject constructor() :
    TupleToPresentationModelMapper<ArticleTuple, ArticlePresentationModel>() {

    override fun map(model: ArticleTuple): ArticlePresentationModel =
        ArticlePresentationModel(
            title = model.title,
            urlToImage = model.urlToImage,
            description = model.description
        )
}
