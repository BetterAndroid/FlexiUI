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
 * This file is created by fankes on 2023/11/27.
 */
@file:Suppress("unused")

package com.highcapable.flexiui.extension

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

// TODO: Decoupling to BetterAndroid

@Stable
fun ComponentPadding(all: Dp): ComponentPadding = ComponentPaddingImpl(all, all, all, all)

@Stable
fun ComponentPadding(
    horizontal: Dp = 0.dp,
    vertical: Dp = 0.dp
): ComponentPadding = ComponentPaddingImpl(horizontal, vertical, horizontal, vertical)

@Stable
fun ComponentPadding(
    start: Dp = 0.dp,
    top: Dp = 0.dp,
    end: Dp = 0.dp,
    bottom: Dp = 0.dp
): ComponentPadding = ComponentPaddingImpl(start, top, end, bottom)

@Stable
interface ComponentPadding : PaddingValues {
    val start: Dp
    val top: Dp
    val end: Dp
    val bottom: Dp
    val horizontal: Dp
    val vertical: Dp

    fun copy(
        start: Dp = this.start,
        top: Dp = this.top,
        end: Dp = this.end,
        bottom: Dp = this.bottom
    ): ComponentPadding
}

@Immutable
private class ComponentPaddingImpl(
    override val start: Dp,
    override val top: Dp,
    override val end: Dp,
    override val bottom: Dp
) : ComponentPadding {

    override val horizontal get() = start + end
    override val vertical get() = top + bottom

    override fun calculateLeftPadding(layoutDirection: LayoutDirection) =
        if (layoutDirection == LayoutDirection.Ltr) start else end

    override fun calculateTopPadding() = top

    override fun calculateRightPadding(layoutDirection: LayoutDirection) =
        if (layoutDirection == LayoutDirection.Ltr) end else start

    override fun calculateBottomPadding() = bottom

    override fun copy(start: Dp, top: Dp, end: Dp, bottom: Dp) = ComponentPadding(start, top, end, bottom)

    override fun equals(other: Any?): Boolean {
        if (other !is ComponentPadding) return false
        return start == other.start &&
            top == other.top &&
            end == other.end &&
            bottom == other.bottom
    }

    override fun hashCode() =
        ((start.hashCode() * 31 + top.hashCode()) * 31 + end.hashCode()) *
            31 + bottom.hashCode()

    override fun toString() = "ComponentPadding(start=$start, top=$top, end=$end, bottom=$bottom)"
}