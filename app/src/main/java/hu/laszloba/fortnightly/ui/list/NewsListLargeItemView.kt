package hu.laszloba.fortnightly.ui.list

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import hu.laszloba.fortnightly.R
import hu.laszloba.fortnightly.databinding.ListItemNewsLargeBinding
import hu.laszloba.fortnightly.model.NewsListItemPresentationModel

// TODO similar to NewsListSmallItemView
class NewsListLargeItemView : ConstraintLayout, BaseNewsListItemView {

    override var onItemClickedListener: NewsListAdapter.OnItemClickedListener? = null

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
        setPadding(paddingLeft, padding, paddingRight, padding)
    }

    override fun bind(model: NewsListItemPresentationModel) {
        with(binding) {
            Glide.with(context)
                .load(model.urlToImage)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageImageView)

            hoursAgoTextView.text =
                context.getString(R.string.news_list_item_hours_ago, model.hoursAgo)
            titleTextView.text = model.title

            root.setOnClickListener {
                onItemClickedListener?.onItemClicked(model)
            }
        }
    }
}
