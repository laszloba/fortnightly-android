package hu.laszloba.fortnightly.ui.list

import hu.laszloba.fortnightly.model.NewsListItemPresentationModel

interface BaseNewsListItemView {

    fun bind(model: NewsListItemPresentationModel)
}
