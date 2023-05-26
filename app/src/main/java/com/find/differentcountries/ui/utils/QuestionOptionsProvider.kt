package com.find.differentcountries.ui.utils

import com.find.differentcountries.ui.utils.Contants.ARG
import com.find.differentcountries.ui.utils.Contants.ARG_INDEX
import com.find.differentcountries.ui.utils.Contants.BRZ
import com.find.differentcountries.ui.utils.Contants.BRZ_INDEX
import com.find.differentcountries.ui.utils.Contants.PRT
import com.find.differentcountries.ui.utils.Contants.argentinaElements
import com.find.differentcountries.ui.utils.Contants.brazilElements
import com.find.differentcountries.ui.utils.Contants.portugalElements
import javax.inject.Inject
import kotlin.random.Random

class QuestionOptionsProviderImp @Inject constructor() : Provider {

    override fun generateRandomCountryName(): Int {
        val randomIndex = randomGeneratorInRange(1, 3)
        return randomIndex
    }

    override fun getCountryNameByIndex(index: Int): String {
        return when (index) {
            ARG_INDEX -> ARG
            BRZ_INDEX -> BRZ
            else -> PRT
        }
    }

    override fun generateCorrectAnswer(country: Int): Int {
        return when (country) {
            ARG_INDEX -> argentinaElements.random()
            BRZ_INDEX -> brazilElements.random()
            else -> portugalElements.random()
        }
    }

    override fun getQuestionOption(correctAnswerCountry: Int): Int {
        val random = randomGeneratorInRange(1, 2)
        return when (correctAnswerCountry) {
            ARG_INDEX -> {
                if (random == 1) brazilElements.random()
                else portugalElements.random()
            }
            BRZ_INDEX -> {
                if (random == 1) argentinaElements.random()
                else portugalElements.random()
            }
            else -> {
                if (random == 1) brazilElements.random()
                else argentinaElements.random()
            }
        }
    }


    override fun randomGeneratorInRange(start: Int, end: Int): Int {
        return (start..end).random()
    }

//    override fun List<Int>.nonSequenceRandom(
//        lastRandomIndex: Int,
//        onLastFound: (Int) -> Unit
//    ): Int {
//        var current = randomGeneratorInRange(0, 14)
//        while (current == lastIndex) {
//            current = randomGeneratorInRange(0, 14)
//        }
//        onLastFound(current)
//        return this[current]
//    }
}