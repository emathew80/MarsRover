package com.elsonmathew.android.nasaproject.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CameraX(
    val full_name: String,
    val id: Int,
    val name: String,
    val rover_id: Int
) : Parcelable