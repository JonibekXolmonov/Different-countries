package com.find.differentcountries.ui.utils

interface Provider {

    fun generateRandomCountryName(): Int

    fun getCountryNameByIndex(index: Int): String

    fun generateCorrectAnswer(country: Int): Int

    fun getQuestionOption(correctAnswerCountry: Int): Int

    fun randomGeneratorInRange(start: Int, end: Int): Int

//    fun List<Int>.nonSequenceRandom(lastRandomIndex: Int, onLastFound: (Int) -> Unit): Int

}