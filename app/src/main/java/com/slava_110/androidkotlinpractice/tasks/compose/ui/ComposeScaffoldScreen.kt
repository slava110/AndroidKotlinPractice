package com.slava_110.androidkotlinpractice.tasks.compose.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.slava_110.androidkotlinpractice.tasks.imageshow.ui.ComposeImageShow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AppMainComposeScreen() {
    AppNavigationDrawer {
        destination("home", "Home") {
            composable("home") {
                Text(text = "Hello!")
            }
        }
        destination("imageshow", "Image Show") {
            composable("imageshow") {
                ComposeImageShow()
            }
        }
        destination("name", "My Name") {
            composable("name") {
                Text(
                    text = "Popov Sviatoslav Mikhailovich, IKBO-26-21"
                )
            }
        }
    }
}

class NavigationItemModelsDsl {
    val destinations = mutableListOf<NavigationItemModel>()

    fun destination(
        route: String,
        displayText: String,
        func: NavGraphBuilder.() -> Unit
    ) {
        destinations += NavigationItemModel(route, displayText, func)
    }
}

data class NavigationItemModel(
    val route: String,
    val displayText: String,
    val navGraphFunc: NavGraphBuilder.() -> Unit
)

@Composable
fun AppNavigationDrawer(
    navigationItemsScope: NavigationItemModelsDsl.() -> Unit
) {
    AppNavigationDrawer(
        navigationItems = NavigationItemModelsDsl()
            .also(navigationItemsScope)
            .destinations
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigationDrawer(
    navigationItems: List<NavigationItemModel>
) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var currentNavItem by remember { mutableStateOf(navigationItems[0]) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Compose", modifier = Modifier.padding(16.dp))
                Divider()
                LazyColumn(Modifier.padding(16.dp)) {
                    items(navigationItems) { navModel ->
                        NavigationDrawerItem(
                            label = { Text(text = navModel.displayText) },
                            selected = currentNavItem.route == navModel.route,
                            onClick = {
                                if(currentNavItem.route == navModel.route) {
                                    return@NavigationDrawerItem
                                }

                                scope.launch {
                                    drawerState.close()
                                }

                                navController.navigate(navModel.route)

                                currentNavItem = navModel
                            }
                        )
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                PracticeTopBar(currentNavItem, scope, drawerState)
            }
        ) { paddingValues ->
            AppNavigationHost(
                modifier = Modifier.padding(paddingValues),
                navController = navController,
                navigationItems = navigationItems
            )
        }
    }
}

@Composable
fun AppNavigationHost(
    modifier: Modifier,
    navController: NavHostController,
    navigationItems: List<NavigationItemModel>
) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        for(navItem in navigationItems) {
            navItem.navGraphFunc(this)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PracticeTopBar(
    currentNavItem: NavigationItemModel,
    scope: CoroutineScope,
    drawerState: DrawerState
) {
    TopAppBar(
        title = {
            Text(
                text = currentNavItem.displayText,
                fontSize = 18.sp
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    scope.launch {
                        drawerState.open()
                    }
                }
            ) {
                Icon(Icons.Filled.Menu, "Menu")
            }
        }
    )
}