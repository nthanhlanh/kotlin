package com.example.kotlin.repository

import com.example.kotlin.dto.StudentDto
import com.example.kotlin.dto.StudentInfoDto
import com.example.kotlin.tables.references.CLASSROOMS
import com.example.kotlin.tables.references.COURSES
import com.example.kotlin.tables.references.ENROLLMENTS
import com.example.kotlin.tables.references.STUDENTS
import com.example.kotlin.tables.references.TEACHERS
import kotlinx.coroutines.delay
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class StudentRepository(private val dsl: DSLContext) {

    fun findAll(): List<StudentDto> =
        dsl.selectFrom(STUDENTS)
            .fetchInto(StudentDto::class.java)

    suspend fun findById(id: Int): StudentDto? {
        delay(2000)
        println("bbbbbbbbbb")
        return dsl.selectFrom(STUDENTS)
            .where(STUDENTS.ID.eq(id))
            .fetchOneInto(StudentDto::class.java)
    }


    fun create(student: StudentDto): StudentDto =
        dsl.insertInto(STUDENTS)
            .set(STUDENTS.NAME, student.name)
            .set(STUDENTS.EMAIL, student.email)
            .set(STUDENTS.DATE_OF_BIRTH, student.dateOfBirth)
            .set(STUDENTS.CLASS_ID, student.classId)
            .returning()
            .fetchOneInto(StudentDto::class.java)!!

    fun delete(id: Int): Int =
        dsl.deleteFrom(STUDENTS)
            .where(STUDENTS.ID.eq(id))
            .execute()

    fun findStudentDetails(): List<StudentInfoDto> {
        return dsl.select(
            STUDENTS.NAME.`as`("studentName"),
            CLASSROOMS.NAME.`as`("className"),
            TEACHERS.NAME.`as`("teacherName"),
            COURSES.NAME.`as`("courseName"),
            ENROLLMENTS.SEMESTER.`as`("semester")
        )
            .from(STUDENTS)
            .join(CLASSROOMS).on(STUDENTS.CLASS_ID.eq(CLASSROOMS.ID))
            .join(TEACHERS).on(CLASSROOMS.TEACHER_ID.eq(TEACHERS.ID))
            .join(ENROLLMENTS).on(STUDENTS.ID.eq(ENROLLMENTS.STUDENT_ID))
            .join(COURSES).on(ENROLLMENTS.COURSE_ID.eq(COURSES.ID))
            .fetchInto(StudentInfoDto::class.java)
    }
}
