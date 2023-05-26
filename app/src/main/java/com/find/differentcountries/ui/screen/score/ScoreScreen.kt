package com.find.differentcountries.ui.screen.score

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.find.differentcountries.R
import com.find.differentcountries.ui.theme.TextColor
import com.find.differentcountries.ui.utils.BackButton
import com.find.differentcountries.ui.utils.BackgroundImage
import com.find.differentcountries.ui.utils.MainGameShape

@Composable
fun ScoreDialog(
    score: Int,
    onBack: () -> Unit
) {

    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        val context = LocalContext.current
        val windowManager =
            remember { context.getSystemService(Context.WINDOW_SERVICE) as WindowManager }

        val metrics = DisplayMetrics().apply {
            windowManager.defaultDisplay.getRealMetrics(this)
        }
        val (width, height) = with(LocalDensity.current) {
            Pair(metrics.widthPixels.toDp(), (metrics.heightPixels).toDp())
        }

        ScoreScreen(
            width,
            height,
            score,
            onBack
        )
    }
}

@Composable
fun ScoreScreen(
    width: Dp,
    height: Dp,
    score: Int,
    onBack: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().requiredSize(width, height)) {

        BackgroundImage()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp)
        ) {

            BackButton(onBack)

            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                MainGameShape(
                    modifier = Modifier
                        .padding(horizontal = 42.dp)
                        .height(270.dp),
                    innerModifier = Modifier.fillMaxSize(),
                    strokeWidth = 26.dp,
                    outerShape = RoundedCornerShape(10.dp),
                    innerShape = RoundedCornerShape(10.dp),
                    shadowRadius = 10.dp
                ) {
                    Text(
                        text = score.toString(), style = TextStyle(
                            fontSize = 96.sp,
                            color = TextColor,
                            fontFamily = FontFamily(Font(R.font.gugi)),
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        }
    }
}