package com.fiap.trustacademy.model

import com.google.gson.annotations.SerializedName

class DocumentType (
    @SerializedName("id")   val id: Long,
    @SerializedName("name") val name: String) {

    override fun toString(): String {
        return this.name
    }
}