package hu.laszloba.fortnightly.data.mapper.api.todatabase

import hu.laszloba.fortnightly.data.api.ArticleApiModel
import hu.laszloba.fortnightly.data.database.ArticleDataModel
import javax.inject.Inject

class NewsListItemApiModelToDataModelMapper @Inject constructor() :
    ApiModelToDataModelMapper<ArticleApiModel, ArticleDataModel>() {

    override fun map(model: ArticleApiModel): ArticleDataModel =
        ArticleDataModel(
            title = model.title,
            urlToImage = model.urlToImage,
            description = model.content,
            publishedAt = model.publishedAt
        )
}
