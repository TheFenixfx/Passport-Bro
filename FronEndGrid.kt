package com.fenix.cleansmall.base

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.fenix.cleansmall.screens.OnExploreItemClicked
import com.fenix.cleansmall.ui.BottomSheetShape
import com.fenix.cleansmall.uidata.ExploreModel
import com.fenix.cleansmall.R
import com.fenix.cleansmall.screens.OnSliderImageItemClicked
import placesmodels.PlacesSuggestion
import placesmodels.SliderImage


@Composable
fun FrontEndGrid(
    widthSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
    title: String,
    exploreList: List<SliderImage>,
    onItemClicked: OnSliderImageItemClicked
) {
    Surface(modifier = modifier.fillMaxSize(), color = Color.White, shape = BottomSheetShape) {
        Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.caption.copy(color = Color.White)
            )
            Spacer(Modifier.height(8.dp))

            LazyVerticalGrid(
                columns = GridCells.Adaptive(200.dp),
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                content = {
                    items(exploreList) { exploreItem ->
                        when (widthSize) {
                            WindowWidthSizeClass.Medium -> {
                                LazyItemColumn(
                                    modifier = Modifier.fillMaxWidth(),
                                    item = exploreItem,
                                    onItemClicked = onItemClicked
                                )
                            }
                            else -> {
                                LazyItemRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    item = exploreItem,
                                    onItemClicked = onItemClicked
                                )
                            }
                        }
                    }
                    item(span = {
                        // Span the whole bottom row of grid items to add space at the bottom of the grid.
                        GridItemSpan(maxLineSpan)
                    }) {
                        Spacer(
                            modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars)
                        )
                    }
                }
            )
        }
    }
}

@Preview
@Composable
private fun LazyItemRow(
    modifier: Modifier = Modifier,
    item: SliderImage = SliderImage("", "", "", false),
    onItemClicked: OnSliderImageItemClicked = {}
) {
    Column {
        Row(modifier = modifier
            .clickable { onItemClicked(item) }
            .padding(top = 12.dp, bottom = 12.dp)) {
            ImageContainer(modifier = Modifier.width(150.dp)) {
                ImageItem(item)
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                verticalAlignment = CenterVertically
            ) {
                Column {
                    Text(
                        modifier = Modifier
                            .width(200.dp)
                            .align(Alignment.CenterHorizontally)
                            .padding(start = 40.dp, bottom = 5.dp),
                        text = "Lorem Ipsum Title",
                        style = MaterialTheme.typography.subtitle1
                    )
                    /*           Spacer(modifier = Modifier.height(4.dp))*/
                    Text(
                        modifier = Modifier
                            .width(125.dp)
                            .align(Alignment.CenterHorizontally),
                        text = "Lorem Ipsum Description",
                        style = MaterialTheme.typography.caption.copy(
                            color = Color.Black
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun LazyItemColumn(
    modifier: Modifier = Modifier,
    item: SliderImage,
    onItemClicked: OnSliderImageItemClicked
) {
    Row(modifier = modifier
        .clickable { onItemClicked }
        .padding(top = 12.dp, bottom = 12.dp)) {
    }

}


@Composable
private fun ImageItem(item: SliderImage) {
    Box {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(item.presigned)
                .crossfade(true)
                .build()
        )
        if (painter.state is AsyncImagePainter.State.Loading) {
            Image(
                painter = painterResource(id = R.drawable.ic_kuaishou),
                contentDescription = null,
                modifier = Modifier
                    .size(36.dp)
                    .align(Center)
                    .padding(PaddingValues(all = 5.dp))
            )
        }
        Image(
            painter = painter, contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
        )

    }
}

@Composable
private fun ImageContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(modifier.aspectRatio(1f), RoundedCornerShape(4.dp)) {
        content()
    }
}

