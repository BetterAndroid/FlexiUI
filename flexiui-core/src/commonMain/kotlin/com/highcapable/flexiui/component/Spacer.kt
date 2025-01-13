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
 * This file is created by fankes on 2024/1/17.
 */
@file:Suppress("unused")

package com.highcapable.flexiui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.highcapable.flexiui.LocalSizes
import com.highcapable.flexiui.Sizes

/**
 * Flexi UI primary spacer.
 * @see Spacer
 * @see Sizes.spacingPrimary
 */
@Composable
fun PrimarySpacer() {
    Spacer(modifier = Modifier.size(LocalSizes.current.spacingPrimary))
}

/**
 * Flexi UI secondary spacer.
 * @see Spacer
 * @see Sizes.spacingSecondary
 */
@Composable
fun SecondarySpacer() {
    Spacer(modifier = Modifier.size(LocalSizes.current.spacingSecondary))
}

/**
 * Flexi UI tertiary spacer.
 * @see Spacer
 * @see Sizes.spacingTertiary
 */
@Composable
fun TertiarySpacer() {
    Spacer(modifier = Modifier.size(LocalSizes.current.spacingTertiary))
}