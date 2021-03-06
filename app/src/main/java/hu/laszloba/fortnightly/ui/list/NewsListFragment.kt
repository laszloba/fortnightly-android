package hu.laszloba.fortnightly.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import hu.laszloba.fortnightly.R
import hu.laszloba.fortnightly.databinding.FragmentNewsListBinding
import hu.laszloba.fortnightly.extension.exhaustive
import hu.laszloba.fortnightly.model.NewsListItemPresentationModel
import hu.laszloba.fortnightly.navigator.AppNavigator
import hu.laszloba.fortnightly.ui.toolbar.ToolbarAnimator
import javax.inject.Inject

@AndroidEntryPoint
class NewsListFragment : Fragment(), ToolbarAnimator.AnimationChangeListener {

    private object Flipper {
        const val LOADING = 0
        const val CONTENT = 1
        const val ERROR = 2
    }

    private var _binding: FragmentNewsListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NewsListViewModel by viewModels()
    private val toolbarAnimator = ToolbarAnimator(this)

    private lateinit var listAdapter: NewsListAdapter

    @Inject
    lateinit var navigator: AppNavigator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listAdapter = NewsListAdapter(requireActivity()).apply {
            onItemClickedListener = object : NewsListAdapter.OnItemClickedListener {
                override fun onItemClicked(model: NewsListItemPresentationModel) {
                    navigator.navigateToArticle(model.id)
                }
            }
        }

        val listLayoutManager = LinearLayoutManager(context)

        with(binding.newsListRecyclerView) {
            layoutManager = listLayoutManager
            addItemDecoration(DividerItemDecoration(context, listLayoutManager.orientation))
            adapter = listAdapter
            clearOnScrollListeners()
            addOnScrollListener(
                object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        toolbarAnimator.calculateAnimation(dy)
                    }
                }
            )
        }

        with(viewModel) {
            viewState.observe(viewLifecycleOwner, ::render)
            loadNewsList()
        }
    }

    override fun onAnimationProgressChange(progress: Float) {
        with(binding) {
            mainMotionLayout.progress = progress
            toolbarBackgroundImage.setShapeInterpolation(progress)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun render(viewState: NewsListViewState) {
        when (viewState) {
            Loading ->
                binding.viewFlipper.displayedChild = Flipper.LOADING
            is Error -> {
                with(binding) {
                    viewFlipper.displayedChild = Flipper.ERROR
                    errorTextView.text = getString(R.string.news_list_error, viewState.message)
                }
            }
            is NewsListLoaded -> {
                binding.viewFlipper.displayedChild = Flipper.CONTENT
                listAdapter.items = viewState.newsList
            }
        }.exhaustive
    }
}
