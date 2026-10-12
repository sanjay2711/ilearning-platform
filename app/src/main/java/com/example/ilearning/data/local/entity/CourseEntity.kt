package com.example.ilearning.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey
    val id: Int,
    val courseName: String,
    val instructorName: String,
    val progress : Int,
)
