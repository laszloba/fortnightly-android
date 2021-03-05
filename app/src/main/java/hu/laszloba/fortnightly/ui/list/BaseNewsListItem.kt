package hu.laszloba.fortnightly.ui.list

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import hu.laszloba.fortnightly.R
import hu.laszloba.fortnightly.extension.addRippleEffectOnClick
import hu.laszloba.fortnightly.model.NewsListItemPresentationModel

abstract class BaseNewsListItem : ConstraintLayout, BaseNewsListItemView {

    override var onItemClickedListener: NewsListAdapter.OnItemClickedListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
        super(context, attrs, defStyleAttr)

    init {
        layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )

        val padding = resources.getDimensionPixelSize(R.dimen.margin_padding_size_medium)
        setPadding(paddingLeft, padding, paddingRight, padding)

        addRippleEffectOnClick()
    }

    override fun bind(model: NewsListItemPresentationModel) {
        Glide.with(context)
            .load(model.urlToImage)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(getImageImageView())

        getHoursAgoTextView().text =
            context.getString(R.string.news_list_item_hours_ago, model.hoursAgo)

        getTitleTextView().text = model.title

        setOnClickListener {
            onItemClickedListener?.onItemClicked(model)
        }
    }

    abstract fun getImageImageView(): ImageView

    abstract fun getHoursAgoTextView(): TextView

    abstract fun getTitleTextView(): TextView
}
