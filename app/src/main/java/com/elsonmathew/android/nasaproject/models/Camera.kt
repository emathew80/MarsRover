package com.elsonmathew.android.nasaproject.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Camera(
    val full_name: String,
    val name: String
) : Parcelable