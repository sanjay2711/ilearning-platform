package com.example.ilearning.data.repository

import com.example.ilearning.data.local.entity.CourseWithLessons
import com.example.ilearning.data.remote.Response
import com.example.ilearning.domain.models.Course
import com.example.ilearning.domain.models.CourseResponse
import kotlinx.coroutines.flow.Flow

interface CourseRepository {

    suspend fun getCourses() : Flow<Response<CourseResponse?>>

    suspend fun getCourseById(id : Int) : Flow<Response<Course?>>
}