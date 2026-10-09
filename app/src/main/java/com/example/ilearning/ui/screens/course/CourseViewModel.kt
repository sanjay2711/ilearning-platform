package com.example.ilearning.ui.screens.course

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ilearning.data.remote.Response
import com.example.ilearning.data.repository.RemoteRepository
import com.example.ilearning.domain.models.CourseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


@HiltViewModel
class CourseViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository
) : ViewModel() {


    private val _courseState = MutableStateFlow<Response<CourseResponse?>>(Response.Empty)
    val courseState get() = _courseState.asStateFlow()

    init {
        getCourseData()
    }


    fun getCourseData() {
        _courseState.value = Response.Loading
        viewModelScope.launch {
            remoteRepository.getCourse().collect {
                _courseState.value = it
            }
        }
    }


}