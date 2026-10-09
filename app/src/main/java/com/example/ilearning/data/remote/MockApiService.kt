package com.example.ilearning.data.remote

import android.content.Context
import com.example.ilearning.domain.models.Course
import com.example.ilearning.domain.models.CourseResponse
import com.example.ilearning.domain.models.LoginRequest
import com.example.ilearning.domain.models.LoginResponse
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import java.util.concurrent.Future
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

class MockApiService @Inject constructor(
    @ApplicationContext private val context: Context
) {


    suspend fun login(loginRequest: LoginRequest): MockResponse<LoginResponse?> {

        delay(1500.milliseconds)

        val email = "sanjay@gmail.com"
        val pass = "sanjay123"


        return if (loginRequest.email == email && loginRequest.password == pass) {
            val loginResponse = LoginResponse(
                id = 101,
                name = "Sanjay S",
                username = "sanjay27",
                email = "sanjay@gmail.com",
                phone = "9988776655"
            )

            MockResponse(
                status = true,
                message = "Login Successfully",
                data = loginResponse
            )
        } else {

            MockResponse(
                status = false,
                message = "Login Failed",
                data = null
            )
        }
    }


    suspend fun getCourse(): MockResponse<CourseResponse?> {

        delay(2000.milliseconds)

        val gson = Gson()
        val json = context.assets
            .open("course.json")
            .bufferedReader()
            .use { it.readText() }

        val course = gson.fromJson(
            json,
            CourseResponse::class.java
        )

        return MockResponse(
            status = true,
            message = "Course List",
            data = course
        )
    }

    suspend fun getCourseById(id : Int) : MockResponse<Course?> {

        val courseList = getCourse()

        val course = courseList.data?.courses?.find {
            it.id == id
        }

        return MockResponse(
            status = true,
            message = "Course",
            data = course
        )
    }
}