package com.xavijimenezmulet.ratingbar

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.xavijimenezmulet.shapes.LemonShape
import com.xavijimenezmulet.shapes.SquareShape
import com.xavijimenezmulet.shapes.StarShape
import com.xavijimenezmulet.shapes.TicketShape

/**
 *   @author xavierjimenez
 *   @since 7/7/23
 *   @email xavijimenezmulet@macaqueconsulting.com
 */

interface Data {
    val label: String
}

data class ImageVectorData(
    val imageVectorData: ImageVector,
    override val label: String
) : Data

data class DrawableData(
    @DrawableRes val drawable: Int,
    override val label: String
) : Data

data class UrlData(
    val url: String,
    override val label: String
) : Data

data class ShapeData(
    val shape: Shape,
    override val label: String
) : Data

@Composable
fun ImageRatingBar(
    data: List<Data>,
    rating: Int,
    setRating: (rating: Int) -> Unit,
    modifier: Modifier = Modifier,
    divider: Boolean = true
) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
    ) {
        if (divider) {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 24.dp,
                        start = 44.dp,
                        end = 44.dp,
                    ),
                thickness = 4.dp,
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
            modifier = modifier
                .height(80.dp)
                .fillMaxWidth(),
        ) {
            data.mapIndexed { index, smileyData ->
                ImageItem(
                    data = smileyData,
                    isSelected = index == rating,
                    index = index,
                    count = data.size,
                    modifier = Modifier.weight(1F),
                    onClick = {
                        setRating(index)
                    },
                )
            }
        }
    }
}

@Composable
private fun ImageItem(
    data: Data,
    isSelected: Boolean,
    index: Int,
    count: Int,
    modifier: Modifier = Modifier,
    animationDurationInMillis: Int = 300,
    onClick: () -> Unit,
) {
    val padding: Dp by animateDpAsState(
        targetValue = if (isSelected) {
            0.dp
        } else {
            4.dp
        },
        animationSpec = tween(
            durationMillis = animationDurationInMillis,
            easing = LinearEasing,
        ),
    )
    val size: Dp by animateDpAsState(
        targetValue = if (isSelected) {
            52.dp
        } else {
            44.dp
        },
        animationSpec = tween(
            durationMillis = animationDurationInMillis,
            easing = LinearEasing,
        ),
    )
    val saturation: Float by animateFloatAsState(
        targetValue = if (isSelected) {
            1F
        } else {
            0F
        },
        animationSpec = tween(
            durationMillis = animationDurationInMillis,
            easing = LinearEasing,
        ),
    )
    val matrix = ColorMatrix().apply {
        setToSaturation(saturation)
    }

    val color = if (isSelected) {
        if (index < (count / 2)) {
            Color.Red
        } else if (index > (count / 2)) {
            Color(0xFF279033)
        } else {
            Color.Black
        }
    } else {
        Color.DarkGray
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth(),
    ) {
        Image(
            data = data,
            size = size,
            padding = padding,
            color = color,
            matrix = matrix,
            onClick = onClick
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = data.label,
            color = color,
            fontWeight = if (isSelected) {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            },
        )
    }
}

@Composable
private fun Image(
    data: Data,
    size: Dp,
    padding: Dp,
    matrix: ColorMatrix,
    color: Color,
    onClick: () -> Unit
) {
    when (data) {
        is UrlData -> {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data.url)
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(
                        top = padding,
                    )
                    .size(size)
                    .clip(CircleShape)
                    .clickable {
                        onClick()
                    },
                colorFilter = ColorFilter.colorMatrix(matrix)
            )
        }

        is DrawableData -> {
            androidx.compose.foundation.Image(
                painter = painterResource(id = data.drawable),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(
                        top = padding,
                    )
                    .size(size)
                    .clip(CircleShape)
                    .clickable {
                        onClick()
                    },
                colorFilter = ColorFilter.colorMatrix(matrix)
            )
        }

        is ImageVectorData -> {
            androidx.compose.foundation.Image(
                imageVector = data.imageVectorData,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(
                        top = padding,
                    )
                    .size(size)
                    .clip(CircleShape)
                    .clickable {
                        onClick()
                    },
                colorFilter = ColorFilter.colorMatrix(matrix)
            )
        }

        is ShapeData -> {
            Box(
                modifier = Modifier
                    .padding(
                        top = padding,
                    )
                    .size(size)
                    .clip(data.shape)
                    .background(color = color)
                    .clickable {
                        onClick()
                    },
            )
        }
    }
}

@Preview(name = "WithLine")
@Composable
fun ImageRatingBarSample() {
    val data: List<Data> = listOf(
        UrlData(
            "https://pics.freeicons.io/uploads/icons/png/12321319441547544872-512.png",
            "Terrible"
        ),
        UrlData("https://pics.freeicons.io/uploads/icons/png/751538241547544850-512.png", "Bad"),
        UrlData("https://pics.freeicons.io/uploads/icons/png/14514278171547544852-512.png", "Okay"),
        UrlData("https://pics.freeicons.io/uploads/icons/png/2403514581547544849-512.png", "Good"),
        UrlData(
            "https://pics.freeicons.io/uploads/icons/png/19965171131547544843-512.png",
            "Awesome"
        ),
    )

    val (rating, setRating) = remember {
        mutableStateOf(data.size / 2)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Image (url) RatingBar")
        ImageRatingBar(
            data = data,
            rating = rating,
            setRating = setRating,
        )
    }
}

