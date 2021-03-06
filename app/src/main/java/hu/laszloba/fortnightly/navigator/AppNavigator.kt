package hu.laszloba.fortnightly.navigator

interface AppNavigator {

    fun navigateToArticle(articleId: Long)

    fun popBackStack()
}
