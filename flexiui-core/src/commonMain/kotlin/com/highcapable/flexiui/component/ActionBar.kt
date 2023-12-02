/*
 * Flexi UI - A flexible and useful UI component library.
 * Copyright (C) 2019-2023 HighCapable
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
import androidx.compose.foundation.layout.PaddingValues
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
import com.highcapable.flexiui.LocalColors
import com.highcapable.flexiui.LocalSizes
import com.highcapable.flexiui.LocalTypography
import com.highcapable.flexiui.resources.Icons
import com.highcapable.flexiui.resources.icon.ArrowNaviUp
import com.highcapable.flexiui.resources.icon.FinishClose

@Immutable
data class ActionBarColors(
    val titleTextColor: Color,
    val subTextColor: Color,
    val actionContentColor: Color
)

@Immutable
data class ActionBarStyle(
    val padding: PaddingValues,
    val contentSpacing: Dp,
    val titleTextStyle: TextStyle,
    val subTextStyle: TextStyle,
    val actionIconSize: Dp,
    val actionIconPadding: Dp,
    val actionContentMaxWidth: Dp
)

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
        val currentColors = colors ?: ActionBar.colors
        val currentStyle = style ?: ActionBar.style
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

@Stable
interface ActionBarScope {

    @Composable
    fun FinishIconButton(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        colors: ButtonColors = IconButton.colors,
        style: ButtonStyle = IconButton.style,
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

    @Composable
    fun NavigationIconButton(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        colors: ButtonColors = IconButton.colors,
        style: ButtonStyle = IconButton.style,
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

    @Composable
    fun ActionIconButton(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        colors: ButtonColors = IconButton.colors,
        style: ButtonStyle = IconButton.style,
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

    @Composable
    private fun CenterContent() {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(style.contentSpacing / 2)
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

object ActionBar {
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
            PaddingValues(vertical = LocalSizes.current.spacingPrimary)
        else -> PaddingValues(LocalSizes.current.spacingPrimary)
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