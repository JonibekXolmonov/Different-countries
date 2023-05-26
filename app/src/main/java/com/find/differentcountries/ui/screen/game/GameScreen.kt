package com.find.differentcountries.ui.screen.game

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.find.differentcountries.R
import com.find.differentcountries.ui.screen.score.ScoreDialog
import com.find.differentcountries.ui.theme.*
import com.find.differentcountries.ui.utils.*

@Composable
fun GameScreen(
    onBack: () -> Unit,
    onVibrate: () -> Unit,
    viewModel: GameViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit){ viewModel.startGame() }

    val countryName by remember { viewModel.countryName }
    val currentQuestion by remember { viewModel.currentQuestion }
    val score by remember { viewModel.score }
    val optionsList by remember { viewModel.optionsList }
    var gameFinish by remember { mutableStateOf(false) }

    if (gameFinish){
        ScoreDialog(score = score){
            gameFinish = false
            viewModel.startGame()
            onVibrate()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        BackgroundImage()


        Column(modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp)) {

            TopPart(score = score, onBack = onBack)

            Row(
                modifier = Modifier.padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_left),
                    contentDescription = "left",
                    modifier = Modifier
                        .size(53.dp, 70.dp)
                        .noRippleClickable {
                            viewModel.previousQuestion()
                            onVibrate()
                        }
                )

                CountryName(
                    modifier = Modifier
                        .padding(9.dp)
                        .weight(1f),
                    innerModifier = Modifier.fillMaxWidth(),
                    countryName = countryName
                )

                Image(painter = painterResource(id = R.drawable.arrow_right),
                    contentDescription = "right",
                    modifier = Modifier
                        .size(53.dp, 70.dp)
                        .noRippleClickable {
                            if (currentQuestion.isAnswered)
                                viewModel.nextOrNewQuestion()
                            onVibrate()
                        })
            }

            Box(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 80.dp)
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Elements(question = currentQuestion,
                    onPictureSelected = { isAnsweredCorrectly ->
                        onVibrate()
                        if (isAnsweredCorrectly) {
                            viewModel.increaseScore()
                        } else {
                            gameFinish = true
                        }
                    },
                    listCollected = optionsList
                )
            }
        }
    }
}

@Composable
fun Elements(
    modifier: Modifier = Modifier,
    question: Question,
    listCollected:List<Int>,
    onPictureSelected: (Boolean) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RowElements(
            elements = listCollected.take(2),
            onPictureSelected = onPictureSelected,
            question = question
        )
        RowElements(
            elements = listCollected.takeLast(2),
            onPictureSelected = onPictureSelected,
            question = question
        )
    }
}

@Composable
fun RowElements(
    modifier: Modifier = Modifier,
    question: Question,
    elements: List<Int>,
    onPictureSelected: (Boolean) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        QuestionImage(
            modifier = Modifier.weight(1f),
            imageId = elements[0],
            question = question,
            onPictureSelected = onPictureSelected
        )
        QuestionImage(
            modifier = Modifier.weight(1f),
            imageId = elements[1],
            question = question,
            onPictureSelected = onPictureSelected
        )
    }
}

@Composable
fun TopPart(
    modifier: Modifier = Modifier, score: Int, onBack: () -> Unit
) {

    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        BackButton(onClick = onBack)

        MainGameShape(
            modifier = modifier
                .padding(end = 9.dp, top = 10.dp)
                .height(62.dp),
            innerModifier = Modifier,
            strokeWidth = 5.dp,
            outerShape = RoundedCornerShape(25.dp),
            innerShape = RoundedCornerShape(25.dp),
            shadowRadius = 25.dp,
            contentPaddingValues = PaddingValues(vertical = 8.dp, horizontal = 12.dp)
        ) {
            Text(
                text = score.toString(), style = TextStyle(
                    fontSize = 32.sp,
                    color = TextColor,
                    fontFamily = FontFamily(Font(R.font.gugi)),
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun QuestionImage(
    modifier: Modifier = Modifier,
    imageId: Int,
    question: Question,
    onPictureSelected: (Boolean) -> Unit
) {
    Log.d("TAG", "QuestionImage: $question")

    var colorStroke by remember { mutableStateOf(ButtonStroke) }
    var colorContent by remember { mutableStateOf(ButtonContentBack) }

    LaunchedEffect(imageId, question, modifier) {
        colorStroke = ButtonStroke
        colorContent = ButtonContentBack
    }

    MainGameShape(modifier = modifier,
        strokeColor = if (question.isAnswered && imageId == question.correctOption) CorrectButtonStroke else colorStroke,
        innerModifier = modifier,
        contentColor = if (question.isAnswered && imageId == question.correctOption) CorrectButtonContent else colorContent,
        contentImage = imageId,
        strokeWidth = 15.dp,
        outerShape = RoundedCornerShape(10.dp),
        innerShape = RoundedCornerShape(10.dp),
        shadowRadius = 10.dp,
        contentPaddingValues = PaddingValues(24.dp),
        onClick = {
            if (!question.isAnswered) {
                colorStroke =
                    if (question.correctOption == it) CorrectButtonStroke else ErrorButtonStroke
                colorContent =
                    if (question.correctOption == it) CorrectButtonContent else ErrorButtonContent
                onPictureSelected(question.correctOption == it)
            }
        }) {
        GlideImage(
            model = imageId,
            contentDescription = "image",
            modifier = Modifier.size(75.dp),
            contentScale = ContentScale.Fit
        )
    }
}