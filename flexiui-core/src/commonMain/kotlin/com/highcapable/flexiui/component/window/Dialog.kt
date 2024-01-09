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

package com.highcapable.flexiui.component.window

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.highcapable.betterandroid.compose.extension.ui.ComponentPadding
import com.highcapable.betterandroid.compose.extension.ui.layout.AdaptiveRow
import com.highcapable.betterandroid.compose.extension.ui.window.Dialog
import com.highcapable.betterandroid.compose.extension.ui.window.DialogPropertiesWrapper
import com.highcapable.flexiui.ColorsDescriptor
import com.highcapable.flexiui.PaddingDescriptor
import com.highcapable.flexiui.SizesDescriptor
import com.highcapable.flexiui.TypographyDescriptor
import com.highcapable.flexiui.component.AreaBox
import com.highcapable.flexiui.component.AreaBoxColors
import com.highcapable.flexiui.component.AreaBoxDefaults
import com.highcapable.flexiui.component.AreaBoxProperties
import com.highcapable.flexiui.component.AreaBoxStyle
import com.highcapable.flexiui.component.Button
import com.highcapable.flexiui.component.Icon
import com.highcapable.flexiui.component.LocalIconStyle
import com.highcapable.flexiui.component.LocalPrimaryButton
import com.highcapable.flexiui.component.LocalTextStyle
import com.highcapable.flexiui.component.Text
import com.highcapable.flexiui.platform.ActualPlatform
import com.highcapable.flexiui.platform.Platform
import com.highcapable.flexiui.toColor
import com.highcapable.flexiui.toDp
import com.highcapable.flexiui.toShape
import com.highcapable.flexiui.toTextStyle

/**
 * Colors defines for flexi dialog.
 * @see FlexiDialogDefaults.colors
 */
@Immutable
data class FlexiDialogColors(
    val backgroundColor: Color,
    val borderColor: Color,
    val titleTextColor: Color,
    val titleIconTint: Color,
    val contentTextColor: Color
)

/**
 * Style defines for flexi dialog.
 * @see FlexiDialogDefaults.style
 */
@Immutable
data class FlexiDialogStyle(
    val titleTextStyle: TextStyle,
    val contentTextStyle: TextStyle,
    val maxWidth: Dp,
    val maxHeight: Dp,
    val padding: ComponentPadding,
    val insetsPadding: ComponentPadding,
    val titlePadding: ComponentPadding,
    val contentPadding: ComponentPadding,
    val shape: Shape,
    val borderWidth: Dp,
    val shadowSize: Dp,
    val buttonsSpacing: Dp
)

/**
 * Flexi UI dialog.
 * @param visible the visible state of dialog.
 * @param onDismissRequest the callback when dismiss dialog.
 * @param modifier the [Modifier] to be applied to this dialog.
 * @param animated whether to animate the dialog, default is true.
 * @param colors the colors of dialog, default is [FlexiDialogDefaults.colors].
 * @param style the style of dialog, default is [FlexiDialogDefaults.style].
 * @param contentAlignment the alignment of dialog content, default is [Alignment.TopStart].
 * @param properties the properties of dialog, default is [DefaultDialogProperties].
 * @param title the title of the [FlexiDialog], should typically be [Icon] or [Text].
 * @param content the content of the [FlexiDialog].
 * @param confirmButton the confirm button of the [FlexiDialog], should typically be [Button].
 * @param cancelButton the cancel button of the [FlexiDialog], should typically be [Button].
 * @param neutralButton the neutral button of the [FlexiDialog], should typically be [Button].
 */
