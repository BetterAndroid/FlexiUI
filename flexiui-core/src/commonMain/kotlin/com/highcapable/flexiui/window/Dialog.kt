/*
 * Flexi UI - A flexible and useful UI component library.
 * Copyright (C) 2019-2024 HighCapable
 * https://github.com/BetterAndroid/FlexiUI
 *
 * Apache License Version 2.0
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * This file is created by fankes on 2023/11/9.
 */
@file:Suppress("unused")

package com.highcapable.flexiui.window

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.highcapable.betterandroid.compose.extension.ui.ComponentPadding
import com.highcapable.betterandroid.compose.extension.ui.window.Dialog
import com.highcapable.betterandroid.compose.extension.ui.window.DialogPropertiesWrapper
import com.highcapable.flexiui.LocalColors
import com.highcapable.flexiui.LocalSizes
import com.highcapable.flexiui.LocalTypography
import com.highcapable.flexiui.component.AreaBox
import com.highcapable.flexiui.component.AreaBoxStyle
import com.highcapable.flexiui.component.LocalPrimaryButton
import com.highcapable.flexiui.component.LocalTextStyle
import com.highcapable.flexiui.utils.SubcomposeRow

@Immutable
data class FlexiDialogColors(
    val titleTextColor: Color,
    val contentTextColor: Color
)

@Immutable
data class FlexiDialogStyle(
    val boxStyle: AreaBoxStyle,
    val titleTextStyle: TextStyle,
    val contentTextStyle: TextStyle,
    val maxWidth: Dp,
    val outPadding: ComponentPadding,
    val titlePadding: ComponentPadding,
    val contentPadding: ComponentPadding,
    val buttonsSpacing: Dp
)

@Composable
fun FlexiDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    animated: Boolean = true,
    colors: FlexiDialogColors = FlexiDialog.colors,
    style: FlexiDialogStyle = FlexiDialog.style,
    contentAlignment: Alignment = Alignment.TopStart,
    properties: DialogPropertiesWrapper = DefaultDialogProperties,
    title: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
    confirmButton: @Composable (() -> Unit)? = null,
    cancelButton: @Composable (() -> Unit)? = null,
    neutralButton: @Composable (() -> Unit)? = null
) {
    @Composable
    fun Content() {
        title?.also { content ->
            Box(modifier = Modifier.padding(style.titlePadding)) {
                CompositionLocalProvider(
                    LocalTextStyle provides style.titleTextStyle.copy(color = colors.titleTextColor),
                    content = content
                )
            }
        }
        Box(modifier = Modifier.padding(style.contentPadding)) {
            CompositionLocalProvider(
                LocalTextStyle provides style.contentTextStyle.copy(color = colors.contentTextColor),
                content = content
            )
        }
    }

    @Composable
    fun Buttons() {
        Column(
            modifier = Modifier.padding(top = style.buttonsSpacing),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            neutralButton?.also { content ->
                SubcomposeRow(
                    modifier = Modifier.fillMaxWidth().padding(bottom = style.buttonsSpacing),
                    content = content
                )
            }
            SubcomposeRow(
                modifier = Modifier.fillMaxWidth(),
                count = if (cancelButton != null) 2 else 1,
                spacingBetween = style.buttonsSpacing
            ) {
                cancelButton?.invoke()
                confirmButton?.also { content ->
                    CompositionLocalProvider(
                        LocalPrimaryButton provides true,
                        content = content
                    )
                }
            }
        }
    }
    BasicFlexiDialog(
        visible = visible,
        onDismissRequest = onDismissRequest,
        modifier = Modifier.padding(style.outPadding).then(modifier),
        animated = animated,
        boxStyle = style.boxStyle,
        maxWidth = style.maxWidth,
        contentAlignment = contentAlignment,
        properties = properties
    ) {
        Column {
            val hasButtons = confirmButton != null || cancelButton != null || neutralButton != null
            Content()
            if (hasButtons) Buttons()
        }
    }
}

@Composable
fun BasicFlexiDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    animated: Boolean = true,
    boxStyle: AreaBoxStyle = AreaBox.style,
    maxWidth: Dp = DefaultMaxWidth,
    contentAlignment: Alignment = Alignment.TopStart,
    properties: DialogPropertiesWrapper = DefaultDialogProperties,
    content: @Composable () -> Unit
) {
    val animatedAlpha by animateFloatAsState(if (visible) 1f else 0f, tween(AnimationDuration))
    val visibleScale = remember { Animatable(AnimationMinmumScale) }
    if (visible) LaunchedEffect(Unit) {
        visibleScale.animateTo(targetValue = 1.1f)
        visibleScale.animateTo(targetValue = 1f)
    }
    val animatedScale by animateFloatAsState(
        targetValue = if (visible) visibleScale.value else AnimationMinmumScale,
        animationSpec = if (visible)
            tween(easing = LinearOutSlowInEasing, durationMillis = AnimationDuration)
        else tween(easing = FastOutSlowInEasing, durationMillis = AnimationDuration)
    )
    val animatedScrimColor by animateColorAsState(if (visible) properties.scrimColor else Color.Transparent)
    val animation = animated && animatedAlpha > 0f
    if (visible || animation) Dialog(
        properties = properties.copy(scrimColor = animatedScrimColor),
        onDismissRequest = onDismissRequest
    ) {
        Box(
            modifier = if (animated)
                Modifier.graphicsLayer {
                    scaleX = animatedScale
                    scaleY = animatedScale
                    alpha = animatedAlpha
                }.then(modifier)
            else Modifier.then(modifier)
        ) {
            AreaBox(
                modifier = Modifier.widthIn(max = maxWidth),
                style = boxStyle,
                contentAlignment = contentAlignment
            ) { content() }
        }
    }
}

object FlexiDialog {
    val colors: FlexiDialogColors
        @Composable
        @ReadOnlyComposable
        get() = defaultFlexiDialogColors()
    val style: FlexiDialogStyle
        @Composable
        @ReadOnlyComposable
        get() = defaultFlexiDialogStyle()
}

@Composable
@ReadOnlyComposable
private fun defaultFlexiDialogColors() = FlexiDialogColors(
    titleTextColor = LocalColors.current.textPrimary,
    contentTextColor = LocalColors.current.textSecondary
)

@Composable
@ReadOnlyComposable
private fun defaultFlexiDialogStyle() = FlexiDialogStyle(
    boxStyle = AreaBox.style.copy(padding = ComponentPadding(LocalSizes.current.spacingSecondary)),
    titleTextStyle = LocalTypography.current.titleSecondary,
    contentTextStyle = LocalTypography.current.primary,
    maxWidth = DefaultMaxWidth,
    outPadding = ComponentPadding(horizontal = DefaultHorizontalOutPadding),
    titlePadding = ComponentPadding(LocalSizes.current.spacingSecondary),
    contentPadding = ComponentPadding(LocalSizes.current.spacingSecondary),
    buttonsSpacing = LocalSizes.current.spacingSecondary
)

private const val AnimationDuration = 250
private const val AnimationMinmumScale = 0.8f

private val DefaultMaxWidth = 300.dp
private val DefaultHorizontalOutPadding = 50.dp

private const val DefaultScrimOpacity = 0.35f
private val DefaultScrimColor = Color.Black.copy(alpha = DefaultScrimOpacity)
private val DefaultDialogProperties = DialogPropertiesWrapper(usePlatformDefaultWidth = false, scrimColor = DefaultScrimColor)