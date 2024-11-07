package com.example.messengerapp.presentation.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.messengerapp.R
import com.example.messengerapp.core.theme.AppTheme


@Composable
fun NavBottomBar(navController: NavController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val screens = listOf(
        Screens.BottomBarScreens.ContactsScreen, Screens.BottomBarScreens.ChatListScreen, Screens.BottomBarScreens.ProfileScreen
    )

    NavigationBar(
        modifier = Modifier.fillMaxHeight(0.08f),
        containerColor = AppTheme.colors.backgroundBottomMenu
    ) {
        screens.forEach { screen ->
            val isSelected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                        restoreState = true
                    }

                },
                icon = {
                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = screen.icon!!),
                            contentDescription = stringResource(R.string.icon_of_screen),
                        )

                        Spacer(modifier = Modifier.padding(top = 4.dp))

                        Text(
                            text = screen.name,
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AppTheme.colors.accentSecondary,
                    unselectedIconColor =  AppTheme.colors.textSecondary,
                    selectedTextColor = AppTheme.colors.accentSecondary,
                    unselectedTextColor =  AppTheme.colors.textSecondary,

                )
            )
        }
    }
}



@Preview
@Composable
fun NavBottomBarPreview() {
    val navController = rememberNavController()
    NavBottomBar(navController = navController)
}