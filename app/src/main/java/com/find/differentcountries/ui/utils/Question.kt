package com.find.differentcountries.ui.utils

import com.find.differentcountries.R

data class Question(
    val country: String = "",
    val option1: Int = R.drawable.test_image,
    val option2: Int = R.drawable.test_image,
    val option3: Int = R.drawable.test_image,
    val option4: Int = R.drawable.test_image,
    val correctOption: Int = R.drawable.test_image,
    var isAnswered: Boolean = false
)
