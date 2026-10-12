package com.example.ilearning.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.ilearning.data.local.entity.CourseEntity
import com.example.ilearning.data.local.entity.CourseWithLessons
import com.example.ilearning.data.local.entity.LessonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {

    @Transaction
    @Query("SELECT * FROM courses")
    fun getAllCourses(): Flow<List<CourseWithLessons>>

    @Transaction
    @Query("SELECT * FROM courses WHERE id = :id")
    suspend fun getCourseById(id: Int): CourseWithLessons?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourses(courses: List<CourseEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLessons(lessons: List<LessonEntity>)

    @Transaction
    suspend fun insertAll(items: List<CourseWithLessons>) {
        insertCourses(items.map { it.course })
        insertLessons(items.flatMap { it.lessons })
    }

    @Query("DELETE FROM courses")
    suspend fun clearCourses()

    @Query("SELECT COUNT(*) FROM courses")
    suspend fun getCourseCount() : Int
}
