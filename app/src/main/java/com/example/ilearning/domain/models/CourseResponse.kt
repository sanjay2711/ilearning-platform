package com.example.ilearning.domain.models

data class CourseResponse(
    val courses: List<Course>
)

data class Course(
    val id : Int,
    val courseName: String,
    val instructorName: String,
    val lessons: List<Lesson>
) {
    val numberOfLessons: Int
        get() = lessons.size

    val progress: Int
        get() = if (lessons.isEmpty()) {
            0
        } else {
            (lessons.count { it.completed } * 100) / lessons.size
        }
}

data class Lesson(
    val lessonId: Int,
    val lessonName: String,
    val completed: Boolean
)