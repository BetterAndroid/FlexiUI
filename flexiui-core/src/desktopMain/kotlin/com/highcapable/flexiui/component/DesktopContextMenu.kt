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
 * This file is created by fankes on 2023/11/8.
 */
@file:Suppress("unused")

package com.highcapable.flexiui.component

import androidx.compose.foundation.ContextMenuItem
import androidx.compose.foundation.ContextMenuRepresentation
import androidx.compose.foundation.ContextMenuState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import com.highcapable.flexiui.LocalColors
import com.highcapable.flexiui.LocalShapes
import com.highcapable.flexiui.LocalSizes
import com.highcapable.flexiui.interaction.rippleClickable
import com.highcapable.flexiui.utils.orElse
import java.awt.event.KeyEvent

@Immutable
data class ContextMenuStyle(
    val backgroundColor: Color,
    val textColor: Color,
    val padding: Dp,
    val shadowSize: Dp,
    val shape: Shape?
)

internal class DesktopContextMenuRepresentation(private val style: ContextMenuStyle) : ContextMenuRepresentation {
    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    override fun Representation(state: ContextMenuState, items: () -> List<ContextMenuItem>) {
        val status = state.status
        if (status is ContextMenuState.Status.Open) {
            val shape = style.shape ?: return
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
                Column(
                    modifier = Modifier
                        .shadow(elevation = style.shadowSize, shape = shape)
                        .background(color = style.backgroundColor, shape = shape)
                        .padding(style.padding)
                        .width(IntrinsicSize.Max)
                        .verticalScroll(rememberScrollState())
                ) {
                    items().forEach { item ->
                        MenuItemContent(
                            shape = shape,
                            onClick = {
                                state.status = ContextMenuState.Status.Closed
                                item.onClick()
                            }
                        ) { Text(text = item.label) }
                    }
                }
            }
        }
    }
}

@Composable
private fun MenuItemContent(
    shape: Shape,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    var hovered by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .clip(shape)
            .rippleClickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() }
            )
            .onHover { hovered = it }
            .fillMaxWidth()
            // Preferred min and max width used during the intrinsic measurement.
            .sizeIn(
                minWidth = 112.dp,
                maxWidth = 280.dp,
                minHeight = 32.dp
            )
            .padding(
                PaddingValues(
                    horizontal = 16.dp,
                    vertical = 0.dp
                )
            ),
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

object DesktopContextMenu {
    val style: ContextMenuStyle
        @Composable
        @ReadOnlyComposable
        get() = LocalContextMenuStyle.current
}

val LocalContextMenuStyle = compositionLocalOf {
    ContextMenuStyle(
        backgroundColor = Color.Unspecified,
        textColor = Color.Unspecified,
        padding = Dp.Unspecified,
        shadowSize = Dp.Unspecified,
        shape = null
    )
}

@Composable
@ReadOnlyComposable
internal fun defaultContextMenuStyle() = ContextMenuStyle(
    backgroundColor = LocalContextMenuStyle.current.backgroundColor.orElse() ?: LocalColors.current.foregroundPrimary,
    textColor = LocalContextMenuStyle.current.textColor.orElse() ?: LocalColors.current.textPrimary,
    padding = LocalContextMenuStyle.current.padding.orElse() ?: LocalSizes.current.spacingTertiary,
    shadowSize = LocalContextMenuStyle.current.shadowSize.orElse() ?: LocalSizes.current.zoomSizeTertiary,
    shape = LocalContextMenuStyle.current.shape ?: LocalShapes.current.primary
)