package com.elsonmathew.android.nasaproject.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo(
    val camera: Camera,
    val earth_date: String,
    val id: Int,
    val img_src: String,
    val rover: Rover,
    val sol: Int
) : Parcelable