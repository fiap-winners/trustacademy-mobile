package com.fiap.trustacademy.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Document(
    @SerializedName("id")   val id: Int,
    @SerializedName("student")val student: Student,
    @SerializedName("institute")val institute: Institute,
    @SerializedName("department")val department: Department,
    @SerializedName("course")val course: Course,
    @SerializedName("type")val type: DocumentType,
    @SerializedName("content")val content: String,
    @SerializedName("createAt")val createAt: Date,
    @SerializedName("modifiedAt")val modifiedAt: Date?
) {}