package com.example.seton

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
//import androidx.compose.ui.node.CanFocusChecker.start
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.seton.ui.screens.BookMarkScreen
import com.example.seton.ui.screens.HomeScreen
import com.example.seton.ui.screens.MyPageScreen

@Composable
fun MyApp(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()

    //val start = of (currentUser != null) "home" else "login"
    NavHost(navController, startDestination = "home", modifier = modifier) {
        composable("home") {
            HomeScreen(
                navController = navController
            )
        }
        composable("bookmark") {
            BookMarkScreen(
                navController = navController
            )
        }

        composable("mypage") {
            MyPageScreen(
                navController = navController
            )

        }
    }
}