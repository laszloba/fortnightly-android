package hu.laszloba.fortnightly.navigator

import android.app.Activity
import androidx.navigation.Navigation
import hu.laszloba.fortnightly.R
import hu.laszloba.fortnightly.ui.list.NewsListFragmentDirections
import javax.inject.Inject

class AppNavigatorImpl @Inject constructor(
    private val activity: Activity
) : AppNavigator {

    private val navController by lazy {
        Navigation.findNavController(activity, R.id.fragmentNavHost)
    }

    override fun navigateToArticle(articleId: Long) {
        navController.navigate(
            NewsListFragmentDirections
                .actionNewsListFragmentToArticleFragment(articleId)
        )
    }

    override fun popBackStack() {
        navController.popBackStack()
    }
}
