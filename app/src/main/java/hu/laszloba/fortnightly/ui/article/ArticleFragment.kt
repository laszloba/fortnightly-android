package hu.laszloba.fortnightly.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint
import hu.laszloba.fortnightly.databinding.FragmentArticleBinding
import hu.laszloba.fortnightly.extension.exhaustive
import hu.laszloba.fortnightly.navigator.AppNavigator
import hu.laszloba.fortnightly.ui.toolbar.ToolbarWithBackButton
import javax.inject.Inject

@AndroidEntryPoint
class ArticleFragment : Fragment() {

    private object Flipper {
        const val LOADING = 0
        const val CONTENT = 1
        const val ERROR = 2
    }

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    private val args: ArticleFragmentArgs by navArgs()
    private val viewModel: ArticleViewModel by viewModels()

    @Inject
    lateinit var navigator: AppNavigator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarWithBackButton.onActionClickedListener =
            object : ToolbarWithBackButton.OnActionClickedListener {
                override fun onBackButtonClicked() {
                    navigator.popBackStack()
                }
            }

        with(viewModel) {
            viewState.observe(viewLifecycleOwner, ::render)
            loadArticle(args.articleId)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun render(viewState: ArticleViewState) {
        when (viewState) {
            Loading ->
                binding.viewFlipper.displayedChild = Flipper.LOADING
            Error ->
                binding.viewFlipper.displayedChild = Flipper.ERROR
            is ArticleLoaded -> {
                val article = viewState.article

                with(binding) {
                    viewFlipper.displayedChild = Flipper.CONTENT

                    Glide.with(requireActivity())
                        .load(article.urlToImage)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageImageView)

                    titleTextView.text = article.title
                    descriptionTextView.text = article.description
                }
            }
        }.exhaustive
    }
}
