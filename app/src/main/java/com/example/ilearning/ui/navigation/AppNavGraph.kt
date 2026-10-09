package com.example.ilearning.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.ilearning.ui.screens.courseDetails.CourseDetailScreen
import com.example.ilearning.ui.screens.course.CourseScreen
import com.example.ilearning.ui.screens.login.LoginScreen


@Composable
fun AppNavGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Course.route) {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Screen.Course.route) {
            CourseScreen(
                onCourseClick = { id ->
                    navController.navigate(Screen.CourseDetails.createRoute(id))
                }
            )
        }

        composable(route = Screen.CourseDetails.route, arguments = listOf(
            navArgument("courseId") {type = NavType.IntType}
        )) { backStack ->

            val id = backStack.arguments?.getInt("courseId") ?: 0
            CourseDetailScreen(
                onBackPressed = {
                    navController.popBackStack()
                },
                id = id
            )
        }
    }
}