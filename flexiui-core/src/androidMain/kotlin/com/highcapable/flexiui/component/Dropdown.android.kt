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
 * This file is created by fankes on 2023/11/18.
 */
@file:Suppress("unused")

package com.highcapable.flexiui.component

import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.node.Ref
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.semantics.Role
import com.highcapable.flexiui.interaction.rippleClickable
import kotlin.math.max

@Composable
internal actual fun DropdownListBox(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier,
    properties: DropdownListProperties,
    menuHeightPx: (Int) -> Unit,
    content: @Composable @UiComposable BoxWithConstraintsScope.() -> Unit
) {
    val view = LocalView.current
    val coordinates = remember { Ref<LayoutCoordinates>() }
    BoxWithConstraints(
        modifier = Modifier.dropdownList(
            properties = properties,
            modifier = modifier.rippleClickable(
                enabled = properties.enabled,
                role = Role.DropdownList,
                interactionSource = properties.interactionSource
            ) {
                properties.focusRequester.requestFocus()
                onExpandedChange(!expanded)
            }.onGloballyPositioned {
                coordinates.value = it
                updateHeight(view.rootView, coordinates.value) { newHeight -> menuHeightPx(newHeight) }
            }
        ),
        content = content
    )
    DisposableEffect(view) {
        val listener = OnGlobalLayoutListener(view) {
            updateHeight(view.rootView, coordinates.value) { newHeight -> menuHeightPx(newHeight) }
        }
        onDispose { listener.dispose() }
    }
}

private fun updateHeight(view: View, coordinates: LayoutCoordinates?, onHeightUpdate: (Int) -> Unit) {
    coordinates ?: return
    val visibleWindowBounds = Rect().let { view.getWindowVisibleDisplayFrame(it); it }
    val heightAbove = coordinates.boundsInWindow().top - visibleWindowBounds.top
    val heightBelow = visibleWindowBounds.bottom - visibleWindowBounds.top - coordinates.boundsInWindow().bottom
    onHeightUpdate(max(heightAbove, heightBelow).toInt())
}

private class OnGlobalLayoutListener(
    private val view: View,
    private val onGlobalLayoutCallback: () -> Unit
) : View.OnAttachStateChangeListener, ViewTreeObserver.OnGlobalLayoutListener {

    private var isListeningToGlobalLayout = false

    init {
        view.addOnAttachStateChangeListener(this)
        registerOnGlobalLayoutListener()
    }

    override fun onViewAttachedToWindow(p0: View) = registerOnGlobalLayoutListener()

    override fun onViewDetachedFromWindow(p0: View) = unregisterOnGlobalLayoutListener()

    override fun onGlobalLayout() = onGlobalLayoutCallback()

    private fun registerOnGlobalLayoutListener() {
        if (isListeningToGlobalLayout || !view.isAttachedToWindow) return
        view.viewTreeObserver.addOnGlobalLayoutListener(this)
        isListeningToGlobalLayout = true
    }

    private fun unregisterOnGlobalLayoutListener() {
        if (!isListeningToGlobalLayout) return
        view.viewTreeObserver.removeOnGlobalLayoutListener(this)
        isListeningToGlobalLayout = false
    }

    fun dispose() {
        unregisterOnGlobalLayoutListener()
        view.removeOnAttachStateChangeListener(this)
    }
}