@Composable
fun FlexiDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    animated: Boolean = true,
    colors: FlexiDialogColors = FlexiDialogDefaults.colors(),
    style: FlexiDialogStyle = FlexiDialogDefaults.style(),
    contentAlignment: Alignment = Alignment.TopStart,
    properties: DialogPropertiesWrapper = DefaultDialogProperties,
    title: (@Composable RowScope.() -> Unit)? = null,
    content: @Composable () -> Unit,
    confirmButton: @Composable (() -> Unit)? = null,
    cancelButton: @Composable (() -> Unit)? = null,
    neutralButton: @Composable (() -> Unit)? = null
) {
    FlexiDialog(
        visible = visible,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        animated = animated,
        scrimAnimated = false,
        colors = colors,
        style = style,
        contentAlignment = contentAlignment,
        properties = properties,
        title = title,
        content = content,
        confirmButton = confirmButton,
        cancelButton = cancelButton,
        neutralButton = neutralButton
    )
}

/**
 * Flexi UI dialog.
 * @see FlexiDialog
 * @param visible the visible state of dialog.
 * @param onDismissRequest the callback when dismiss dialog.
 * @param modifier the [Modifier] to be applied to this dialog.
 * @param animated whether to animate the dialog, default is true.
 * @param scrimAnimated whether to animate the scrim, when false,
 * this effect will only be available on Android platform, default is false.
 * @param colors the colors of dialog, default is [FlexiDialogDefaults.colors].
 * @param style the style of dialog, default is [FlexiDialogDefaults.style].
 * @param contentAlignment the alignment of dialog content, default is [Alignment.TopStart].
 * @param properties the properties of dialog, default is [DefaultDialogProperties].
 * @param title the title of the [FlexiDialog], should typically be [Icon] or [Text].
 * @param content the content of the [FlexiDialog].
 * @param confirmButton the confirm button of the [FlexiDialog], should typically be [Button].
 * @param cancelButton the cancel button of the [FlexiDialog], should typically be [Button].
 * @param neutralButton the neutral button of the [FlexiDialog], should typically be [Button].
 */
