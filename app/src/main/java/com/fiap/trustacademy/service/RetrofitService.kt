package com.fiap.trustacademy.service

import com.fiap.trustacademy.model.Document
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    @GET("institutes/{instituteId}/students/{studentId}/documents")
    fun getDocumentsByStudent(@Path("instituteId") instituteId: Long,
                              @Path("studentId") studentId: Long): Call<List<Document>>

    @GET("institutes/{instituteId}/documents")
    fun getDocumentsByInstitute(@Path("instituteId") instituteId: Long): Call<List<Document>>

}