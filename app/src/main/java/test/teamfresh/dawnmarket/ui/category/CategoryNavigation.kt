package test.teamfresh.dawnmarket.ui.category

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation

const val categoryNavigationRoute = "category_route"

internal const val CATEGORY_ID = "dispClasSeq"
internal const val CATEGORY_NAME = "dispClasNm"

const val LIST_ROUTE = "list_route"
const val RESULT_ROUTE = "result_route?$CATEGORY_ID={$CATEGORY_ID}?$CATEGORY_NAME={$CATEGORY_NAME}"

fun NavController.navigateToCategory(navOptions: NavOptions? = null) {
    this.navigate(categoryNavigationRoute, navOptions)
}

fun NavGraphBuilder.categoryGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = LIST_ROUTE,
        route = categoryNavigationRoute,
    ) {
        composable(LIST_ROUTE) {
            CategoryRoute(
                onCategoryClick = { id, name ->
                    navController.navigate(
                        RESULT_ROUTE
                            .replace("{$CATEGORY_ID}", id.toString())
                            .replace("{$CATEGORY_NAME}", name)
                    )
                }
            )
        }

        composable(
            route = RESULT_ROUTE,
            arguments = listOf(
                navArgument(CATEGORY_ID) { type = NavType.IntType },
                navArgument(CATEGORY_NAME) { type = NavType.StringType },
            )
        ) {
            SubcategoryRoute { navController.popBackStack() }
        }
    }
}