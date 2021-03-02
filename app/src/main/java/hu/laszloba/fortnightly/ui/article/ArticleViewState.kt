package hu.laszloba.fortnightly.ui.article

import hu.laszloba.fortnightly.model.ArticlePresentationModel

sealed class ArticleViewState

object Loading : ArticleViewState()

object Error : ArticleViewState()

data class ArticleLoaded(
    val article: ArticlePresentationModel
) : ArticleViewState()
