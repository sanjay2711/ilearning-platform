package com.example.ilearning.ui.navigation

sealed  class Screen(val route : String) {

    data object Course : Screen("Course")

    data object Login : Screen("Login")

    data object  CourseDetails : Screen("CourseDetails/{courseId}") {

        fun createRoute(id : Int) : String {
            return "CourseDetails/${id}"
        }
    }
}