package com.elsonmathew.android.nasaproject.models

data class ManifestPhoto(
    val cameras: List<String>,
    val sol: Int,
    val total_photos: Int
)