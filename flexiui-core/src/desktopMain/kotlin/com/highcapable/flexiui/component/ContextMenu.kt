/*
 * Flexi UI - A flexible and useful UI component library.
 * Copyright (C) 2019 HighCapable
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
 * This file is created by fankes on 2023/11/8.
 */
@file:Suppress("unused")

package com.highcapable.flexiui.component

import androidx.compose.foundation.ContextMenuItem
import androidx.compose.foundation.ContextMenuRepresentation
import androidx.compose.foundation.ContextMenuState
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.InputMode
import androidx.compose.ui.input.InputModeManager
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalInputModeManager
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.window.rememberPopupPositionProviderAtPosition
import com.highcapable.betterandroid.compose.extension.ui.ComponentPadding
import com.highcapable.flexiui.ColorsDescriptor
import com.highcapable.flexiui.PaddingDescriptor
import com.highcapable.flexiui.ShapesDescriptor
import com.highcapable.flexiui.SizesDescriptor
import com.highcapable.flexiui.component.interaction.rippleClickable
import com.highcapable.flexiui.toColor
import com.highcapable.flexiui.toDp
import com.highcapable.flexiui.toShape
import java.awt.event.KeyEvent

/**
 * Colors defines for the context menu.
 * @see ContextMenuDefaults.colors
 */
@Immutable
data class ContextMenuColors(
    val contentColor: Color,
    val borderColor: Color
)

/**
 * Style defines for the context menu.
 * @see ContextMenuDefaults.style
 */
@Immutable
data class ContextMenuStyle(
    val padding: ComponentPadding,
    val shape: Shape,
    val borderWidth: Dp,
    val contentPadding: ComponentPadding,
    val contentShape: Shape,
    val shadowSize: Dp
)

internal class FlexiContextMenuRepresentation : ContextMenuRepresentation {

    @Composable
    override fun Representation(state: ContextMenuState, items: () -> List<ContextMenuItem>) {
        val colors = LocalContextMenuColors.current ?: ContextMenuDefaults.colors()
        val style = LocalContextMenuStyle.current ?: ContextMenuDefaults.style()
        val status = state.status
        if (status is ContextMenuState.Status.Open) {
            var focusManager: FocusManager? by mutableStateOf(null)
            var inputModeManager: InputModeManager? by mutableStateOf(null)
            Popup(
                popupPositionProvider = rememberPopupPositionProviderAtPosition(positionPx = status.rect.center),
                onDismissRequest = { state.status = ContextMenuState.Status.Closed },
                properties = PopupProperties(focusable = true), onPreviewKeyEvent = { false }, onKeyEvent = {
                    if (it.type == KeyEventType.KeyDown)
                        when (it.key.nativeKeyCode) {
                            KeyEvent.VK_DOWN -> {
                                inputModeManager?.requestInputMode(InputMode.Keyboard)
                                focusManager?.moveFocus(FocusDirection.Next)
                                true
                            }
                            KeyEvent.VK_UP -> {
                                inputModeManager?.requestInputMode(InputMode.Keyboard)
                                focusManager?.moveFocus(FocusDirection.Previous)
                                true
                            }
                            else -> false
                        }
                    else false
                }) {
                focusManager = LocalFocusManager.current
                inputModeManager = LocalInputModeManager.current
                AreaColumn(
                    modifier = Modifier
                        .width(IntrinsicSize.Max)
                        .verticalScroll(rememberScrollState()),
                    colors = AreaBoxDefaults.colors(backgroundColor = colors.borderColor),
                    style = AreaBoxDefaults.style(
                        padding = style.padding,
                        shape = style.shape,
                        borderWidth = style.borderWidth,
                        shadowSize = style.shadowSize
                    )
                ) {
                    items().forEach { item ->
                        MenuItemContent(
                            style = AreaBoxDefaults.style(
                                padding = style.contentPadding,
                                shape = style.contentShape
                            ),
                            onClick = {
                                state.status = ContextMenuState.Status.Closed
                                item.onClick()
                            }
                        ) { Text(text = item.label, color = colors.contentColor) }
                    }
                }
            }
        }
    }
}

