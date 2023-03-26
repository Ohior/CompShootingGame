package com.example.ohiorgamelib

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ohMoveImageRepeatable(
    imagePropertiesX: CharacterDataclass,
    imagePropertiesY: CharacterDataclass,
    @DrawableRes image: Int,
    contentDescription: String?,
): Pair<Float, Float> {
    val animateX = remember {
        Animatable(imagePropertiesX.startValue)
    }
    val animateY = remember {
        Animatable(imagePropertiesY.startValue)
    }

    LaunchedEffect(key1 = animateX) {
        animateX.animateTo(
            imagePropertiesX.endValue,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = imagePropertiesX.speedMillis,
                    easing = LinearEasing
                )
            )
        )
    }
    LaunchedEffect(key1 = animateY) {
        animateY.animateTo(
            imagePropertiesY.endValue,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = imagePropertiesY.speedMillis,
                    easing = LinearEasing
                )
            )
        )
    }
    Image(
        modifier = Modifier
            .wrapContentSize()
            .offset(animateX.value.dp, animateY.value.dp),
        painter = painterResource(id = image),
        contentDescription = contentDescription,
        contentScale = ContentScale.None
    )
    return Pair(animateX.value, animateY.value)
}


@Composable
fun ohMoveImageLinear(
    imagePropertiesX: CharacterDataclass,
    imagePropertiesY: CharacterDataclass,
    @DrawableRes image: Int,
    contentDescription: String?,
): Pair<Float, Float> {
    val animateX = remember {
        Animatable(imagePropertiesX.startValue)
    }
    val animateY = remember {
        Animatable(imagePropertiesY.startValue)
    }

    LaunchedEffect(key1 = animateX) {
        animateX.animateTo(
            imagePropertiesX.endValue,
            animationSpec = FloatTweenSpec(
                duration = imagePropertiesX.speedMillis,
                easing = LinearEasing
            )
        )
    }
    LaunchedEffect(key1 = animateY) {
        animateY.animateTo(
            imagePropertiesY.endValue,
            animationSpec = FloatTweenSpec(
                duration = imagePropertiesY.speedMillis,
                easing = LinearEasing
            )

        )
    }
    Image(
        modifier = Modifier
            .wrapContentSize()
            .offset(animateX.value.dp, animateY.value.dp),
        painter = painterResource(id = image),
        contentDescription = contentDescription,
        contentScale = ContentScale.None
    )
    return Pair(animateX.value, animateY.value)
}
//================================================================================================

/**
 * This function animate the list of DrawableRes image pass to it.
 * To move the image, set 'shouldDrawImages' to false, and put ohMoveImageLinear Function
 * inside the lambda while passing the 'it' as the image
 */
@Composable
fun OhAnimateImages(
    startValue: Float = 1f,
    @DrawableRes images: List<Int>,
    speedMillis: Int = 1000,
    contentDescription: String?,
    shouldDrawImages: Boolean = true,
    function: @Composable ((resImage: Int) -> Pair<Float, Float>?)? = null,
) {
    val movement: Pair<Float, Float>?
    val imageValue = remember {
        Animatable(initialValue = startValue)
    }
    LaunchedEffect(key1 = imageValue) {
        imageValue.animateTo(
            images.size.toFloat(),
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = speedMillis,
                    easing = LinearEasing
                )
            )
        )
    }
    movement = function?.invoke(resImage = images[imageValue.value.toInt()])

    if (shouldDrawImages) {
        Image(
            painter = painterResource(id = images[imageValue.value.toInt()]),
            contentDescription = contentDescription,
            contentScale = ContentScale.Fit,
            modifier = if (movement != null) {
                Modifier
                    .wrapContentSize()
                    .offset(movement.first.dp, movement.second.dp)
            } else {
                Modifier
                    .wrapContentSize()
            }
        )
    }
}

//================================================================================================
/**
 * This move any drown compose object in one sided direction.
 * Draw your composable inside the lambda and pass the 'it' value to the offset
 */
@Composable
fun OhMoveComposeLinear(
    character: CharacterDataclass,
    function: @Composable (x: Float) -> Unit,
) {
    val animate = remember {
        Animatable(character.startValue)
    }
    LaunchedEffect(key1 = animate) {
        animate.animateTo(
            targetValue = character.endValue,
            animationSpec = FloatTweenSpec(
                duration = character.speedMillis,
                easing = LinearEasing,
            )
        )
    }
    function(animate.value)
}

/**
 * This move any drown compose object in one sided direction.
 * Draw your composable inside the lambda and pass the 'it' value to the offset\n
 * This function repeats the process when it gets to the end value
 */
@Composable
fun OhMoveComposeRepeat(
    character: CharacterDataclass,
    function: @Composable (x: Float) -> Unit,
) {
    val animate = remember {
        Animatable(character.startValue)
    }
    LaunchedEffect(key1 = animate) {
        animate.animateTo(
            targetValue = character.endValue,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = character.speedMillis,
                    easing = LinearEasing
                )
            )
        )
    }
    function(animate.value)
}
//================================================================================================

@Composable
fun OhMoveComposableLinear(
    characterX: CharacterDataclass,
    characterY: CharacterDataclass,
    function: @Composable (x: Float, y: Float) -> Unit,
) {
    val animateX = remember {
        Animatable(characterX.startValue)
    }
    val animateY = remember {
        Animatable(characterY.startValue)
    }
    LaunchedEffect(key1 = animateX) {
        animateX.animateTo(
            targetValue = characterX.endValue,
            animationSpec = FloatTweenSpec(duration = characterX.speedMillis, easing = LinearEasing)
        )
    }
    LaunchedEffect(key1 = animateY) {
        animateY.animateTo(
            targetValue = characterY.endValue,
            animationSpec = FloatTweenSpec(duration = characterY.speedMillis, easing = LinearEasing)
        )
    }
    function(animateX.value, animateY.value)
}

@Composable
fun OhMoveComposableRepeat(
    characterX: CharacterDataclass,
    characterY: CharacterDataclass,
    function: @Composable (x: Float, y: Float) -> Unit,
) {
    val animateX = remember {
        Animatable(characterX.startValue)
    }
    val animateY = remember {
        Animatable(characterY.startValue)
    }
    LaunchedEffect(key1 = animateX) {
        animateX.animateTo(
            targetValue = characterX.endValue,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = characterX.speedMillis,
                    easing = LinearEasing
                )
            )
        )
    }
    LaunchedEffect(key1 = animateY) {
        animateY.animateTo(
            targetValue = characterY.endValue,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = characterY.speedMillis,
                    easing = LinearEasing
                )
            )
        )
    }
    function(animateX.value, animateY.value)
}