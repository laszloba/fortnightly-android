package hu.laszloba.fortnightly.ui.list

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import hu.laszloba.fortnightly.R
import hu.laszloba.fortnightly.databinding.ListItemNewsLargeBinding
import hu.laszloba.fortnightly.model.NewsListItemPresentationModel

// TODO similar to NewsListSmallItemView
class NewsListLargeItemView : ConstraintLayout, BaseNewsListItemView {

    private val binding =
        ListItemNewsLargeBinding.inflate(LayoutInflater.from(context), this)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
        super(context, attrs, defStyleAttr)

    init {
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        val padding = resources.getDimension(R.dimen.margin_padding_size_medium).toInt()
        setPadding(padding, padding, padding, padding)
    }

    override fun bind(model: NewsListItemPresentationModel) {
        with(binding) {
            timeAgoTextView.text = model.timeAgo
            titleTextView.text = model.title
        }
    }
}
