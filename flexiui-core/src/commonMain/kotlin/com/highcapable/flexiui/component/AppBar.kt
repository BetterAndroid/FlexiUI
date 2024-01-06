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
 * This file is created by fankes on 2023/11/5.
 */
@file:Suppress("unused")

package com.highcapable.flexiui.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.highcapable.betterandroid.compose.extension.ui.ComponentPadding
import com.highcapable.flexiui.LocalColors
import com.highcapable.flexiui.LocalSizes
import com.highcapable.flexiui.LocalTypography
import com.highcapable.flexiui.resources.Icons
import com.highcapable.flexiui.resources.icon.ArrowNaviUp
import com.highcapable.flexiui.resources.icon.FinishClose

/**
 * Colors defines for app bar.
 * @param titleTextColor the title text color.
 * @param subTextColor the sub text color.
 * @param actionContentColor the action content color, usually for icon tint and text color.
 */
@Immutable
data class AppBarColors(
    val titleTextColor: Color,
    val subTextColor: Color,
    val actionContentColor: Color
)

/**
 * Style defines for app bar.
 * @param padding the padding of content.
 * @param contentSpacing the spacing between the components of content.
 * @param titleTextStyle the title text style.
 * @param subTextStyle the sub text style.
 * @param actionIconSize the size of action icon.
 * @param actionIconPadding the padding of action icon.
 * @param actionContentMaxWidth the max width of actions content.
 */
@Immutable
data class AppBarStyle(
    val padding: ComponentPadding,
    val contentSpacing: Dp,
    val titleTextStyle: TextStyle,
    val subTextStyle: TextStyle,
    val actionIconSize: Dp,
    val actionIconPadding: Dp,
    val actionContentMaxWidth: Dp
)

/**
 * Flexi UI primary app bar.
 * @see SecondaryAppBar
 * @param modifier the [Modifier] to be applied to this app bar.
 * @param colors the colors of this app bar, default is [AppBarDefaults.colors].
 * @param style the style of this app bar, default is [AppBarDefaults.style].
 * @param titleText the title text of this app bar, should typically be [Text].
 * @param subText the sub text of this app bar, should typically be [Text].
 * @param actions the actions displayed at the end of the app bar, should typically be [AppBarScope.ActionIconButton].
 */
@Composable
fun PrimaryAppBar(
    modifier: Modifier = Modifier,
    colors: AppBarColors? = null,
    style: AppBarStyle? = null,
    titleText: @Composable () -> Unit,
    subText: @Composable (() -> Unit)? = null,
    actions: @Composable (AppBarScope.() -> Unit)? = null
) {
    BasicAppBar(
        type = AppBarType.Primary,
        modifier = modifier,
        colors = colors,
        style = style,
        titleText = titleText,
        subText = subText,
        finishIcon = null,
        navigationIcon = null,
        actions = actions
    )
}

/**
 * Flexi UI secondary app bar.
 * @see PrimaryAppBar
 * @param modifier the [Modifier] to be applied to this app bar.
 * @param colors the colors of this app bar, default is [AppBarDefaults.colors].
 * @param style the style of this app bar, default is [AppBarDefaults.style].
 * @param titleText the title text of this app bar, should typically be [Text].
 * @param subText the sub text of this app bar, should typically be [Text].
 * @param finishIcon the finish icon displayed at the start of the app bar, should typically be [AppBarScope.FinishIconButton].
 * @param navigationIcon the navigation icon displayed at the start of the app bar, should typically be [AppBarScope.NavigationIconButton].
 * @param actions the actions displayed at the end of the app bar, should typically be [AppBarScope.ActionIconButton].
 */
@Composable
fun SecondaryAppBar(
    modifier: Modifier = Modifier,
    colors: AppBarColors? = null,
    style: AppBarStyle? = null,
    titleText: @Composable () -> Unit,
    subText: @Composable (() -> Unit)? = null,
    finishIcon: @Composable (AppBarScope.() -> Unit)? = null,
    navigationIcon: @Composable (AppBarScope.() -> Unit)? = null,
    actions: @Composable (AppBarScope.() -> Unit)? = null
) {
    BasicAppBar(
        type = AppBarType.Secondary,
        modifier = modifier,
        colors = colors,
        style = style,
        titleText = titleText,
        subText = subText,
        finishIcon = finishIcon,
        navigationIcon = navigationIcon,
        actions = actions
    )
}

