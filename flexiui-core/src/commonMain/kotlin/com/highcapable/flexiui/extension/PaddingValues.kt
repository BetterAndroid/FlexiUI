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
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

@Stable
internal val PaddingValues.left get() = calculateLeftPadding(LayoutDirection.Ltr)

@Stable
internal val PaddingValues.top get() = calculateTopPadding()

@Stable
internal val PaddingValues.bottom get() = calculateBottomPadding()

@Stable
internal val PaddingValues.right get() = calculateRightPadding(LayoutDirection.Ltr)

@Stable
internal val PaddingValues.horizontal get() = left + right

@Stable
internal val PaddingValues.vertical get() = top + bottom

@Composable
internal fun PaddingValues.calculateStart() = calculateStartPadding(LocalLayoutDirection.current)

@Composable
internal fun PaddingValues.calculateEnd() = calculateEndPadding(LocalLayoutDirection.current)