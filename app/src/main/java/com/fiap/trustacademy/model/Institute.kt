package com.fiap.trustacademy.model

import com.google.gson.annotations.SerializedName

data class Institute (
    @SerializedName("id")   val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("code") val code: String){}