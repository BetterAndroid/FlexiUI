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
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.highcapable.betterandroid.compose.extension.ui.ComponentPadding
import com.highcapable.flexiui.ColorsDescriptor
import com.highcapable.flexiui.PaddingDescriptor
import com.highcapable.flexiui.SizesDescriptor
import com.highcapable.flexiui.TypographyDescriptor
import com.highcapable.flexiui.resources.FlexiIcons
import com.highcapable.flexiui.resources.icon.ArrowNaviUp
import com.highcapable.flexiui.resources.icon.FinishClose
import com.highcapable.flexiui.toColor
import com.highcapable.flexiui.toDp
import com.highcapable.flexiui.toTextStyle

/**
 * Colors defines for app bar.
 * @see AppBarDefaults.colors
 */
@Immutable
data class AppBarColors(
    val titleTextColor: Color,
    val subtitleTextColor: Color,
    val actionContentColor: Color
)

/**
 * Style defines for app bar.
 * @see AppBarDefaults.style
 */
@Immutable
data class AppBarStyle(
    val padding: ComponentPadding,
    val contentSpacing: Dp,
    val titleTextStyle: TextStyle,
    val subtitleTextStyle: TextStyle,
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
 * @param title the title of this app bar, should typically be [Text].
 * @param subtitle the subtitle of this app bar, should typically be [Text].
 * @param actions the actions displayed at the end of the app bar, should typically be [AppBarScope.ActionIconButton].
 */
@Composable
fun PrimaryAppBar(
    modifier: Modifier = Modifier,
    colors: AppBarColors = AppBarDefaults.colors(),
    style: AppBarStyle = AppBarDefaults.style(type = AppBarType.Primary),
    title: @Composable () -> Unit,
    subtitle: @Composable (() -> Unit)? = null,
    actions: @Composable (AppBarScope.() -> Unit)? = null
) {
    BasicAppBar(
        type = AppBarType.Primary,
        modifier = modifier,
        colors = colors,
        style = style,
        title = title,
        subtitle = subtitle,
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
 * @param title the title of this app bar, should typically be [Text].
 * @param subtitle the subtitle of this app bar, should typically be [Text].
 * @param finishIcon the finish icon displayed at the start of the app bar, should typically be [AppBarScope.FinishIconButton].
 * @param navigationIcon the navigation icon displayed at the start of the app bar, should typically be [AppBarScope.NavigationIconButton].
 * @param actions the actions displayed at the end of the app bar, should typically be [AppBarScope.ActionIconButton].
 */
@Composable
fun SecondaryAppBar(
    modifier: Modifier = Modifier,
    colors: AppBarColors = AppBarDefaults.colors(),
    style: AppBarStyle = AppBarDefaults.style(type = AppBarType.Secondary),
    title: @Composable () -> Unit,
    subtitle: @Composable (() -> Unit)? = null,
    finishIcon: @Composable (AppBarScope.() -> Unit)? = null,
    navigationIcon: @Composable (AppBarScope.() -> Unit)? = null,
    actions: @Composable (AppBarScope.() -> Unit)? = null
) {
    BasicAppBar(
        type = AppBarType.Secondary,
        modifier = modifier,
        colors = colors,
        style = style,
        title = title,
        subtitle = subtitle,
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
    colors: AppBarColors,
    style: AppBarStyle,
    title: @Composable () -> Unit,
    subtitle: @Composable (() -> Unit)?,
    finishIcon: @Composable (AppBarScope.() -> Unit)?,
    navigationIcon: @Composable (AppBarScope.() -> Unit)?,
    actions: @Composable (AppBarScope.() -> Unit)?
) {
    Box(modifier = modifier.padding(style.padding)) {
        AppBarImpl(
            type = type,
            colors = colors,
            style = style,
            title = title,
            subtitle = subtitle,
            finishIcon = finishIcon,
            navigationIcon = navigationIcon,
            actions = actions
        ).Content()
    }
}

/**
 * A scope for app bar.
 */
@Stable
interface AppBarScope {

    /**
     * App bar's finish icon button.
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
        colors: ButtonColors = IconButtonDefaults.colors(),
        style: ButtonStyle = IconButtonDefaults.style(),
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
        ) { Icon(imageVector = FlexiIcons.FinishClose) }
    }

    /**
     * App bar's navigation icon button.
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
        colors: ButtonColors = IconButtonDefaults.colors(),
        style: ButtonStyle = IconButtonDefaults.style(),
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
        ) { Icon(imageVector = FlexiIcons.ArrowNaviUp) }
    }

    /**
     * App bar's action icon button.
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
        colors: ButtonColors = IconButtonDefaults.colors(),
        style: ButtonStyle = IconButtonDefaults.style(),
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
    val title: @Composable () -> Unit,
    val subtitle: @Composable (() -> Unit)?,
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
                content = title
            )
            subtitle?.also { content ->
                ContentStyle(
                    color = colors.subtitleTextColor,
                    textStyle = style.subtitleTextStyle,
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

/**
 * App bar's type definition.
 */
@Stable
enum class AppBarType {
    /** @see PrimaryAppBar */
    Primary,

    /** @see SecondaryAppBar */
    Secondary
}

/**
 * Defaults of app bar.
 */
object AppBarDefaults {

    /**
     * Creates a [AppBarColors] with the default values.
     * @param titleTextColor the title text color.
     * @param subtitleTextColor the subtitle text color.
     * @param actionContentColor the action content color, usually for icon tint and text color.
     * @return [AppBarColors]
     */
    @Composable
    fun colors(
        titleTextColor: Color = AppBarProperties.TitleTextColor.toColor(),
        subtitleTextColor: Color = AppBarProperties.SubtitleTextColor.toColor(),
        actionContentColor: Color = AppBarProperties.ActionContentColor.toColor()
    ) = AppBarColors(
        titleTextColor = titleTextColor,
        subtitleTextColor = subtitleTextColor,
        actionContentColor = actionContentColor
    )

    /**
     * Creates a [AppBarStyle] with the default values.
     * @param type the type of app bar.
     * @param padding the padding of content.
     * @param contentSpacing the spacing between the components of content.
     * @param titleTextStyle the title text style.
     * @param subtitleTextStyle the subtitle text style.
     * @param actionIconSize the size of action icon.
     * @param actionIconPadding the padding of action icon.
     * @param actionContentMaxWidth the max width of actions content.
     * @return [AppBarStyle]
     */
    @Composable
    fun style(
        type: AppBarType,
        padding: ComponentPadding = when {
            LocalInSurface.current || LocalInAreaBox.current ->
                AppBarProperties.InBoxPadding
            else -> AppBarProperties.Padding
        }.toPadding(),
        contentSpacing: Dp = AppBarProperties.ContentSpacing.toDp(),
        titleTextStyle: TextStyle = when (type) {
            AppBarType.Primary -> AppBarProperties.PrimaryTitleTextStyle
            AppBarType.Secondary -> AppBarProperties.SecondaryTitleTextStyle
        }.toTextStyle(),
        subtitleTextStyle: TextStyle = AppBarProperties.SubtitleTextStyle.toTextStyle(),
        actionIconSize: Dp = when (type) {
            AppBarType.Primary -> AppBarProperties.PrimaryActionIconSize
            AppBarType.Secondary -> AppBarProperties.SecondaryActionIconSize
        }.toDp(),
        actionIconPadding: Dp = AppBarProperties.ActionIconPadding.toDp(),
        actionContentMaxWidth: Dp = AppBarProperties.ActionContentMaxWidth
    ) = AppBarStyle(
        padding = padding,
        contentSpacing = contentSpacing,
        titleTextStyle = titleTextStyle,
        subtitleTextStyle = subtitleTextStyle,
        actionIconSize = actionIconSize,
        actionIconPadding = actionIconPadding,
        actionContentMaxWidth = actionContentMaxWidth
    )
}

@Stable
internal object AppBarProperties {
    val TitleTextColor = ColorsDescriptor.TextPrimary
    val SubtitleTextColor = ColorsDescriptor.TextSecondary
    val ActionContentColor = ColorsDescriptor.TextPrimary
    val Padding = PaddingDescriptor(SizesDescriptor.SpacingPrimary)
    val InBoxPadding = PaddingDescriptor(vertical = SizesDescriptor.SpacingPrimary)
    val ContentSpacing = SizesDescriptor.SpacingSecondary
    val PrimaryTitleTextStyle = TypographyDescriptor.TitlePrimary
    val SecondaryTitleTextStyle = TypographyDescriptor.TitleSecondary
    val SubtitleTextStyle = TypographyDescriptor.Subtitle
    val PrimaryActionIconSize = SizesDescriptor.IconSizePrimary
    val SecondaryActionIconSize = SizesDescriptor.IconSizeSecondary
    val ActionIconPadding = SizesDescriptor.SpacingTertiary
    val ActionContentMaxWidth = 170.dp
}

private const val VerticalContentSpacingRatio = 1.6f