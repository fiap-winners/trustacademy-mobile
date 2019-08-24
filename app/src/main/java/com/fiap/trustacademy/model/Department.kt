package com.fiap.trustacademy.model

import com.google.gson.annotations.SerializedName

class Department (
    @SerializedName("id")           val id: Long,
    @SerializedName("name")         val name: String
){}