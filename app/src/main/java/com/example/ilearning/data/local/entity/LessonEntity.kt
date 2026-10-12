package com.example.ilearning.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = CourseEntity::class,
        parentColumns = ["id"],
        childColumns = ["courseId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class LessonEntity(
    @PrimaryKey
    val id : Int,
    val lessonName : String,
    val completed : Boolean,
    val courseId : Int,
)