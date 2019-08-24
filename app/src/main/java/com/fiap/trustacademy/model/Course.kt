package com.fiap.trustacademy.model

import com.google.gson.annotations.SerializedName

data class Course (
    @SerializedName("id")           val id: Long,
    @SerializedName("name")         val name: String) {}