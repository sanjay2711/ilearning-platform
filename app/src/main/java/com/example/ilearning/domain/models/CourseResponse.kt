package com.example.ilearning.domain.models

import com.example.ilearning.data.local.entity.CourseEntity
import com.example.ilearning.data.local.entity.CourseWithLessons
import com.example.ilearning.data.local.entity.LessonEntity

data class CourseResponse(
    val courses: List<Course>
)

data class Course(
    val id: Int,
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

fun Course.toCourseEntity() = CourseWithLessons(
    course = CourseEntity(
        id = id,
        courseName = courseName,
        instructorName = instructorName,
        progress = progress
    ),
    lessons = lessons.map { it.toLessonEntity(id) }
)


fun Lesson.toLessonEntity(courseId: Int) = LessonEntity(
    id = lessonId,
    lessonName = lessonName,
    completed = completed,
    courseId = courseId
)