/**
 * Basic app bar for internal use.
 */
@Composable
private fun BasicAppBar(
    type: AppBarType,
    modifier: Modifier,
    colors: AppBarColors?,
    style: AppBarStyle?,
    titleText: @Composable () -> Unit,
    subText: @Composable (() -> Unit)?,
    finishIcon: @Composable (AppBarScope.() -> Unit)?,
    navigationIcon: @Composable (AppBarScope.() -> Unit)?,
    actions: @Composable (AppBarScope.() -> Unit)?
) {
    CompositionLocalProvider(LocalAppBarType provides type) {
        val currentColors = colors ?: AppBarDefaults.colors
        val currentStyle = style ?: AppBarDefaults.style
        Box(modifier = modifier.padding(currentStyle.padding)) {
            AppBarImpl(
                type = type,
                colors = currentColors,
                style = currentStyle,
                titleText = titleText,
                subText = subText,
                finishIcon = finishIcon,
                navigationIcon = navigationIcon,
                actions = actions
            ).Content()
        }
    }
}

/**
 * A scope for app bar.
 */
@Stable
interface AppBarScope {

    /**
     * app bar's finish icon button.
     * @param onClick the callback when the icon button is clicked.
     * @param modifier the [Modifier] to be applied to this icon button.
     * @param colors the colors of this icon button, default is [IconButtonDefaults.colors].
     * @param style the style of this icon button, default is [IconButtonDefaults.style].
     * @param enabled whether this icon button is enabled, default is true.
     * @param interactionSource the interaction source of this icon button.
     */
    @Composable
    fun FinishIconButton(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        colors: ButtonColors = IconButtonDefaults.colors,
        style: ButtonStyle = IconButtonDefaults.style,
        enabled: Boolean = true,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    ) {
        ActionIconButton(
            onClick = onClick,
            modifier = modifier,
            colors = colors,
            style = style,
            enabled = enabled,
            interactionSource = interactionSource
        ) { Icon(imageVector = Icons.FinishClose) }
    }

    /**
     * app bar's navigation icon button.
     * @param onClick the callback when the icon button is clicked.
     * @param modifier the [Modifier] to be applied to this icon button.
     * @param colors the colors of this icon button, default is [IconButtonDefaults.colors].
     * @param style the style of this icon button, default is [IconButtonDefaults.style].
     * @param enabled whether this icon button is enabled, default is true.
     * @param interactionSource the interaction source of this icon button.
     */
    @Composable
    fun NavigationIconButton(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        colors: ButtonColors = IconButtonDefaults.colors,
        style: ButtonStyle = IconButtonDefaults.style,
        enabled: Boolean = true,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    ) {
        ActionIconButton(
            onClick = onClick,
            modifier = modifier,
            colors = colors,
            style = style,
            enabled = enabled,
            interactionSource = interactionSource
        ) { Icon(imageVector = Icons.ArrowNaviUp) }
    }

    /**
     * app bar's action icon button.
     * @param onClick the callback when the icon button is clicked.
     * @param modifier the [Modifier] to be applied to this icon button.
     * @param colors the colors of this icon button, default is [IconButtonDefaults.colors].
     * @param style the style of this icon button, default is [IconButtonDefaults.style].
     * @param enabled whether this icon button is enabled, default is true.
     * @param interactionSource the interaction source of this icon button.
     * @param content the content of the [ActionIconButton], should typically be [Icon].
     */
    @Composable
    fun ActionIconButton(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        colors: ButtonColors = IconButtonDefaults.colors,
        style: ButtonStyle = IconButtonDefaults.style,
        enabled: Boolean = true,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        content: @Composable () -> Unit
    ) {
        val iconInflateSize = impl.style.actionIconSize + impl.style.actionIconPadding
        IconButton(
            onClick = onClick,
            modifier = Modifier.size(iconInflateSize).then(modifier),
            colors = colors,
            style = style,
            enabled = enabled,
            interactionSource = interactionSource,
            content = content
        )
    }
}

/**
 * App bar's implementation.
 */
