package com.find.differentcountries.ui.screen.menu

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.find.differentcountries.R
import com.find.differentcountries.ui.theme.TextColor
import com.find.differentcountries.ui.utils.BackgroundImage
import com.find.differentcountries.ui.utils.MainGameShape

@Composable
fun MenuScreen(
    onGameStart: () -> Unit, onSettingsClick: () -> Unit, onRulesClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        BackgroundImage()

        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(48.dp),
            ) {

                MainGameShape(modifier = Modifier
                    .padding(horizontal = 40.dp)
                    .height(80.dp)
                    .fillMaxWidth(),
                    innerModifier = Modifier.fillMaxWidth(),
                    strokeWidth = 9.dp,
                    shadowRadius = 30.dp,
                    outerShape = RoundedCornerShape(30.dp),
                    innerShape = RoundedCornerShape(30.dp),
                    contentPaddingValues = PaddingValues(vertical = 9.dp),
                    onClick = {
                        onSettingsClick()
                    }) {
                    MenuText(textId = R.string.settings)
                }

                MainGameShape(modifier = Modifier
                    .padding(horizontal = 40.dp)
                    .height(80.dp)
                    .fillMaxWidth(),
                    innerModifier = Modifier.fillMaxWidth(),
                    strokeWidth = 9.dp,
                    shadowRadius = 30.dp,
                    outerShape = RoundedCornerShape(30.dp),
                    innerShape = RoundedCornerShape(30.dp),
                    contentPaddingValues = PaddingValues(vertical = 9.dp),
                    onClick = {
                        onRulesClick()
                    }) {
                    MenuText(textId = R.string.rules)
                }

                MainGameShape(modifier = Modifier
                    .padding(horizontal = 40.dp)
                    .height(80.dp)
                    .fillMaxWidth(),
                    innerModifier = Modifier.fillMaxWidth(),
                    strokeWidth = 9.dp,
                    shadowRadius = 30.dp,
                    outerShape = RoundedCornerShape(30.dp),
                    innerShape = RoundedCornerShape(30.dp),
                    contentPaddingValues = PaddingValues(vertical = 9.dp),
                    onClick = {
                        onGameStart()
                    }) {
                    MenuText(textId = R.string.game)
                }
            }
        }
    }
}

@Composable
fun MenuText(textId: Int) {
    Text(
        text = stringResource(id = textId), style = TextStyle(
            fontSize = 40.sp,
            color = TextColor,
            fontFamily = FontFamily(Font(R.font.gugi)),
            textAlign = TextAlign.Center
        )
    )
}