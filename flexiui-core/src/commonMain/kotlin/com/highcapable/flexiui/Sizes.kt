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

package com.highcapable.flexiui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.highcapable.betterandroid.compose.extension.ui.ComponentPadding

/**
 * Sizes defines for Flexi UI.
 */
@ExperimentalFlexiUISizesApi
@Immutable
data class Sizes(
    val spacingPrimary: Dp,
    val spacingSecondary: Dp,
    val spacingTertiary: Dp,
    val iconSizePrimary: Dp,
    val iconSizeSecondary: Dp,
    val iconSizeTertiary: Dp,
    val zoomSizePrimary: Dp,
    val zoomSizeSecondary: Dp,
    val zoomSizeTertiary: Dp,
    val borderSizePrimary: Dp,
    val borderSizeSecondary: Dp,
    val borderSizeTertiary: Dp
)

/**
 * Descriptor for [Sizes].
 */
@ExperimentalFlexiUISizesApi
@Stable
internal enum class SizesDescriptor {
    SpacingPrimary,
    SpacingSecondary,
    SpacingTertiary,
    IconSizePrimary,
    IconSizeSecondary,
    IconSizeTertiary,
    ZoomSizePrimary,
    ZoomSizeSecondary,
    ZoomSizeTertiary,
    BorderSizePrimary,
    BorderSizeSecondary,
    BorderSizeTertiary
}

/**
 * Descriptor for [ComponentPadding].
 */
@Stable
internal fun PaddingDescriptor(
    all: SizesDescriptor
): PaddingDescriptor = PaddingDescriptorImpl(all, all, all, all)

/**
 * Descriptor for [ComponentPadding].
 */
@Stable
internal fun PaddingDescriptor(
    horizontal: SizesDescriptor? = null,
    vertical: SizesDescriptor? = null
): PaddingDescriptor = PaddingDescriptorImpl(horizontal, vertical, vertical, horizontal)

/**
 * Descriptor for [ComponentPadding].
 */
@Stable
internal fun PaddingDescriptor(
    start: SizesDescriptor? = null,
    top: SizesDescriptor? = null,
    bottom: SizesDescriptor? = null,
    end: SizesDescriptor? = null
): PaddingDescriptor = PaddingDescriptorImpl(start, top, bottom, end)

@Stable
internal interface PaddingDescriptor {

    val start: SizesDescriptor?
    val top: SizesDescriptor?
    val bottom: SizesDescriptor?
    val end: SizesDescriptor?

    @Composable
    @ReadOnlyComposable
    fun toPadding(): ComponentPadding
}

@Immutable
private class PaddingDescriptorImpl(
    override val start: SizesDescriptor?,
    override val top: SizesDescriptor?,
    override val bottom: SizesDescriptor?,
    override val end: SizesDescriptor?
) : PaddingDescriptor {

    @Composable
    @ReadOnlyComposable
    override fun toPadding() = ComponentPadding(
        start = start?.toDp() ?: 0.dp,
        top = top?.toDp() ?: 0.dp,
        bottom = bottom?.toDp() ?: 0.dp,
        end = end?.toDp() ?: 0.dp
    )
}

internal val LocalSizes = staticCompositionLocalOf { DefaultSizes }

internal val DefaultSizes = Sizes(
    spacingPrimary = 15.dp,
    spacingSecondary = 10.dp,
    spacingTertiary = 5.dp,
    iconSizePrimary = 27.dp,
    iconSizeSecondary = 25.dp,
    iconSizeTertiary = 20.dp,
    zoomSizePrimary = 15.dp,
    zoomSizeSecondary = 10.dp,
    zoomSizeTertiary = 8.dp,
    borderSizePrimary = 2.dp,
    borderSizeSecondary = 1.dp,
    borderSizeTertiary = 0.dp
)

/**
 * Gets a [Dp] from descriptor.
 * @see SizesDescriptor.toDp
 * @param value the descriptor.
 * @return [Dp]
 */
internal fun Sizes.fromDescriptor(value: SizesDescriptor) = when (value) {
    SizesDescriptor.SpacingPrimary -> spacingPrimary
    SizesDescriptor.SpacingSecondary -> spacingSecondary
    SizesDescriptor.SpacingTertiary -> spacingTertiary
    SizesDescriptor.IconSizePrimary -> iconSizePrimary
    SizesDescriptor.IconSizeSecondary -> iconSizeSecondary
    SizesDescriptor.IconSizeTertiary -> iconSizeTertiary
    SizesDescriptor.ZoomSizePrimary -> zoomSizePrimary
    SizesDescriptor.ZoomSizeSecondary -> zoomSizeSecondary
    SizesDescriptor.ZoomSizeTertiary -> zoomSizeTertiary
    SizesDescriptor.BorderSizePrimary -> borderSizePrimary
    SizesDescriptor.BorderSizeSecondary -> borderSizeSecondary
    SizesDescriptor.BorderSizeTertiary -> borderSizeTertiary
}

/**
 * Converts a descriptor to a [Dp].
 * @see Sizes.fromDescriptor
 * @return [Dp]
 */
@Composable
@ReadOnlyComposable
internal fun SizesDescriptor.toDp() = LocalSizes.current.fromDescriptor(this)

/**
 * The [Sizes] is experimental, the relevant design specifications for size are still being improved,
 * this is the old design plan.
 *
 * Some sizes will modify in the future.
 */
@RequiresOptIn(
    message = "The Sizes is experimental, the relevant design specifications for size are still being improved, this is the old design plan.\n" +
        "Some sizes will modify in the future.",
    level = RequiresOptIn.Level.WARNING
)
@MustBeDocumented
annotation class ExperimentalFlexiUISizesApi