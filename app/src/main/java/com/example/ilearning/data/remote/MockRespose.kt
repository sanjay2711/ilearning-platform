package com.example.ilearning.data.remote

data class MockResponse<T>(
    var status : Boolean,
    var message : String,
    var data : T
)