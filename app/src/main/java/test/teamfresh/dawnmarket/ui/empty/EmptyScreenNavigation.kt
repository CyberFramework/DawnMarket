package test.teamfresh.dawnmarket.ui.empty

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val bookmarkNavigationRoute = "bookmark_route"
const val homeNavigationRoute = "home_route"
const val cartNavigationRoute = "cart_route"
const val myInfoNavigationRoute = "my_info_route"

fun NavController.navigateToBookmark(navOptions: NavOptions? = null) {
    this.navigate(bookmarkNavigationRoute, navOptions)
}
fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(homeNavigationRoute, navOptions)
}
fun NavController.navigateToCart(navOptions: NavOptions? = null) {
    this.navigate(cartNavigationRoute, navOptions)
}
fun NavController.navigateToMyInfo(navOptions: NavOptions? = null) {
    this.navigate(myInfoNavigationRoute, navOptions)
}

fun NavGraphBuilder.emptyScreen() {
    composable(bookmarkNavigationRoute) {}
    composable(homeNavigationRoute) {}
    composable(cartNavigationRoute) {}
    composable(myInfoNavigationRoute) {}
}