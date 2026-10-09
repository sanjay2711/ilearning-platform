package com.example.ilearning.ui.screens.courseDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ilearning.data.remote.Response
import com.example.ilearning.data.repository.RemoteRepository
import com.example.ilearning.domain.models.Course
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class CourseDetailViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository
) : ViewModel() {


    private val _courseDetailState = MutableStateFlow<Response<Course?>>(Response.Empty)
    val courseDetailState get() = _courseDetailState


    fun getCourseById(id: Int) {
        _courseDetailState.value = Response.Loading
        viewModelScope.launch {
            remoteRepository.getCourseById(id).collect {
                _courseDetailState.value = it
            }
        }
    }

}