package com.example.ilearning.domain.repository

import com.example.ilearning.data.local.dao.CourseDao
import com.example.ilearning.data.local.entity.CourseWithLessons
import com.example.ilearning.data.local.entity.toCourseModel
import com.example.ilearning.data.remote.MockApiService
import com.example.ilearning.data.remote.Response
import com.example.ilearning.data.repository.CourseRepository
import com.example.ilearning.domain.models.Course
import com.example.ilearning.domain.models.CourseResponse
import com.example.ilearning.domain.models.toCourseEntity
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val courseDao: CourseDao,
    private val api: MockApiService
) : CourseRepository {

    override suspend fun getCourses(): Flow<Response<CourseResponse?>> = flow {

        if (courseDao.getCourseCount() == 0) {
            emit(Response.Loading)
            when (val remote = getCourse()) {
                is Response.Success -> {
                    insertCourses(remote.data?.courses?.map { data -> data.toCourseEntity() }
                        ?: emptyList())
                }

                is Response.Error -> {
                    emit(remote)
                    return@flow
                }

                else -> {}
            }
        }

        emitAll(
            courseDao.getAllCourses().map {
                Response.Success(CourseResponse(it.map { course -> course.toCourseModel() }))
            }
        )
    }.flowOn(Dispatchers.Default)

    override suspend fun getCourseById(id: Int): Flow<Response<Course?>> = flow {
        emit(Response.Loading)
        val result = courseDao.getCourseById(id)
        result?.let {
            emit(Response.Success(it.toCourseModel()))
        } ?: emit(Response.Error("Course not found"))
    }

    private suspend fun insertCourses(courses: List<CourseWithLessons>) {
        courseDao.insertAll(courses)
    }

    private suspend fun getCourse(): Response<CourseResponse?> {

        //This is mock Api real Api logic resides here
        try {
            val result = api.getCourse()
            return if (result.status) {
                Response.Success(result.data)
            } else
                Response.Error(result.message)
        } catch (e: Exception) {
            return Response.Error(message = e.message ?: "Something went wrong")
        } catch (e: CancellationException) {
            throw e
        }
    }

}