@Composable
private fun MenuItemContent(
    style: AreaBoxStyle,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    var hovered by remember { mutableStateOf(false) }
    AreaRow(
        modifier = Modifier
            .rippleClickable(onClick = onClick)
            .onHover { hovered = it }
            .fillMaxWidth()
            .sizeIn(
                minWidth = MenuContentMinWidth,
                maxWidth = MenuContentMaxWidth,
                minHeight = MenuContentMinHeight
            ),
        colors = AreaBoxDefaults.colors(backgroundColor = Color.Transparent),
        style = style,
        verticalAlignment = Alignment.CenterVertically
    ) { content() }
}

private fun Modifier.onHover(onHover: (Boolean) -> Unit) = pointerInput(Unit) {
    awaitPointerEventScope {
        while (true) {
            val event = awaitPointerEvent()
            when (event.type) {
                PointerEventType.Enter -> onHover(true)
                PointerEventType.Exit -> onHover(false)
            }
        }
    }
}

/**
 * Defaults of context menu.
 */
object ContextMenuDefaults {

    /**
     * Creates a [ContextMenuColors] with the default values.
     * @param contentColor the content color, usually for the text color.
     * @param borderColor the border color.
     * @return [ContextMenuColors]
     */
    @Composable
    fun colors(
        contentColor: Color = ContextMenuProperties.ContentColor.toColor(),
        borderColor: Color = ContextMenuProperties.BorderColor.toColor()
    ) = ContextMenuColors(
        contentColor = contentColor,
        borderColor = borderColor
    )

    /**
     * Creates a [ContextMenuStyle] with the default values.
     * @param padding the menu padding.
     * @param shape the menu shape.
     * @param borderWidth the menu border width.
     * @param contentPadding the content padding.
     * @param contentShape the content shape.
     * @param shadowSize the shadow size.
     * @return [ContextMenuStyle]
     */
    @Composable
    fun style(
        padding: ComponentPadding = ContextMenuProperties.Padding.toPadding(),
        shape: Shape = ContextMenuProperties.Shape.toShape(),
        borderWidth: Dp = ContextMenuProperties.BorderWidth.toDp(),
        contentPadding: ComponentPadding = ContextMenuProperties.ContentPadding,
        contentShape: Shape = ContextMenuProperties.ContentShape.toShape(),
        shadowSize: Dp = ContextMenuProperties.ShadowSize.toDp()
    ) = ContextMenuStyle(
        padding = padding,
        shape = shape,
        borderWidth = borderWidth,
        contentPadding = contentPadding,
        contentShape = contentShape,
        shadowSize = shadowSize
    )
}

@Stable
internal object ContextMenuProperties {
    val ContentColor = ColorsDescriptor.TextPrimary
    val BorderColor = ColorsDescriptor.BackgroundSecondary
    val Padding = PaddingDescriptor(SizesDescriptor.SpacingTertiary)
    val Shape = ShapesDescriptor.Primary
    val BorderWidth = AreaBoxProperties.BorderWidth
    val ContentPadding = ComponentPadding(horizontal = 16.dp)
    val ContentShape = ShapesDescriptor.Secondary
    val ShadowSize = SizesDescriptor.ZoomSizeTertiary
}

/**
 * Composition local containing the preferred [ContextMenuColors]
 * that will be used by [ContextMenuRepresentation] by default.
 */
val LocalContextMenuColors = compositionLocalOf<ContextMenuColors?> { null }

/**
 * Composition local containing the preferred [ContextMenuStyle]
 * that will be used by [ContextMenuRepresentation] by default.
 */
val LocalContextMenuStyle = compositionLocalOf<ContextMenuStyle?> { null }

private val MenuContentMinWidth = 112.dp
private val MenuContentMaxWidth = 280.dp
private val MenuContentMinHeight = 32.dp