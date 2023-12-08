package test.teamfresh.dawnmarket.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import test.teamfresh.dawnmarket.navigation.RootDestination
import test.teamfresh.dawnmarket.navigation.RootDestination.BOOKMARK
import test.teamfresh.dawnmarket.navigation.RootDestination.CART
import test.teamfresh.dawnmarket.navigation.RootDestination.CATEGORY
import test.teamfresh.dawnmarket.navigation.RootDestination.HOME
import test.teamfresh.dawnmarket.navigation.RootDestination.MY_INFO
import test.teamfresh.dawnmarket.navigation.RootNavHost
import test.teamfresh.dawnmarket.ui.category.RESULT_ROUTE
import test.teamfresh.dawnmarket.ui.category.navigateToCategory
import test.teamfresh.dawnmarket.ui.component.DmNavigationBar
import test.teamfresh.dawnmarket.ui.component.DmNavigationBarItem
import test.teamfresh.dawnmarket.ui.empty.navigateToBookmark
import test.teamfresh.dawnmarket.ui.empty.navigateToCart
import test.teamfresh.dawnmarket.ui.empty.navigateToHome
import test.teamfresh.dawnmarket.ui.empty.navigateToMyInfo

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DawnMarket() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var showBottomBar by rememberSaveable { (mutableStateOf(false)) }

    showBottomBar = when (navBackStackEntry?.destination?.route) {
        RESULT_ROUTE -> false
        else -> true
    }

    Scaffold(
        bottomBar =  {
            if (showBottomBar) {
                DmBottomBar(
                    destinations = RootDestination.values().asList(),
                    onNavigateToDestination = { dest ->
                        val navOptions = navOptions {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }

                        when (dest) {
                            CATEGORY -> navController.navigateToCategory(navOptions)
                            BOOKMARK -> navController.navigateToBookmark(navOptions)
                            HOME -> navController.navigateToHome(navOptions)
                            CART -> navController.navigateToCart(navOptions)
                            MY_INFO -> navController.navigateToMyInfo(navOptions)
                        }
                    },
                    currentDestination = navBackStackEntry?.destination
                )
            }
        }
    ) { padding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal,
                    ),
                ),
        ) {
            RootNavHost(navController = navController)
        }
    }
}

@Composable
private fun DmBottomBar(
    destinations: List<RootDestination>,
    onNavigateToDestination: (RootDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    Column {
        Divider(color = Color(0xFFDDDFE4))
        DmNavigationBar(
            modifier = modifier,
        ) {
            destinations.forEach { destination ->
                val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
                DmNavigationBarItem(
                    selected = selected,
                    onClick = { onNavigateToDestination(destination) },
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(destination.iconId),
                            contentDescription = null,
                        )
                    },
                    label = { Text(stringResource(destination.iconTextId)) },
                )
            }
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: RootDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false