package com.example.ilearning.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.example.ilearning.domain.models.Course
import com.example.ilearning.domain.models.Lesson

data class CourseWithLessons (

    @Embedded
    val course: CourseEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "courseId"
    )
    val lessons: List<LessonEntity>
)

fun CourseWithLessons.toCourseModel() = Course(
    id = course.id,
    courseName = course.courseName,
    instructorName = course.instructorName,
    lessons = lessons.map { it.toLesson() }
)


fun LessonEntity.toLesson() = Lesson(
    lessonId = id,
    lessonName = lessonName,
    completed = completed
)
