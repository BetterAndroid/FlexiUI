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
 * This file is created by fankes on 2024/1/13.
 */
package com.highcapable.flexiui.demo.bean

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
data class SimpleListBean(
    val index: Int,
    val title: String,
    val subtitle: String
) {

    companion object {

        @Stable
        fun create(index: Int) = SimpleListBean(
            index = index,
            title = "Item $index",
            subtitle = "This is a simple data of index $index"
        )
    }
}