@Preview(name = "WithoutLine")
@Composable
fun ImageRatingBarWithoutLineSample() {
    val data: List<Data> = listOf(
        UrlData(
            "https://pics.freeicons.io/uploads/icons/png/12321319441547544872-512.png",
            "Terrible"
        ),
        UrlData("https://pics.freeicons.io/uploads/icons/png/751538241547544850-512.png", "Bad"),
        UrlData("https://pics.freeicons.io/uploads/icons/png/14514278171547544852-512.png", "Okay"),
        UrlData("https://pics.freeicons.io/uploads/icons/png/2403514581547544849-512.png", "Good"),
        UrlData(
            "https://pics.freeicons.io/uploads/icons/png/19965171131547544843-512.png",
            "Awesome"
        ),
    )

    val (rating, setRating) = remember {
        mutableStateOf(data.size / 2)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Image (asynk) RatingBar No Divider")
        ImageRatingBar(
            data = data,
            rating = rating,
            setRating = setRating,
            divider = false
        )
    }
}

@Preview(name = "WithLine")
@Composable
fun DrawableRatingBarSample() {
    val data: List<Data> = listOf(
        DrawableData(R.drawable.ic_cat_terrible, "Terrible"),
        DrawableData(R.drawable.ic_cat_bad, "Bad"),
        DrawableData(R.drawable.ic_cat_okay, "Okay"),
        DrawableData(R.drawable.ic_cat_good, "Good"),
        DrawableData(R.drawable.ic_cat_awesome, "Awesome"),
    )


    val (rating, setRating) = remember {
        mutableStateOf(data.size / 2)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Drawable RatingBar")
        ImageRatingBar(
            data = data,
            rating = rating,
            setRating = setRating,
        )
    }
}

@Preview(name = "WithoutLine")
@Composable
fun DrawableRatingBarWithoutLineSample() {
    val data: List<Data> = listOf(
        DrawableData(R.drawable.ic_cat_terrible, "Terrible"),
        DrawableData(R.drawable.ic_cat_bad, "Bad"),
        DrawableData(R.drawable.ic_cat_okay, "Okay"),
        DrawableData(R.drawable.ic_cat_good, "Good"),
        DrawableData(R.drawable.ic_cat_awesome, "Awesome"),
    )

    val (rating, setRating) = remember {
        mutableStateOf(data.size / 2)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Drawable RatingBar No Divider")
        ImageRatingBar(
            data = data,
            rating = rating,
            setRating = setRating,
            divider = false
        )
    }
}

@Preview(name = "WithLine")
@Composable
fun ShapeRatingBarSample() {
    val data: List<Data> = listOf(
        ShapeData(shape = StarShape(5), "Terrible"),
        ShapeData(shape = StarShape(6), "Bad"),
        ShapeData(shape = StarShape(7), "Okay"),
        ShapeData(shape = StarShape(8), "Good"),
        ShapeData(shape = StarShape(9), "Awesome"),
    )


    val (rating, setRating) = remember {
        mutableStateOf(data.size / 2)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Shape RatingBar")
        ImageRatingBar(
            data = data,
            rating = rating,
            setRating = setRating,
        )
    }
}

@Preview(name = "WithoutLine")
@Composable
fun ShapeRatingBarWithoutLineSample() {
    val data: List<Data> = listOf(
        ShapeData(shape = StarShape(5), "Terrible"),
        ShapeData(shape = StarShape(6), "Bad"),
        ShapeData(shape = StarShape(7), "Okay"),
        ShapeData(shape = StarShape(8), "Good"),
        ShapeData(shape = StarShape(9), "Awesome"),
    )

    val (rating, setRating) = remember {
        mutableStateOf(data.size / 2)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Shape RatingBar No Divider")
        ImageRatingBar(
            data = data,
            rating = rating,
            setRating = setRating,
            divider = false
        )
    }
}

@Preview(name = "WithLine")
@Composable
fun VectorRatingBarSample() {
    val data: List<Data> = listOf(
        ImageVectorData(Icons.Rounded.Face, "Terrible"),
        ImageVectorData(Icons.Rounded.Face, "Bad"),
        ImageVectorData(Icons.Rounded.Face, "Okay"),
        ImageVectorData(Icons.Rounded.Face, "Good"),
        ImageVectorData(Icons.Rounded.Face, "Awesome"),
    )


    val (rating, setRating) = remember {
        mutableStateOf(data.size / 2)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Image Vector RatingBar")
        ImageRatingBar(
            data = data,
            rating = rating,
            setRating = setRating,
        )
    }
}

@Preview(name = "WithoutLine")
@Composable
fun VectorRatingBarWithoutLineSample() {
    val data: List<Data> = listOf(
        ImageVectorData(Icons.Rounded.Face, "Terrible"),
        ImageVectorData(Icons.Rounded.Face, "Bad"),
        ImageVectorData(Icons.Rounded.Face, "Okay"),
        ImageVectorData(Icons.Rounded.Face, "Good"),
        ImageVectorData(Icons.Rounded.Face, "Awesome"),
    )

    val (rating, setRating) = remember {
        mutableStateOf(data.size / 2)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Image Vector RatingBar No Divider")
        ImageRatingBar(
            data = data,
            rating = rating,
            setRating = setRating,
            divider = false
        )
    }
}