package com.example.ilearning.domain.repository

import com.example.ilearning.data.remote.MockApiService
import com.example.ilearning.data.remote.MockResponse
import com.example.ilearning.data.remote.Response
import com.example.ilearning.data.repository.LoginRepository
import com.example.ilearning.domain.models.LoginRequest
import com.example.ilearning.domain.models.LoginResponse
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val api: MockApiService
) : LoginRepository {


    override suspend fun login(loginRequest: LoginRequest) = flow {
        emit(Response.Loading)
        val result: Response<LoginResponse?> = safeApiCall { api.login(loginRequest) }
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