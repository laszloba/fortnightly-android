package hu.laszloba.fortnightly.ui.list

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.laszloba.fortnightly.model.LargeNewsListItemPresentationModel
import hu.laszloba.fortnightly.model.NewsListItemPresentationModel
import hu.laszloba.fortnightly.model.SmallNewsListItemPresentationModel

class NewsListAdapter(
    private val context: Context
) : ListAdapter<NewsListItemPresentationModel, RecyclerView.ViewHolder>(
    NewsListItemDiffCallback
) {
    companion object {
        private const val VIEW_TYPE_LARGE = 0
        private const val VIEW_TYPE_SMALL = 1
    }

    var items: List<NewsListItemPresentationModel> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onItemClickedListener: OnItemClickedListener? = null

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int =
        when (items[position]) {
            is LargeNewsListItemPresentationModel -> VIEW_TYPE_LARGE
            is SmallNewsListItemPresentationModel -> VIEW_TYPE_SMALL
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            VIEW_TYPE_LARGE -> NewsListLargeItemViewHolder(NewsListLargeItemView(context))
            VIEW_TYPE_SMALL -> NewsListSmallItemViewHolder(NewsListSmallItemView(context))
            else -> throw IllegalArgumentException("Bad view type: $viewType")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder.itemView as BaseNewsListItemView) {
            onItemClickedListener = this@NewsListAdapter.onItemClickedListener
            bind(items[position])
        }
    }

    class NewsListLargeItemViewHolder(view: NewsListLargeItemView) :
        RecyclerView.ViewHolder(view)

    class NewsListSmallItemViewHolder(view: NewsListSmallItemView) :
        RecyclerView.ViewHolder(view)

    object NewsListItemDiffCallback : DiffUtil.ItemCallback<NewsListItemPresentationModel>() {
        override fun areItemsTheSame(
            oldItem: NewsListItemPresentationModel,
            newItem: NewsListItemPresentationModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: NewsListItemPresentationModel,
            newItem: NewsListItemPresentationModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    interface OnItemClickedListener {
        fun onItemClicked(model: NewsListItemPresentationModel)
    }
}
