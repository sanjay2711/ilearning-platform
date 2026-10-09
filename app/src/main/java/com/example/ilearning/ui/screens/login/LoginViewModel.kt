package com.example.ilearning.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ilearning.data.remote.Response
import com.example.ilearning.data.repository.RemoteRepository
import com.example.ilearning.domain.models.LoginRequest
import com.example.ilearning.domain.models.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository
): ViewModel() {


    private val _loginState  =  MutableStateFlow<Response<LoginResponse?>>(Response.Empty)
    val loginState get() = _loginState.asStateFlow()


    fun login(mail : String , pass : String)
    {
        _loginState.value = Response.Loading
        viewModelScope.launch {
            LoginRequest(
                email = mail,
                password = pass
            ).also {
                remoteRepository.login(it).collect { data ->
                    _loginState.value = data
                }
            }
        }
    }

}