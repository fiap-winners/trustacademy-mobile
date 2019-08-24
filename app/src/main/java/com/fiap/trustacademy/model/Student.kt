package com.fiap.trustacademy.model

import com.google.gson.annotations.SerializedName

data class Student (
    @SerializedName("id")   val id: Long,
    @SerializedName("name") val name: String
){}