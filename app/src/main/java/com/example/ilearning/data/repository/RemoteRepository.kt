package com.example.ilearning.data.repository

import com.example.ilearning.data.remote.Response
import com.example.ilearning.domain.models.Course
import com.example.ilearning.domain.models.CourseResponse
import com.example.ilearning.domain.models.LoginRequest
import com.example.ilearning.domain.models.LoginResponse
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {

    suspend fun login(loginRequest: LoginRequest) : Flow<Response<LoginResponse?>>

    suspend fun getCourse() : Flow<Response<CourseResponse?>>

    suspend fun getCourseById(id : Int ) : Flow<Response<Course?>>
}