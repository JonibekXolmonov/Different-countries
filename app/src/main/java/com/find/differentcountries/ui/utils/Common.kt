package com.find.differentcountries.ui.utils

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.find.differentcountries.R
import com.find.differentcountries.ui.theme.ButtonContentBack
import com.find.differentcountries.ui.theme.ButtonStroke
import com.find.differentcountries.ui.theme.TextColor

@Composable
fun MainGameShape(
    modifier: Modifier,
    innerModifier: Modifier,
    contentImage: Int = 0,
    strokeColor: Color = ButtonStroke,
    contentColor: Color = ButtonContentBack,
    strokeWidth: Dp,
    outerShape: Shape,
    innerShape: Shape,
    shadowRadius: Dp,
    contentPaddingValues: PaddingValues = PaddingValues(),
    onClick: (Int) -> Unit = {},
    content: @Composable () -> Unit

) {

    Box(
        modifier = modifier.advancedShadow(
            offsetY = 4.dp,
            shadowBlurRadius = 10.dp,
            cornersRadius = shadowRadius
        )
    ) {
        Box(
            modifier = Modifier
                .background(strokeColor, outerShape)
                .padding(all = strokeWidth)
                .noRippleClickable { onClick(contentImage) }
        ) {
            Box(
                modifier = innerModifier
                    .background(contentColor, innerShape)
                    .innerShadow(
                        blur = 4.dp,
                        offsetY = 4.dp,
                        cornersRadius = shadowRadius
                    )
                    .padding(paddingValues = contentPaddingValues),
                contentAlignment = Alignment.Center
            ) {
                content()
            }
        }
    }
}

@Composable
fun BackButton(onClick: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.ic_back), contentDescription = "back",
        modifier = Modifier
            .padding(top = 10.dp, start = 9.dp)
            .size(53.dp)
            .noRippleClickable { onClick() }
    )
}

@Composable
fun BackgroundImage() {
    Image(
        painter = painterResource(id = R.drawable.main_background),
        contentDescription = "menu_back",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun CountryName(
    modifier: Modifier,
    innerModifier: Modifier,
    countryName: String,
) {
    MainGameShape(
        modifier = modifier,
        innerModifier = innerModifier,
        strokeWidth = 7.dp,
        outerShape = RoundedCornerShape(25.dp),
        innerShape = RoundedCornerShape(25.dp),
        shadowRadius = 25.dp,
        contentPaddingValues = PaddingValues(vertical = 8.dp),
    ) {
        var change by remember { mutableStateOf(false) }
        change = !change

//        AnimatedContent(targetState = change,transitionSpec = {
//            EnterTransition.None with ExitTransition.None
//        }) {
        Text(
            text = countryName,
            style = TextStyle(fontSize = 32.sp, color = TextColor),
            fontFamily = FontFamily(Font(R.font.gugi)),
            textAlign = TextAlign.Center
        )
//        }
    }
}