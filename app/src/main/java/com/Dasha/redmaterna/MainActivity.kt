package com.Dasha.redmaterna

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.Dasha.redmaterna.ui.theme.RedMaternaTheme
import com.Dasha.redmaterna.ui.theme.SelectionColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RedMaternaTheme {
                RedMaternaApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RedMaternaApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val context = LocalContext.current

    val navItemColors = NavigationSuiteDefaults.itemColors(
        navigationBarItemColors = androidx.compose.material3.NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            indicatorColor = SelectionColor
        ),
        navigationRailItemColors = androidx.compose.material3.NavigationRailItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            indicatorColor = SelectionColor
        ),
        navigationDrawerItemColors = androidx.compose.material3.NavigationDrawerItemDefaults.colors(
            selectedContainerColor = SelectionColor
        )
    )

    NavigationSuiteScaffold(
        containerColor = Color.White,
        contentColor = MaterialTheme.colorScheme.onBackground,
        navigationSuiteItems = {
            AppDestinations.entries.forEach { destination ->
                val route = if (destination == AppDestinations.Journal) {
                    "${destination.name}/0"
                } else {
                    destination.name
                }

                item(
                    icon = {
                        Icon(
                            painter = painterResource(destination.icon),
                            contentDescription = destination.label
                        )
                    },
                    label = { Text(destination.label) },
                    selected = currentDestination?.hierarchy?.any { 
                        it.route?.startsWith(destination.name) == true 
                    } == true,
                    onClick = {
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    colors = navItemColors
                )
            }
        }
    ) {
        val currentRoutePattern = currentDestination?.route ?: ""
        val baseRoute = currentRoutePattern.split("/").firstOrNull() ?: ""
        val currentAppDestination = AppDestinations.entries.find { it.name == baseRoute }

        Scaffold(
            topBar = {
                Column {
                    CenterAlignedTopAppBar(
                        title = { Text(currentAppDestination?.label ?: "") },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            titleContentColor = MaterialTheme.colorScheme.onBackground
                        ),
                    )
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                }
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = AppDestinations.HOME.name,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                composable(AppDestinations.HOME.name) {
                    HomeScreen(
                        onRecordClick = {
                            val intent = Intent(context, WriteJournalActivity::class.java)
                            context.startActivity(intent)
                        },
                        onJournalClick = {
                            navController.navigate("${AppDestinations.Journal.name}/0") {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        onSymptomsClick = {
                            navController.navigate("${AppDestinations.Journal.name}/1") {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
                composable(AppDestinations.FORUM.name) { ForumScreen() }
                composable(AppDestinations.SUPPORT.name) { 
                    SupportScreen(
                        onForumClick = {
                            navController.navigate(AppDestinations.FORUM.name) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    ) 
                }
                
                composable(
                    route = "${AppDestinations.Journal.name}/{tabIndex}",
                    arguments = listOf(navArgument("tabIndex") { type = NavType.IntType })
                ) { backStackEntry ->
                    val tabIndex = backStackEntry.arguments?.getInt("tabIndex") ?: 0
                    JournalScreen(initialTabIndex = tabIndex)
                }

                composable(AppDestinations.PROFILE.name) { ProfileScreen() }
            }
        }
    }
}

enum class AppDestinations(
    val label: String,
    val icon: Int,
) {
    HOME("Home", R.drawable.home_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
    FORUM("Forum", R.drawable.forum_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
    SUPPORT("Support", R.drawable.support_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
    Journal("Journal", R.drawable.book_ribbon_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
    PROFILE("Profile", R.drawable.account_circle_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
}
