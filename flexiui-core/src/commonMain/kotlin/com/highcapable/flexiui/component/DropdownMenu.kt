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
 * This file is created by fankes on 2023/11/9.
 */
@file:Suppress("unused")

package com.highcapable.flexiui.component

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.InputMode
import androidx.compose.ui.input.InputModeManager
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalInputModeManager
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import com.highcapable.flexiui.LocalColors
import com.highcapable.flexiui.LocalShapes
import com.highcapable.flexiui.LocalSizes
import com.highcapable.flexiui.interaction.rippleClickable
import com.highcapable.flexiui.utils.orElse
import com.highcapable.flexiui.utils.status
import kotlin.math.max
import kotlin.math.min

@Immutable
data class DropdownMenuColors(
    val contentColor: Color,
    val borderColor: Color
)

@Immutable
data class DropdownMenuStyle(
    val inTransitionDuration: Int,
    val outTransitionDuration: Int,
    val contentStyle: AreaBoxStyle,
    val borderStyle: AreaBoxStyle
)

@Composable
fun DropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    colors: DropdownMenuColors = DropdownMenu.colors,
    style: DropdownMenuStyle = DropdownMenu.style,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    scrollState: ScrollState = rememberScrollState(),
    properties: PopupProperties = PopupProperties(focusable = true),
    content: @Composable ColumnScope.() -> Unit
) {
    val expandedStates = remember { MutableTransitionState(false) }
    expandedStates.targetState = expanded
    if (expandedStates.currentState || expandedStates.targetState) {
        val density = LocalDensity.current
        val transformOriginState = remember { mutableStateOf(TransformOrigin.Center) }
        val popupPositionProvider = DropdownMenuPositionProvider(offset, density) { parentBounds, menuBounds ->
            transformOriginState.value = calculateTransformOrigin(parentBounds, menuBounds)
        }
        var focusManager: FocusManager? by mutableStateOf(null)
        var inputModeManager: InputModeManager? by mutableStateOf(null)
        Popup(
            onDismissRequest = onDismissRequest,
            popupPositionProvider = popupPositionProvider,
            properties = properties,
            onKeyEvent = { handlePopupOnKeyEvent(it, focusManager, inputModeManager) }
        ) {
            focusManager = LocalFocusManager.current
            inputModeManager = LocalInputModeManager.current
            DropdownMenuContent(
                expandedStates = expandedStates,
                transformOriginState = transformOriginState,
                scrollState = scrollState,
                modifier = modifier,
                colors = colors,
                style = style,
                content = content
            )
        }
    }
}

@Composable
fun DropdownMenuItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentColor: Color = Color.Unspecified,
    contentStyle: AreaBoxStyle? = null,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit
) {
    val currentColor = contentColor.orElse() ?: LocalDropdownMenuContentColor.current.orElse() ?: DropdownMenu.colors.contentColor
    val currentStyle = contentStyle ?: LocalDropdownMenuContentStyle.current ?: DropdownMenu.style.contentStyle
    AreaRow(
        modifier = Modifier.status(enabled)
            .then(modifier)
            .fillMaxWidth()
            .sizeIn(
                minWidth = DefaultMenuItemMinWidth,
                maxWidth = DefaultMenuItemMaxWidth,
                minHeight = DefaultMenuItemMinHeight
            )
            .rippleClickable(
                enabled = enabled,
                role = Role.DropdownList,
                interactionSource = interactionSource,
                onClick = onClick
            ),
        color = Color.Transparent,
        style = currentStyle,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CompositionLocalProvider(LocalTextStyle provides LocalTextStyle.current.default(currentColor)) {
            content()
        }
    }
}

@Composable
private fun DropdownMenuContent(
    expandedStates: MutableTransitionState<Boolean>,
    transformOriginState: MutableState<TransformOrigin>,
    scrollState: ScrollState,
    modifier: Modifier = Modifier,
    colors: DropdownMenuColors,
    style: DropdownMenuStyle,
    content: @Composable ColumnScope.() -> Unit
) {
    val transition = updateTransition(expandedStates, label = "DropDownMenu")
    val scale by transition.animateFloat(
        transitionSpec = {
            if (false isTransitioningTo true) tween(
                durationMillis = style.inTransitionDuration,
                easing = LinearOutSlowInEasing
            ) else tween(
                durationMillis = 1,
                delayMillis = style.outTransitionDuration - 1
            )
        }
    ) { if (it) 1f else 0.8f }
    val alpha by transition.animateFloat(
        transitionSpec = {
            if (false isTransitioningTo true) tween(durationMillis = 30)
            else tween(durationMillis = style.outTransitionDuration)
        }
    ) { if (it) 1f else 0f }
    AreaColumn(
        modifier = modifier.width(IntrinsicSize.Max)
            .verticalScroll(scrollState),
        initializer = {
            graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
                transformOrigin = transformOriginState.value
            }
        },
        style = style.borderStyle
    ) {
        CompositionLocalProvider(
            LocalDropdownMenuContentColor provides colors.contentColor,
            LocalDropdownMenuContentStyle provides style.contentStyle
        ) { content() }
    }
}