@Immutable
private class AppBarImpl(
    val type: AppBarType,
    val colors: AppBarColors,
    val style: AppBarStyle,
    val titleText: @Composable () -> Unit,
    val subText: @Composable (() -> Unit)?,
    val finishIcon: @Composable (AppBarScope.() -> Unit)?,
    val navigationIcon: @Composable (AppBarScope.() -> Unit)?,
    val actions: @Composable (AppBarScope.() -> Unit)?
) : AppBarScope {

    /** Build app bar's content. */
    @Composable
    fun Content() {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val contentMaxWidth = maxWidth
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val actionContentMaxWidth = if (actions != null) style.actionContentMaxWidth else 0.dp
                Row(
                    modifier = Modifier.padding(end = style.contentSpacing)
                        .widthIn(max = contentMaxWidth - actionContentMaxWidth),
                    horizontalArrangement = Arrangement.spacedBy(style.contentSpacing),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StartContent()
                    CenterContent()
                }
                EndContent()
            }
        }
    }

    /** Build app bar's start content. */
    @Composable
    private fun StartContent() {
        if (type == AppBarType.Secondary && (finishIcon != null || navigationIcon != null))
            Row(
                horizontalArrangement = Arrangement.spacedBy(style.contentSpacing),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ContentStyle(colors.actionContentColor) {
                    finishIcon?.also { it() }
                    navigationIcon?.also { it() }
                }
            }
    }

    /** Build app bar's center content. */
    @Composable
    private fun CenterContent() {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(style.contentSpacing / VerticalContentSpacingRatio)
        ) {
            ContentStyle(
                color = colors.titleTextColor,
                textStyle = style.titleTextStyle,
                content = titleText
            )
            subText?.also { content ->
                ContentStyle(
                    color = colors.subTextColor,
                    textStyle = style.subTextStyle,
                    content = content
                )
            }
        }
    }

    /** Build app bar's end content. */
    @Composable
    private fun EndContent() {
        actions?.also { content ->
            val scrollState = rememberScrollState()
            Row(
                modifier = Modifier.horizontalScroll(scrollState),
                horizontalArrangement = Arrangement.spacedBy(style.contentSpacing),
                verticalAlignment = Alignment.CenterVertically
            ) { ContentStyle(colors.actionContentColor) { content() } }
        }
    }

    /** Provided the style for app bar's content. */
    @Composable
    private fun ContentStyle(
        color: Color,
        textStyle: TextStyle? = null,
        content: @Composable () -> Unit
    ) {
        CompositionLocalProvider(
            LocalIconStyle provides LocalIconStyle.current.copy(size = style.actionIconSize, tint = color),
            LocalTextStyle provides LocalTextStyle.current.merge(textStyle ?: LocalTextStyle.current).copy(color = color),
            content = content
        )
    }
}

@Stable
private val AppBarScope.impl get() = this as? AppBarImpl? ?: error("Could not got AppBarScope's impl.")

@Stable
private enum class AppBarType { Primary, Secondary }

/**
 * Defaults of app bar.
 */
object AppBarDefaults {
    val colors: AppBarColors
        @Composable
        @ReadOnlyComposable
        get() = defaultAppBarColors()
    val style: AppBarStyle
        @Composable
        @ReadOnlyComposable
        get() = defaultAppBarStyle()
}

private val LocalAppBarType = compositionLocalOf { AppBarType.Primary }

@Composable
@ReadOnlyComposable
private fun defaultAppBarColors() = AppBarColors(
    titleTextColor = LocalColors.current.textPrimary,
    subTextColor = LocalColors.current.textSecondary,
    actionContentColor = LocalColors.current.textPrimary
)

@Composable
@ReadOnlyComposable
private fun defaultAppBarStyle() = AppBarStyle(
    padding = when {
        LocalInSurface.current || LocalInAreaBox.current ->
            ComponentPadding(vertical = LocalSizes.current.spacingPrimary)
        else -> ComponentPadding(LocalSizes.current.spacingPrimary)
    },
    contentSpacing = LocalSizes.current.spacingSecondary,
    titleTextStyle = when (LocalAppBarType.current) {
        AppBarType.Primary -> LocalTypography.current.titlePrimary
        AppBarType.Secondary -> LocalTypography.current.titleSecondary
    },
    subTextStyle = LocalTypography.current.subtitle,
    actionIconSize = when (LocalAppBarType.current) {
        AppBarType.Primary -> LocalSizes.current.iconSizePrimary
        AppBarType.Secondary -> LocalSizes.current.iconSizeSecondary
    },
    actionIconPadding = LocalSizes.current.spacingTertiary,
    actionContentMaxWidth = DefaultActionContentMaxWidth
)

private val DefaultActionContentMaxWidth = 170.dp

private const val VerticalContentSpacingRatio = 1.6f