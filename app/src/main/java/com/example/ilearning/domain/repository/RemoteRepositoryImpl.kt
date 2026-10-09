package com.example.ilearning.domain.repository

import com.example.ilearning.data.remote.MockApiService
import com.example.ilearning.data.remote.MockResponse
import com.example.ilearning.data.remote.Response
import com.example.ilearning.data.repository.RemoteRepository
import com.example.ilearning.domain.models.Course
import com.example.ilearning.domain.models.CourseResponse
import com.example.ilearning.domain.models.LoginRequest
import com.example.ilearning.domain.models.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val api: MockApiService
) : RemoteRepository {


    override suspend fun login(loginRequest: LoginRequest) = flow {

        val result: Response<LoginResponse?> = safeApiCall { api.login(loginRequest) }
        emit(result)
    }


    override suspend fun getCourse(): Flow<Response<CourseResponse?>> = flow {
        val result: Response<CourseResponse?> = safeApiCall { api.getCourse() }
        emit(result)
    }

    override suspend fun getCourseById(id : Int): Flow<Response<Course?>> = flow {
        val result : Response<Course?> = safeApiCall {  api.getCourseById(id) }
        emit(result)
    }

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> MockResponse<T>
    ): Response<T> {

        return try {

            val response = apiCall()

            if (response.status) {
                response.data?.let {
                    Response.Success(it)
                } ?: Response.Error(
                    message = "Empty response",
                )

            } else {
                Response.Error(
                    message = response.message,
                )
            }
        } catch (e: Exception) {
            Response.Error(
                message = e.message ?: "Something went wrong"
            )
        }
    }
}