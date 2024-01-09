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
 * This file is created by fankes on 2023/11/10.
 */
@file:Suppress("unused", "ConstPropertyName")

package com.highcapable.flexiui.component.interaction

import androidx.compose.foundation.Indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import com.highcapable.betterandroid.compose.extension.ui.clickable
import com.highcapable.betterandroid.compose.extension.ui.combinedClickable
import com.highcapable.betterandroid.compose.extension.ui.selectable
import com.highcapable.betterandroid.compose.extension.ui.toggleable
import com.highcapable.flexiui.ColorsDescriptor
import com.highcapable.flexiui.toColor
import androidx.compose.material.ripple.rememberRipple as materialRememberRipple

/**
 * Style defines for ripple.
 * @see InteractionDefaults.rippleStyle
 */
@Immutable
data class RippleStyle(
    val bounded: Boolean,
    val radius: Dp,
    val color: Color
)

/**
 * Creates and remember a ripple effect [Indication] with the given [style].
 * @see materialRememberRipple
 * @param style the style, default is [InteractionDefaults.rippleStyle].
 * @return [Indication]
 */
@Composable
fun rememberRipple(style: RippleStyle = InteractionDefaults.rippleStyle()) =
    materialRememberRipple(style.bounded, style.radius, style.color)

/**
 * The clickable modifier has a ripple effect.
 * @see Modifier.clickable
 * @receiver [Modifier]
 * @param rippleStyle the ripple style, default is [InteractionDefaults.rippleStyle].
 * @param interactionSource the interaction source.
 * @param enabled whether to enable the event, default is true.
 * @param onClickLabel the click label.
 * @param role the role.
 * @param onClick the click event.
 * @return [Modifier]
 */
fun Modifier.rippleClickable(
    rippleStyle: RippleStyle? = null,
    interactionSource: MutableInteractionSource? = null,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "rippleClickable"
        properties["rippleStyle"] = rippleStyle
        properties["interactionSource"] = interactionSource
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
    }
) {
    val currentRippleStyle = rippleStyle ?: InteractionDefaults.rippleStyle()
    val currentIndication = rememberRipple(currentRippleStyle)
    clickable(
        onClick = onClick,
        interactionSource = interactionSource,
        indication = currentIndication,
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role
    )
}

/**
 * The combined clickable modifier has a ripple effect.
 * @see Modifier.combinedClickable
 * @receiver [Modifier]
 * @param rippleStyle the ripple style, default is [InteractionDefaults.rippleStyle].
 * @param interactionSource the interaction source.
 * @param enabled whether to enable the event, default is true.
 * @param onClickLabel the click label.
 * @param role the role.
 * @param onLongClickLabel the long click label.
 * @param onLongClick the long click event.
 * @param onDoubleClick the double click event.
 * @param onClick the click event.
 * @return [Modifier]
 */
fun Modifier.rippleCombinedClickable(
    rippleStyle: RippleStyle? = null,
    interactionSource: MutableInteractionSource? = null,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onLongClickLabel: String? = null,
    onLongClick: (() -> Unit)? = null,
    onDoubleClick: (() -> Unit)? = null,
    onClick: () -> Unit
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "rippleCombinedClickable"
        properties["rippleStyle"] = rippleStyle
        properties["interactionSource"] = interactionSource
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onLongClickLabel"] = onLongClickLabel
        properties["onLongClick"] = onLongClick
        properties["onDoubleClick"] = onDoubleClick
        properties["onClick"] = onClick
    }
) {
    val currentRippleStyle = rippleStyle ?: InteractionDefaults.rippleStyle()
    val currentIndication = rememberRipple(currentRippleStyle)
    combinedClickable(
        onClick = onClick,
        interactionSource = interactionSource,
        indication = currentIndication,
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role,
        onLongClickLabel = onLongClickLabel,
        onLongClick = onLongClick,
        onDoubleClick = onDoubleClick
    )
}

/**
 * The toggleable modifier has a ripple effect.
 * @see Modifier.toggleable
 * @receiver [Modifier]
 * @param rippleStyle the ripple style, default is [InteractionDefaults.rippleStyle].
 * @param interactionSource the interaction source.
 * @param enabled whether to enable the event, default is true.
 * @param role the role.
 * @param onValueChange the value change event.
 * @return [Modifier]
 */
fun Modifier.rippleToggleable(
    value: Boolean,
    rippleStyle: RippleStyle? = null,
    interactionSource: MutableInteractionSource? = null,
    enabled: Boolean = true,
    role: Role? = null,
    onValueChange: (Boolean) -> Unit
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "rippleToggleable"
        properties["value"] = value
        properties["rippleStyle"] = rippleStyle
        properties["interactionSource"] = interactionSource
        properties["enabled"] = enabled
        properties["role"] = role
        properties["onValueChange"] = onValueChange
    }
) {
    val currentRippleStyle = rippleStyle ?: InteractionDefaults.rippleStyle()
    val currentIndication = rememberRipple(currentRippleStyle)
    toggleable(
        value = value,
        interactionSource = interactionSource,
        indication = currentIndication,
        enabled = enabled,
        role = role,
        onValueChange = onValueChange
    )
}

/**
 * The selectable modifier has a ripple effect.
 * @see Modifier.selectable
 * @receiver [Modifier]
 * @param rippleStyle the ripple style, default is [InteractionDefaults.rippleStyle].
 * @param interactionSource the interaction source.
 * @param enabled whether to enable the event, default is true.
 * @param role the role.
 * @param onClick the click event.
 * @return [Modifier]
 */
fun Modifier.rippleSelectable(
    selected: Boolean,
    rippleStyle: RippleStyle? = null,
    interactionSource: MutableInteractionSource? = null,
    enabled: Boolean = true,
    role: Role? = null,
    onClick: () -> Unit
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "rippleSelectable"
        properties["selected"] = selected
        properties["rippleStyle"] = rippleStyle
        properties["interactionSource"] = interactionSource
        properties["enabled"] = enabled
        properties["role"] = role
        properties["onClick"] = onClick
    }
) {
    val currentRippleStyle = rippleStyle ?: InteractionDefaults.rippleStyle()
    val currentIndication = rememberRipple(currentRippleStyle)
    selectable(
        selected = selected,
        interactionSource = interactionSource,
        indication = currentIndication,
        enabled = enabled,
        role = role,
        onClick = onClick
    )
}

/**
 * Defaults of interaction.
 */
object InteractionDefaults {

    /**
     * Creates a [RippleStyle] with the default values.
     * @param bounded whether the ripple is bounded.
     * @param radius the radius.
     * @param color the color.
     * @return [RippleStyle]
     */
    @Composable
    fun rippleStyle(
        bounded: Boolean = InteractionProperties.RippleBounded,
        radius: Dp = InteractionProperties.RippleRadius,
        color: Color = InteractionProperties.RippleColor.toColor()
    ) = RippleStyle(
        bounded = bounded,
        radius = radius,
        color = color
    )
}

@Stable
internal object InteractionProperties {
    const val RippleBounded = true
    val RippleRadius = Dp.Unspecified
    val RippleColor = ColorsDescriptor.ThemeSecondary
}

/**
 * Composition local containing the preferred [RippleStyle]
 * that will be used by interaction by default.
 */
val LocalRippleStyle = compositionLocalOf<RippleStyle?> { null }