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
 * Colors defines for action bar.
 * @param titleTextColor the title text color.
 * @param subTextColor the sub text color.
 * @param actionContentColor the action content color, usually for icon tint and text color.
 */
@Immutable
data class ActionBarColors(
    val titleTextColor: Color,
    val subTextColor: Color,
    val actionContentColor: Color
)

/**
 * Style defines for action bar.
 * @param padding the padding of content.
 * @param contentSpacing the spacing between the components of content.
 * @param titleTextStyle the title text style.
 * @param subTextStyle the sub text style.
 * @param actionIconSize the size of action icon.
 * @param actionIconPadding the padding of action icon.
 * @param actionContentMaxWidth the max width of actions content.
 */
@Immutable
data class ActionBarStyle(
    val padding: ComponentPadding,
    val contentSpacing: Dp,
    val titleTextStyle: TextStyle,
    val subTextStyle: TextStyle,
    val actionIconSize: Dp,
    val actionIconPadding: Dp,
    val actionContentMaxWidth: Dp
)

/**
 * Flexi UI large action bar.
 * @see ActionBarDefaults
 * @param modifier the [Modifier] to be applied to this action bar.
 * @param colors the colors of this action bar, default is [ActionBarDefaults.colors].
 * @param style the style of this action bar, default is [ActionBarDefaults.style].
 * @param titleText the title text of this action bar, should typically be [Text].
 * @param subText the sub text of this action bar, should typically be [Text].
 * @param actions the actions displayed at the end of the action bar, should typically be [ActionBarScope.ActionIconButton].
 */
@Composable
fun TopActionBar(
    modifier: Modifier = Modifier,
    colors: ActionBarColors? = null,
    style: ActionBarStyle? = null,
    titleText: @Composable () -> Unit,
    subText: @Composable (() -> Unit)? = null,
    actions: @Composable (ActionBarScope.() -> Unit)? = null
) {
    BasicActionBar(
        type = ActionBarType.LARGE,
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
 * Flexi UI middle action bar.
 * @see TopActionBar
 * @param modifier the [Modifier] to be applied to this action bar.
 * @param colors the colors of this action bar, default is [ActionBarDefaults.colors].
 * @param style the style of this action bar, default is [ActionBarDefaults.style].
 * @param titleText the title text of this action bar, should typically be [Text].
 * @param subText the sub text of this action bar, should typically be [Text].
 * @param finishIcon the finish icon displayed at the start of the action bar, should typically be [ActionBarScope.FinishIconButton].
 * @param navigationIcon the navigation icon displayed at the start of the action bar, should typically be [ActionBarScope.NavigationIconButton].
 * @param actions the actions displayed at the end of the action bar, should typically be [ActionBarScope.ActionIconButton].
 */
@Composable
fun ActionBar(
    modifier: Modifier = Modifier,
    colors: ActionBarColors? = null,
    style: ActionBarStyle? = null,
    titleText: @Composable () -> Unit,
    subText: @Composable (() -> Unit)? = null,
    finishIcon: @Composable (ActionBarScope.() -> Unit)? = null,
    navigationIcon: @Composable (ActionBarScope.() -> Unit)? = null,
    actions: @Composable (ActionBarScope.() -> Unit)? = null
) {
    BasicActionBar(
        type = ActionBarType.MIDDLE,
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
 * Basic action bar for internal use.
 */
@Composable
private fun BasicActionBar(
    type: ActionBarType,
    modifier: Modifier,
    colors: ActionBarColors?,
    style: ActionBarStyle?,
    titleText: @Composable () -> Unit,
    subText: @Composable (() -> Unit)?,
    finishIcon: @Composable (ActionBarScope.() -> Unit)?,
    navigationIcon: @Composable (ActionBarScope.() -> Unit)?,
    actions: @Composable (ActionBarScope.() -> Unit)?
) {
    CompositionLocalProvider(LocalActionBarType provides type) {
        val currentColors = colors ?: ActionBarDefaults.colors
        val currentStyle = style ?: ActionBarDefaults.style
        Box(modifier = modifier.padding(currentStyle.padding)) {
            ActionBarImpl(
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
 * A scope for action bar.
 */
@Stable
interface ActionBarScope {

    /**
     * Action bar's finish icon button.
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
     * Action bar's navigation icon button.
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
     * Action bar's action icon button.
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
 * Action bar's implementation.
 */
@Immutable
private class ActionBarImpl(
    val type: ActionBarType,
    val colors: ActionBarColors,
    val style: ActionBarStyle,
    val titleText: @Composable () -> Unit,
    val subText: @Composable (() -> Unit)?,
    val finishIcon: @Composable (ActionBarScope.() -> Unit)?,
    val navigationIcon: @Composable (ActionBarScope.() -> Unit)?,
    val actions: @Composable (ActionBarScope.() -> Unit)?
) : ActionBarScope {

    /** Build action bar's content. */
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

    /** Build action bar's start content. */
    @Composable
    private fun StartContent() {
        if (type == ActionBarType.MIDDLE && (finishIcon != null || navigationIcon != null))
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

    /** Build action bar's center content. */
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

    /** Build action bar's end content. */
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

    /** Provided the style for action bar's content. */
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
private val ActionBarScope.impl get() = this as? ActionBarImpl? ?: error("Could not got ActionBarScope's impl.")

@Stable
private enum class ActionBarType { LARGE, MIDDLE }

/**
 * Defaults of action bar.
 */
object ActionBarDefaults {
    val colors: ActionBarColors
        @Composable
        @ReadOnlyComposable
        get() = defaultActionBarColors()
    val style: ActionBarStyle
        @Composable
        @ReadOnlyComposable
        get() = defaultActionBarStyle()
}

private val LocalActionBarType = compositionLocalOf { ActionBarType.LARGE }

@Composable
@ReadOnlyComposable
private fun defaultActionBarColors() = ActionBarColors(
    titleTextColor = LocalColors.current.textPrimary,
    subTextColor = LocalColors.current.textSecondary,
    actionContentColor = LocalColors.current.textPrimary
)

@Composable
@ReadOnlyComposable
private fun defaultActionBarStyle() = ActionBarStyle(
    padding = when {
        LocalInSurface.current || LocalInAreaBox.current ->
            ComponentPadding(vertical = LocalSizes.current.spacingPrimary)
        else -> ComponentPadding(LocalSizes.current.spacingPrimary)
    },
    contentSpacing = LocalSizes.current.spacingSecondary,
    titleTextStyle = when (LocalActionBarType.current) {
        ActionBarType.LARGE -> LocalTypography.current.titlePrimary
        ActionBarType.MIDDLE -> LocalTypography.current.titleSecondary
    },
    subTextStyle = LocalTypography.current.subtitle,
    actionIconSize = when (LocalActionBarType.current) {
        ActionBarType.LARGE -> LocalSizes.current.iconSizePrimary
        ActionBarType.MIDDLE -> LocalSizes.current.iconSizeSecondary
    },
    actionIconPadding = LocalSizes.current.spacingTertiary,
    actionContentMaxWidth = DefaultActionContentMaxWidth
)

private val DefaultActionContentMaxWidth = 170.dp

private const val VerticalContentSpacingRatio = 1.6f