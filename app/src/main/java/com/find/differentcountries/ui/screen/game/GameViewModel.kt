package com.find.differentcountries.ui.screen.game

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.find.differentcountries.R
import com.find.differentcountries.ui.utils.Contants.EMPTY
import com.find.differentcountries.ui.utils.Question
import com.find.differentcountries.ui.utils.QuestionOptionsProviderImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val provider: QuestionOptionsProviderImp
) : ViewModel() {

    private var _allQuestions = mutableStateListOf<Question>()

    private var _optionsList = mutableStateOf(
        listOf(
            R.drawable.test_image,
            R.drawable.test_image,
            R.drawable.test_image,
            R.drawable.test_image
        )
    )

    val optionsList get() = _optionsList

    private var _correctId = mutableStateOf(R.drawable.test_image)

    private var _score = mutableStateOf(0)
    val score get() = _score

    private var _questionNumber = mutableStateOf(-1)
    val questionNumber get() = _questionNumber

    private var _option1 = mutableStateOf(R.drawable.test_image)

    private var _option2 = mutableStateOf(R.drawable.test_image)

    private var _option3 = mutableStateOf(R.drawable.test_image)

    private var _countryIndex = mutableStateOf(0)

    private var _countryName = mutableStateOf(EMPTY)
    val countryName get() = _countryName

    private var _currentQuestion = mutableStateOf(Question())
    val currentQuestion get() = _currentQuestion

    fun startGame() {
        _allQuestions.clear()
        _optionsList.value = listOf(
            R.drawable.test_image,
            R.drawable.test_image,
            R.drawable.test_image,
            R.drawable.test_image
        )
        _countryName.value = EMPTY
        _questionNumber.value = -1
        _score.value = 0
        nextOrNewQuestion()
    }

    fun previousQuestion() {
        viewModelScope.launch {
            _questionNumber.value--
            if (_questionNumber.value >= 0) {
                getQuestion()
            } else {
                _questionNumber.value = 0
            }
        }
    }

    private fun getQuestion() {
        val question = _allQuestions[_questionNumber.value]
        _currentQuestion.value = question
        _optionsList.value =
            listOf(
                question.option1,
                question.option2,
                question.option3,
                question.option4
            ).shuffled()
    }

    fun nextOrNewQuestion() {
        if (questionNumber.value != _allQuestions.size - 1) {
            _questionNumber.value++
            getQuestion()
        } else {
            _countryIndex.value = provider.generateRandomCountryName()
            _correctId.value = provider.generateCorrectAnswer(_countryIndex.value)
            _countryName.value = provider.getCountryNameByIndex(_countryIndex.value)
            _option1.value = provider.getQuestionOption(_countryIndex.value)
            _option2.value = provider.getQuestionOption(_countryIndex.value)
            _option3.value = provider.getQuestionOption(_countryIndex.value)
            currentQuestion.value =
                Question(
                    country = _countryName.value,
                    option1 = _correctId.value,
                    option2 = _option1.value,
                    option3 = _option2.value,
                    option4 = _option3.value,
                    correctOption = _correctId.value
                )
            _allQuestions.add(
                _currentQuestion.value
            )
            _optionsList.value =
                listOf(
                    currentQuestion.value.option1,
                    currentQuestion.value.option2,
                    currentQuestion.value.option3,
                    currentQuestion.value.option4
                ).shuffled()
            _questionNumber.value++
        }

    }

    fun increaseScore() {
        viewModelScope.launch {
            score.value++
            _allQuestions[_questionNumber.value].isAnswered = true
        }
    }
}