package com.example.ilearning.data.repository

import com.example.ilearning.data.remote.Response
import com.example.ilearning.domain.models.LoginRequest
import com.example.ilearning.domain.models.LoginResponse
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun login(loginRequest: LoginRequest): Flow<Response<LoginResponse?>>
}