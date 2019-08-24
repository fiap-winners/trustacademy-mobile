package com.fiap.trustacademy.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Card (
    var lastUpdatedDate: String,
    var documentVersionQty: String,
    var documentTypeName: String,
    var courseName: String
) : Parcelable {}