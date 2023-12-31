# Compose RatingBar Repository

[![Download](https://jitpack.io/v/xavijimenezmulet/ratingbar-jetpackcompose.svg)](https://jitpack.io/#xavijimenezmulet/ratingbar-jetpackcompose)
[![API](https://img.shields.io/badge/API-26%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=26)
![Build Status](https://github.com/Dhaval2404/ImagePicker/workflows/Build/badge.svg)
![PR Welcome](https://camo.githubusercontent.com/b0ad703a46e8b249ef2a969ab95b2cb361a2866ecb8fe18495a2229f5847102d/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f5052732d77656c636f6d652d627269676874677265656e2e737667)
![Language](https://img.shields.io/badge/language-Kotlin-orange.svg)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)
[![Open Source Love](https://badges.frapsoft.com/os/v1/open-source.svg?v=102)](https://opensource.org/licenses/Apache-2.0)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](https://github.com/xavijimenezmulet/SnackBar/blob/main/LICENSE)

[![ko-fi](https://www.ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/xavijimenez)

This repository contains a custom RatingBar component built for Jetpack Compose, a modern UI toolkit for Android app development. The RatingBar component provides a user-friendly way to rate items or content by allowing users to select a specific number of stars. It offers customization options such as star color, size, and maximum rating value.

<img src="https://github.com/xavijimenezmulet/shapes-for-jetpackcompose/assets/44567433/8ce6b84a-fea6-4a52-994e-33a70f38b30b" width="250" height="500"/> <img src="https://github.com/xavijimenezmulet/ratingbar-jetpackcompose/assets/44567433/b10f9cab-cf69-4704-b962-fcee197a9176" width="250" height="500"/>

## Features

Customizable Images: With this rating bar, you have the freedom to customize the rating icons using various image sources such as shapes, image vectors, drawables, and even async images loaded from a URL.

Shape Customization: You can use different shapes to represent the rating icons, allowing you to create unique and visually appealing rating bars.

Image Vector Support: The rating bar supports image vectors, which are scalable and resolution-independent. This enables you to use vector-based icons as rating symbols for a crisp and sharp visual presentation.

Drawable Integration: You can utilize drawables as rating icons, offering a wide range of possibilities for icon designs and styles. Drawables can be easily customized and applied to the rating bar.

AsyncImage (URL) Compatibility: The rating bar also supports loading rating icons asynchronously from a URL using the AsyncImage component. This allows you to dynamically fetch and display rating icons from remote sources.

## Getting Started

To get started with the Compose Shapes Repository, simply clone the repository and import the desired shape utilities or custom shapes into your Compose project. You can then use these shapes in your Compose UI code by applying them to the appropriate components using the provided extensions.

```bash
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
```

Step 2. Add the dependency:

```bash
dependencies {
	implementation "com.github.xavijimenezmulet:ratingbar-jetpackcompose:$latest_version"
}
```

Simple usage:

```kotlin
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
```

Any help and improve the code will be welcome.
