package com.fiap.trustacademy.service

import com.fiap.trustacademy.model.*
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    @GET("institutes/{instituteId}")
    fun getInstituteById(@Path("instituteId") instituteId: Long): Call<Institute>

    @GET("institutes/{instituteId}/students/{studentId}")
    fun getStudentById(@Path("instituteId") instituteId: Long,
                       @Path("StudentId") studentId: Long): Call<Student>

    @GET("institutes/{instituteId}/students/{studentId}/documents")
    fun getDocumentsByStudent(@Path("instituteId") instituteId: Long,
                              @Path("studentId") studentId: Long): Call<List<Document>>

    @GET("institutes/{instituteId}/documents")
    fun getDocumentsByInstitute(@Path("instituteId") instituteId: Long): Call<List<Document>>

    @GET("institutes/{instituteId}/departments")
    fun getDepartmentsByInstitute(@Path("instituteId") instituteId: Long): Call<List<Department>>

    @GET("institutes/{instituteId}/departments/{departmentId}/courses")
    fun getCourseByDepartment(@Path("instituteId") instituteId: Long,
                              @Path("departmentId") departmentId: Long): Call<List<Course>>

    @GET("institutes/{instituteId}/document-types")
    fun getDocumentTypeByInstitute(@Path("instituteId") instituteId: Long): Call<List<DocumentType>>

    @POST("institutes/{instituteId}/departments/{departmentId}/courses/{courseId}/students/{studentId}/document-types/{documentType}/documents")
    fun setDocument(@Path("instituteId") instituteId: Long,
                    @Path("departmentId") departmentId: Long,
                    @Path("courseId") courseId: Long,
                    @Path("studentId") studentId: Long,
                    @Path("documentType") documentTypeId: Long,
                    @Body document: DocumentToSave): Call<Document>
}