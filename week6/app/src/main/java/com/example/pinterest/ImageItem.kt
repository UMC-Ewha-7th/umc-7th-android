package com.example.pinterest

data class ImageItem(
    val image: Int,
    val title: String? = null,
    var height: Int = (400..600 step 100).toList().random()
)
