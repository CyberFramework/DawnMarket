package test.teamfresh.dawnmarket.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import test.teamfresh.dawnmarket.ui.category.categoryGraph
import test.teamfresh.dawnmarket.ui.category.categoryNavigationRoute
import test.teamfresh.dawnmarket.ui.empty.emptyScreen

@Composable
fun RootNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = categoryNavigationRoute,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        categoryGraph(navController)

        emptyScreen()
    }
}