private fun calculateTransformOrigin(parentBounds: IntRect, menuBounds: IntRect): TransformOrigin {
    val pivotX = when {
        menuBounds.left >= parentBounds.right -> 0f
        menuBounds.right <= parentBounds.left -> 1f
        menuBounds.width == 0 -> 0f
        else -> {
            val intersectionCenter =
                (max(parentBounds.left, menuBounds.left) + min(parentBounds.right, menuBounds.right)) / 2
            (intersectionCenter - menuBounds.left).toFloat() / menuBounds.width
        }
    }
    val pivotY = when {
        menuBounds.top >= parentBounds.bottom -> 0f
        menuBounds.bottom <= parentBounds.top -> 1f
        menuBounds.height == 0 -> 0f
        else -> {
            val intersectionCenter =
                (max(parentBounds.top, menuBounds.top) + min(parentBounds.bottom, menuBounds.bottom)) / 2
            (intersectionCenter - menuBounds.top).toFloat() / menuBounds.height
        }
    }
    return TransformOrigin(pivotX, pivotY)
}

@OptIn(ExperimentalComposeUiApi::class)
private fun handlePopupOnKeyEvent(
    keyEvent: KeyEvent,
    focusManager: FocusManager?,
    inputModeManager: InputModeManager?
) = if (keyEvent.type == KeyEventType.KeyDown) when (keyEvent.key) {
    Key.DirectionDown -> {
        inputModeManager?.requestInputMode(InputMode.Keyboard)
        focusManager?.moveFocus(FocusDirection.Next)
        true
    }
    Key.DirectionUp -> {
        inputModeManager?.requestInputMode(InputMode.Keyboard)
        focusManager?.moveFocus(FocusDirection.Previous)
        true
    }
    else -> false
} else false

@Immutable
private data class DropdownMenuPositionProvider(
    val contentOffset: DpOffset,
    val density: Density,
    val onPositionCalculated: (IntRect, IntRect) -> Unit = { _, _ -> }
) : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {
        val verticalMargin = with(density) { 48.dp.roundToPx() }
        val contentOffsetX = with(density) { contentOffset.x.roundToPx() }
        val contentOffsetY = with(density) { contentOffset.y.roundToPx() }
        val toRight = anchorBounds.left + contentOffsetX
        val toLeft = anchorBounds.right - contentOffsetX - popupContentSize.width
        val toDisplayRight = windowSize.width - popupContentSize.width
        val toDisplayLeft = 0
        val x = (if (layoutDirection == LayoutDirection.Ltr)
            sequenceOf(toRight, toLeft, if (anchorBounds.left >= 0) toDisplayRight else toDisplayLeft)
        else sequenceOf(toLeft, toRight, if (anchorBounds.right <= windowSize.width) toDisplayLeft else toDisplayRight)).firstOrNull {
            it >= 0 && it + popupContentSize.width <= windowSize.width
        } ?: toLeft
        val toBottom = maxOf(anchorBounds.bottom + contentOffsetY, verticalMargin)
        val toTop = anchorBounds.top - contentOffsetY - popupContentSize.height
        val toCenter = anchorBounds.top - popupContentSize.height / 2
        val toDisplayBottom = windowSize.height - popupContentSize.height - verticalMargin
        val y = sequenceOf(toBottom, toTop, toCenter, toDisplayBottom).firstOrNull {
            it >= verticalMargin &&
                it + popupContentSize.height <= windowSize.height - verticalMargin
        } ?: toTop
        onPositionCalculated(anchorBounds, IntRect(x, y, x + popupContentSize.width, y + popupContentSize.height))
        return IntOffset(x, y)
    }
}

object DropdownMenu {
    val colors: DropdownMenuColors
        @Composable
        @ReadOnlyComposable
        get() = defaultDropdownMenuColors()
    val style: DropdownMenuStyle
        @Composable
        @ReadOnlyComposable
        get() = defaultDropdownMenuStyle()
}

private val LocalDropdownMenuContentColor = compositionLocalOf { Color.Unspecified }

private val LocalDropdownMenuContentStyle = compositionLocalOf<AreaBoxStyle?> { null }

@Composable
@ReadOnlyComposable
private fun defaultDropdownMenuColors() = DropdownMenuColors(
    contentColor = LocalColors.current.textPrimary,
    borderColor = AreaBox.color
)

@Composable
@ReadOnlyComposable
private fun defaultDropdownMenuStyle() = DropdownMenuStyle(
    inTransitionDuration = DefaultInTransitionDuration,
    outTransitionDuration = DefaultOutTransitionDuration,
    contentStyle = AreaBox.style.copy(
        padding = 0.dp,
        startPadding = DefaultMenuContentPadding,
        endPadding = DefaultMenuContentPadding,
        shape = LocalShapes.current.secondary
    ),
    borderStyle = AreaBox.style.copy(
        padding = LocalSizes.current.spacingTertiary,
        shadowSize = LocalSizes.current.zoomSizeTertiary,
        shape = LocalShapes.current.primary
    )
)

private val DefaultMenuContentPadding = 16.dp

private const val DefaultInTransitionDuration = 120
private const val DefaultOutTransitionDuration = 90

private val DefaultMenuItemMinWidth = 112.dp
private val DefaultMenuItemMaxWidth = 280.dp
private val DefaultMenuItemMinHeight = 32.dp