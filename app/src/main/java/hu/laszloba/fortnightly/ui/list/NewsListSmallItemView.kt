package hu.laszloba.fortnightly.ui.list

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import hu.laszloba.fortnightly.databinding.ListItemNewsSmallBinding

class NewsListSmallItemView : BaseNewsListItem {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
        super(context, attrs, defStyleAttr)

    private val binding =
        ListItemNewsSmallBinding.inflate(LayoutInflater.from(context), this)

    override fun getViewForClickListener(): View = binding.root

    override fun getImageImageView(): ImageView = binding.imageImageView

    override fun getHoursAgoTextView(): TextView = binding.hoursAgoTextView

    override fun getTitleTextView(): TextView = binding.titleTextView
}
