package com.example.ilearning.ui.screens.course

import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ilearning.data.remote.Response
import com.example.ilearning.data.repository.CourseRepository
import com.example.ilearning.domain.models.CourseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


@HiltViewModel
class CourseViewModel @Inject constructor(
    private val courseRepository: CourseRepository,
) : ViewModel() {


    private val _courseState = MutableStateFlow<Response<CourseResponse?>>(Response.Empty)
    val courseState get() = _courseState.asStateFlow()

    var courseJob: Job? = null

    init {
        Log.d("Perf", "course VM init ${SystemClock.elapsedRealtime()}")
        getCourseData()
    }


    fun getCourseData() {
        courseJob?.cancel()
        courseJob = viewModelScope.launch {
            courseRepository.getCourses().collect {
                _courseState.value = it
            }
        }
    }
}