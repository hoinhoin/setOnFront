package com.example.seton.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.NavDestination
import com.example.seton.R
import com.example.seton.ui.theme.Skyblue

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem("home", "홈", painterResource(id = R.drawable.baseline_home_24)),
        BottomNavItem("bookmark", "북마크", painterResource(id = R.drawable.baseline_bookmark_24)),
        BottomNavItem("mypage", "마이페이지", painterResource(id = R.drawable.baseline_contact_page_24)),
    )

    // 현재 네비게이션 상태 확인
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry.value?.destination

    NavigationBar(
        containerColor = Skyblue,
        contentColor = Color.Gray,
        modifier = Modifier,
    ) {
        items.forEach { item ->
            NavigationBarItem(

                icon = {
                    Column(
                        horizontalAlignment = CenterHorizontally
                    ) { Icon(
                        painter = item.icon,
                        tint = Color.White,
                        contentDescription = item.label,
                        modifier = Modifier.size(30.dp)
                    )
                        Text(
                            text = item.label,
                            color = Color.White,
                            modifier = Modifier
                        )}
                },
                //label = { Text(item.label,color = Color.White) },
                selected = isSelected(currentDestination, item.route),
                onClick = {
                    if (currentDestination?.route != item.route) {
                        navController.navigate(item.route) {
                            // 동일한 목적지로 여러 번 이동하지 않도록 방지
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

// 현재 선택된 상태를 확인하는 헬퍼 함수
private fun isSelected(currentDestination: NavDestination?, route: String): Boolean {
    return currentDestination?.route == route
}

data class BottomNavItem(val route: String, val label: String, val icon: Painter)
