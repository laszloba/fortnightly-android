package hu.laszloba.fortnightly.ui.list

import hu.laszloba.fortnightly.model.NewsListItemPresentationModel

interface BaseNewsListItemView {

    var onItemClickedListener: NewsListAdapter.OnItemClickedListener?

    fun bind(model: NewsListItemPresentationModel)
}
