@file:OptIn(ExperimentalMaterial3Api::class)

package com.slava_110.androidkotlinpractice.tasks.compose.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class NavigationItemModel(
    val route: String,
    val displayText: String,
    val icon: ImageVector,
    val navGraphFunc: NavGraphBuilder.() -> Unit
)

private val navigationItems = listOf(
    NavigationItemModel(
        route = "home",
        displayText = "Home",
        icon = Icons.Rounded.Warning
    ) {
        composable("home") {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Hello!",
                    fontSize = 50.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
    },
    NavigationItemModel(
        route = "imageshow",
        displayText = "Image Show",
        icon = Icons.Rounded.Info
    ) {
        composable("imageshow") {
            ComposeImageShow()
        }
    },
    NavigationItemModel(
        route = "name",
        displayText = "My Name",
        icon = Icons.Rounded.AccountBox
    ) {
        composable("name") {
            Text(
                text = "Popov Sviatoslav Mikhailovich, IKBO-26-21"
            )
        }
    }
)

@Composable
fun AppMainComposeScreen(
    onExit: () -> Unit = {}
) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val currentNavItemState = remember { mutableStateOf(navigationItems[0]) }

    val useBottomBarNavigationState = remember { mutableStateOf(false) }

    if(useBottomBarNavigationState.value) {
        BottomBarNavigation(
            onExit,
            useBottomBarNavigationState,
            navController,
            navigationItems,
            scope,
            currentNavItemState
        )
    } else {
        DrawerNavigation(
            onExit,
            useBottomBarNavigationState,
            navController,
            navigationItems,
            scope,
            currentNavItemState
        )
    }
}

@Composable
private fun DrawerNavigation(
    onExit: () -> Unit,
    useBottomBarNavigationState: MutableState<Boolean>,
    navController: NavHostController,
    navigationItems: List<NavigationItemModel>,
    scope: CoroutineScope,
    currentNavItemState: MutableState<NavigationItemModel>
) {
    var currentNavItem by currentNavItemState
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

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
                            icon = {
                                Icon(
                                    imageVector = navModel.icon,
                                    contentDescription = navModel.displayText
                                )
                            },
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
                PracticeTopBar(onExit, useBottomBarNavigationState, currentNavItem, scope, drawerState)
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
private fun BottomBarNavigation(
    onExit: () -> Unit,
    useBottomBarNavigationState: MutableState<Boolean>,
    navController: NavHostController,
    navigationItems: List<NavigationItemModel>,
    scope: CoroutineScope,
    currentNavItemState: MutableState<NavigationItemModel>
) {
    var currentNavItem by currentNavItemState

    Scaffold(
        topBar = {
            PracticeTopBar(onExit, useBottomBarNavigationState, currentNavItem, scope)
        },
        bottomBar = {
            NavigationBar {
                for (navModel in navigationItems) {
                    NavigationBarItem(
                        selected = currentNavItem.route == navModel.route,
                        onClick = {
                            if (currentNavItem.route == navModel.route) {
                                return@NavigationBarItem
                            }

                            navController.navigate(navModel.route)

                            currentNavItem = navModel
                        },
                        icon = {
                            Icon(
                                imageVector = navModel.icon,
                                contentDescription = navModel.displayText
                            )
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        AppNavigationHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            navigationItems = navigationItems
        )
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
    onExit: () -> Unit,
    useBottomBarNavigationState: MutableState<Boolean>,
    currentNavItem: NavigationItemModel,
    scope: CoroutineScope,
    drawerState: DrawerState? = null
) {
    TopAppBar(
        title = {
            Text(
                text = currentNavItem.displayText,
                fontSize = 18.sp
            )
        },
        navigationIcon = {
            if(drawerState != null) {
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
        },
        actions = {
            IconButton(
                onClick = {
                    useBottomBarNavigationState.value = !useBottomBarNavigationState.value
                }
            ) {
                Icon(imageVector = Icons.Rounded.Build, contentDescription = "Switch")
            }
            IconButton(
                onClick = {
                    onExit()
                }
            ) {
                Icon(imageVector = Icons.Rounded.ExitToApp, contentDescription = "Exit")
            }
        }
    )
}