@ExperimentalFlexiDialogScrimAnimated
@Composable
fun FlexiDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    animated: Boolean = true,
    scrimAnimated: Boolean = false,
    colors: FlexiDialogColors = FlexiDialogDefaults.colors(),
    style: FlexiDialogStyle = FlexiDialogDefaults.style(),
    contentAlignment: Alignment = Alignment.TopStart,
    properties: DialogPropertiesWrapper = DefaultDialogProperties,
    title: (@Composable RowScope.() -> Unit)? = null,
    content: @Composable () -> Unit,
    confirmButton: @Composable (() -> Unit)? = null,
    cancelButton: @Composable (() -> Unit)? = null,
    neutralButton: @Composable (() -> Unit)? = null
) {
    /** Build the content of dialog. */
    @Composable
    fun Content() {
        title?.also { titleContent ->
            CompositionLocalProvider(
                LocalIconStyle provides LocalIconStyle.current.copy(tint = colors.titleIconTint),
                LocalTextStyle provides style.titleTextStyle.copy(color = colors.titleTextColor)
            ) {
                Row(
                    modifier = Modifier.padding(style.titlePadding),
                    verticalAlignment = Alignment.CenterVertically,
                    content = titleContent
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

    /** Build the buttons of dialog. */
    @Composable
    fun Buttons() {
        Column(
            modifier = Modifier.padding(top = style.buttonsSpacing),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            neutralButton?.also { button ->
                AdaptiveRow(
                    modifier = Modifier.fillMaxWidth().padding(bottom = style.buttonsSpacing),
                    content = button
                )
            }
            AdaptiveRow(
                modifier = Modifier.fillMaxWidth(),
                spacingBetween = style.buttonsSpacing
            ) {
                cancelButton?.invoke()
                confirmButton?.also { button ->
                    CompositionLocalProvider(
                        LocalPrimaryButton provides true,
                        content = button
                    )
                }
            }
        }
    }
    BasicFlexiDialog(
        visible = visible,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        animated = animated,
        scrimAnimated = scrimAnimated,
        boxColors = AreaBoxDefaults.colors(
            backgroundColor = colors.backgroundColor,
            borderColor = colors.borderColor
        ),
        boxStyle = AreaBoxDefaults.style(
            padding = style.padding,
            shape = style.shape,
            borderWidth = style.borderWidth,
            shadowSize = style.shadowSize
        ),
        insetsPadding = style.insetsPadding,
        maxWidth = style.maxWidth,
        maxHeight = style.maxHeight,
        contentAlignment = contentAlignment,
        properties = properties
    ) {
        Column {
            val hasButtons = confirmButton != null || cancelButton != null || neutralButton != null
            // The content should not overlap with the buttons, so it has a weight of 1f.
            Column(modifier = Modifier.weight(1f, fill = false)) { Content() }
            if (hasButtons) Buttons()
        }
    }
}

/**
 * Basic flexi dialog for internal use.
 */
@Composable
private fun BasicFlexiDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier,
    animated: Boolean,
    scrimAnimated: Boolean,
    boxColors: AreaBoxColors,
    boxStyle: AreaBoxStyle,
    insetsPadding: ComponentPadding,
    maxWidth: Dp,
    maxHeight: Dp,
    contentAlignment: Alignment,
    properties: DialogPropertiesWrapper,
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
    // Resolve @ExperimentalFlexiDialogScrimAnimated.
    val propertiesCopy = if (scrimAnimated || ActualPlatform == Platform.Android)
        properties.copy(scrimColor = animatedScrimColor)
    else properties
    if (visible || animation) Dialog(
        properties = propertiesCopy,
        onDismissRequest = onDismissRequest
    ) {
        val sModifier = if (animated)
            Modifier.graphicsLayer {
                scaleX = animatedScale
                scaleY = animatedScale
                alpha = animatedAlpha
            }.then(modifier)
        else modifier
        Box(modifier = Modifier.padding(insetsPadding).then(sModifier)) {
            AreaBox(
                modifier = Modifier.widthIn(max = maxWidth).heightIn(max = maxHeight),
                colors = boxColors,
                style = boxStyle,
                contentAlignment = contentAlignment
            ) { content() }
        }
    }
}

/**
 * Defaults of flexi dialog.
 */
object FlexiDialogDefaults {

    /**
     * Creates a [FlexiDialogColors] with the default values.
     * @param backgroundColor the background color.
     * @param borderColor the border color.
     * @param titleTextColor the title text color.
     * @param titleIconTint the title icon tint.
     * @param contentTextColor the content text color.
     * @return [FlexiDialogColors]
     */
    @Composable
    fun colors(
        backgroundColor: Color = FlexiDialogProperties.BackgroundColor.toColor(),
        borderColor: Color = FlexiDialogProperties.BorderColor.toColor(),
        titleTextColor: Color = FlexiDialogProperties.TitleTextColor.toColor(),
        titleIconTint: Color = FlexiDialogProperties.TitleIconTint.toColor(),
        contentTextColor: Color = FlexiDialogProperties.ContentTextColor.toColor()
    ) = FlexiDialogColors(
        backgroundColor = backgroundColor,
        borderColor = borderColor,
        titleTextColor = titleTextColor,
        titleIconTint = titleIconTint,
        contentTextColor = contentTextColor
    )

    /**
     * Creates a [FlexiDialogStyle] with the default values.
     * @param titleTextStyle the title text style.
     * @param contentTextStyle the content text style.
     * @param maxWidth the dialog's max width.
     * @param maxHeight the dialog's max height.
     * @param padding the dialog's padding.
     * @param insetsPadding the dialog's insets padding.
     * @param titlePadding the title padding.
     * @param contentPadding the content padding.
     * @param shape the dialog's shape.
     * @param borderWidth the dialog's border width.
     * @param shadowSize the dialog's shadow size.
     * @param buttonsSpacing the spacing between buttons.
     * @return [FlexiDialogStyle]
     */
    @Composable
    fun style(
        titleTextStyle: TextStyle = FlexiDialogProperties.TitleTextStyle.toTextStyle(),
        contentTextStyle: TextStyle = FlexiDialogProperties.ContentTextStyle.toTextStyle(),
        maxWidth: Dp = FlexiDialogProperties.MaxWidth,
        maxHeight: Dp = FlexiDialogProperties.MaxHeight,
        padding: ComponentPadding = FlexiDialogProperties.Padding.toPadding(),
        insetsPadding: ComponentPadding = FlexiDialogProperties.InsetsPadding,
        titlePadding: ComponentPadding = FlexiDialogProperties.TitlePadding.toPadding(),
        contentPadding: ComponentPadding = FlexiDialogProperties.ContentPadding.toPadding(),
        shape: Shape = FlexiDialogProperties.Shape.toShape(),
        borderWidth: Dp = FlexiDialogProperties.BorderWidth.toDp(),
        shadowSize: Dp = FlexiDialogProperties.ShadowSize,
        buttonsSpacing: Dp = FlexiDialogProperties.ButtonsSpacing.toDp()
    ) = FlexiDialogStyle(
        titleTextStyle = titleTextStyle,
        contentTextStyle = contentTextStyle,
        maxWidth = maxWidth,
        maxHeight = maxHeight,
        padding = padding,
        insetsPadding = insetsPadding,
        titlePadding = titlePadding,
        contentPadding = contentPadding,
        shape = shape,
        borderWidth = borderWidth,
        shadowSize = shadowSize,
        buttonsSpacing = buttonsSpacing
    )
}

@Stable
internal object FlexiDialogProperties {
    val BackgroundColor = AreaBoxProperties.BackgroundColor
    val BorderColor = AreaBoxProperties.BorderColor
    val TitleTextColor = ColorsDescriptor.TextPrimary
    val TitleIconTint = ColorsDescriptor.TextPrimary
    val ContentTextColor = ColorsDescriptor.TextSecondary
    val TitleTextStyle = TypographyDescriptor.TitleSecondary
    val ContentTextStyle = TypographyDescriptor.Primary
    val MaxWidth = 300.dp
    val MaxHeight = 500.dp
    val Padding = PaddingDescriptor(SizesDescriptor.SpacingSecondary)
    val InsetsPadding = ComponentPadding(horizontal = 50.dp)
    val TitlePadding = PaddingDescriptor(SizesDescriptor.SpacingSecondary)
    val ContentPadding = PaddingDescriptor(SizesDescriptor.SpacingSecondary)
    val Shape = AreaBoxProperties.Shape
    val BorderWidth = AreaBoxProperties.BorderWidth
    val ShadowSize = AreaBoxProperties.ShadowSize
    val ButtonsSpacing = SizesDescriptor.SpacingSecondary
}

private const val AnimationDuration = 250
private const val AnimationMinmumScale = 0.8f

private const val DefaultScrimOpacity = 0.35f
private val DefaultScrimColor = Color.Black.copy(alpha = DefaultScrimOpacity)
private val DefaultDialogProperties = DialogPropertiesWrapper(usePlatformDefaultWidth = false, scrimColor = DefaultScrimColor)

/**
 * The scrim animation effect of Flexi UI dialog is still in experimental function,
 * which may cause the scrim effect to be lost on non-Android platforms.
 *
 * Reproduced on compose-multiplatform 1.5.x, and has been fixed on latest 1.6.0-dev.
 */
@RequiresOptIn(
    message = "The scrim animation effect of Flexi UI dialog is still in experimental function, " +
        "which may cause the scrim effect to be lost on non-Android platforms.\n" +
        "Reproduced on compose-multiplatform 1.5.x, and has been fixed on latest 1.6.0-dev.",
    level = RequiresOptIn.Level.WARNING
)
@MustBeDocumented
annotation class ExperimentalFlexiDialogScrimAnimated