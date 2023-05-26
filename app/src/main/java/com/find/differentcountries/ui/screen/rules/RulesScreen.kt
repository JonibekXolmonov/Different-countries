package com.find.differentcountries.ui.screen.rules

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.find.differentcountries.R
import com.find.differentcountries.ui.theme.ButtonStroke
import com.find.differentcountries.ui.utils.BackButton
import com.find.differentcountries.ui.utils.BackgroundImage
import com.find.differentcountries.ui.utils.Contants.ARG
import com.find.differentcountries.ui.utils.Contants.BRZ
import com.find.differentcountries.ui.utils.Contants.PRT
import com.find.differentcountries.ui.utils.Contants.argentinaElements
import com.find.differentcountries.ui.utils.Contants.brazilElements
import com.find.differentcountries.ui.utils.Contants.portugalElements
import com.find.differentcountries.ui.utils.CountryName
import com.find.differentcountries.ui.utils.noRippleClickable
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RulesScreen(
    onBack: () -> Unit,
    onVibrate: () -> Unit
) {

    var countryName by remember { mutableStateOf(ARG) }
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {

        BackgroundImage()

        Column(modifier = Modifier.fillMaxWidth().padding(top = 32.dp)) {

            Box(modifier = Modifier.fillMaxWidth()) {
                BackButton(onBack)
            }

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
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                            onVibrate()
                        })

                CountryName(
                    modifier = Modifier
                        .padding(9.dp)
                        .weight(1f),
                    innerModifier = Modifier.fillMaxWidth(),
                    countryName = countryName
                )

                Image(
                    painter = painterResource(id = R.drawable.arrow_right),
                    contentDescription = "right",
                    modifier = Modifier
                        .size(53.dp, 70.dp)
                        .noRippleClickable {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                            onVibrate()
                        })
            }

            Box(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                CountryElements(pagerState = pagerState) {
                    countryName = getCountryName(it)
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CountryElements(pagerState: PagerState, onPageChange: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(ButtonStroke.copy(alpha = 0.6f))
            .padding(horizontal = 6.dp, vertical = 24.dp)
    ) {

        HorizontalPager(pageCount = 3, state = pagerState) {
            PagerContent(it)
        }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect {
            onPageChange(it)
        }
    }
}

@Composable
fun PagerContent(pageIndex: Int) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(36.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(getItems(pageIndex)) {
            Image(
                painter = painterResource(id = it),
                contentDescription = "element",
                contentScale = ContentScale.Fit,
                modifier = Modifier.height(75.dp)
            )
        }
    }
}

fun getCountryName(pageIndex: Int) = if (pageIndex == 0) ARG else if (pageIndex == 1) BRZ else PRT

fun getItems(pageIndex: Int) =
    if (pageIndex == 0) argentinaElements else if (pageIndex == 1) brazilElements else portugalElements
