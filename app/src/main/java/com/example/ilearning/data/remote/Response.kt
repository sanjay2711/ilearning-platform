package com.example.ilearning.data.remote

sealed class  Response<out T> {
    object Empty : Response<Nothing>()
    object  Loading : Response<Nothing>()

    data class Success<T>(val data : T) : Response<T>()
    data class Error(val message : String) : Response<Nothing>()
}