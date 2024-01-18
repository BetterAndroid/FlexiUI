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
 * This file is created by fankes on 2023/11/9.
 */
@file:Suppress("unused", "ConstPropertyName")

package com.highcapable.flexiui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
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
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import com.highcapable.betterandroid.compose.extension.ui.ComponentPadding
import com.highcapable.betterandroid.compose.extension.ui.borderOrElse
import com.highcapable.betterandroid.compose.extension.ui.componentState
import com.highcapable.betterandroid.compose.extension.ui.orNull
import com.highcapable.betterandroid.compose.extension.ui.window.Popup
import com.highcapable.flexiui.ColorsDescriptor
import com.highcapable.flexiui.PaddingDescriptor
import com.highcapable.flexiui.ShapesDescriptor
import com.highcapable.flexiui.SizesDescriptor
import com.highcapable.flexiui.component.interaction.rippleClickable
import com.highcapable.flexiui.resources.FlexiIcons
import com.highcapable.flexiui.resources.icon.Dropdown
import com.highcapable.flexiui.toColor
import com.highcapable.flexiui.toDp
import com.highcapable.flexiui.toShape
import kotlin.math.max
import kotlin.math.min

/**
 * Colors defines for dropdown list.
 * @see DropdownListDefaults.colors
 */
@Immutable
data class DropdownListColors(
    val endIconInactiveTint: Color,
    val endIconActiveTint: Color,
    val backgroundColor: Color,
    val borderInactiveColor: Color,
    val borderActiveColor: Color
)

/**
 * Colors defines for dropdown menu.
 * @see DropdownMenuDefaults.colors
 */
@Immutable
data class DropdownMenuColors(
    val contentColor: Color,
    val activeColor: Color,
    val backgroundColor: Color,
    val borderColor: Color
)

/**
 * Style defines for dropdown list.
 * @see DropdownListDefaults.style
 */
@Immutable
data class DropdownListStyle(
    val padding: ComponentPadding,
    val shape: Shape,
    val endIconSize: Dp,
    val borderInactiveWidth: Dp,
    val borderActiveWidth: Dp
)

/**
 * Style defines for dropdown menu.
 * @see DropdownMenuDefaults.style
 */
@Immutable
data class DropdownMenuStyle(
    val padding: ComponentPadding,
    val shape: Shape,
    val borderWidth: Dp,
    val contentPadding: ComponentPadding,
    val contentShape: Shape,
    val shadowSize: Dp,
    val inTransitionDuration: Int,
    val outTransitionDuration: Int
)

/**
 * Flexi UI dropdown list.
 * @see DropdownMenu
 * @see DropdownMenuItem
 * @param expanded whether the dropdown menu is expanded.
 * @param onExpandedChange the callback when the expanded state is changed.
 * @param modifier the [Modifier] to be applied to this dropdown list.
 * @param colors the colors of this dropdown list, default is [DropdownListDefaults.colors].
 * @param style the style of this dropdown list, default is [DropdownListDefaults.style].
 * @param menuColors the colors of the dropdown menu, default is [DropdownMenuDefaults.colors].
 * @param menuStyle the style of the dropdown menu, default is [DropdownMenuDefaults.style].
 * @param enabled whether the dropdown list is enabled, default is true.
 * @param scrollState the scroll state of the dropdown menu.
 * @param properties the popup properties.
 * @param interactionSource the interaction source of the dropdown list.
 * @param text the text of the [DropdownList], should typically be [Text].
 * @param content the content of the [DropdownMenu], should typically be [DropdownMenuItem].
 */
@Composable
fun DropdownList(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    colors: DropdownListColors = DropdownListDefaults.colors(),
    style: DropdownListStyle = DropdownListDefaults.style(),
    menuColors: DropdownMenuColors = DropdownMenuDefaults.colors(),
    menuStyle: DropdownMenuStyle = DropdownMenuDefaults.style(),
    enabled: Boolean = true,
    scrollState: ScrollState = rememberScrollState(),
    properties: PopupProperties = PopupProperties(focusable = true),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    text: @Composable () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val focused by interactionSource.collectIsFocusedAsState()
    val hovered by interactionSource.collectIsHoveredAsState()
    val focusRequester = remember { FocusRequester() }
    val animatedEndIconTint by animateColorAsState(when {
        focused || hovered -> colors.endIconActiveTint
        else -> colors.endIconInactiveTint
    })
    val animatedBorderColor by animateColorAsState(when {
        focused || hovered -> colors.borderActiveColor
        else -> colors.borderInactiveColor
    })
    val animatedDirection by animateFloatAsState(if (expanded) 180f else 0f)
    val animatedBorderWidth by animateDpAsState(when {
        focused -> style.borderActiveWidth
        else -> style.borderInactiveWidth
    })
    DropdownMenuBox(
        modifier = Modifier.dropdownList(
            enabled = enabled,
            colors = colors,
            style = style,
            borderColor = animatedBorderColor,
            borderWidth = animatedBorderWidth,
            focusRequester = focusRequester,
            interactionSource = interactionSource,
            then = modifier.rippleClickable(
                enabled = enabled,
                role = Role.DropdownList,
                interactionSource = interactionSource
            ) {
                focusRequester.requestFocus()
                onExpandedChange(!expanded)
            }
        )
    ) {
        val menuMaxWidth = maxWidth + style.padding.horizontal
        // Note: If minWidth is not 0, a constant width is currently set.
        //       At this time, the child layout must be completely filled into the parent layout.
        val needInflatable = minWidth > 0.dp
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(modifier = Modifier.weight(1f, needInflatable)) { text() }
            Icon(
                modifier = Modifier
                    .graphicsLayer { rotationZ = animatedDirection }
                    .size(style.endIconSize),
                imageVector = FlexiIcons.Dropdown,
                style = IconDefaults.style(tint = animatedEndIconTint)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
            offset = DpOffset(-style.padding.start, style.padding.end),
            modifier = Modifier
                .width(menuMaxWidth)
                .heightIn(max = menuMaxHeight),
            colors = menuColors,
            style = menuStyle,
            scrollState = scrollState,
            properties = properties,
            content = content
        )
    }
}

/**
 * Flexi UI dropdown menu.
 * @see DropdownList
 * @see DropdownMenuItem
 * @param expanded whether the dropdown menu is expanded.
 * @param onDismissRequest the callback when the dropdown menu is dismissed.
 * @param modifier the [Modifier] to be applied to this dropdown menu.
 * @param colors the colors of this dropdown menu, default is [DropdownMenuDefaults.colors].
 * @param style the style of this dropdown menu, default is [DropdownMenuDefaults.style].
 * @param offset the offset of this dropdown menu.
 * @param scrollState the scroll state of the dropdown menu.
 * @param properties the popup properties.
 * @param content the content of the [DropdownMenu], should typically be [DropdownMenuItem].
 */
@Composable
fun DropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    colors: DropdownMenuColors = DropdownMenuDefaults.colors(),
    style: DropdownMenuStyle = DropdownMenuDefaults.style(),
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

/**
 * A box for manually measuring the size of the dropdown menu.
 * @see DropdownList
 * @see DropdownMenu
 * @param modifier the [Modifier] to be applied to this box.
 * @param content the content for measuring.
 */
@Composable
fun DropdownMenuBox(
    modifier: Modifier = Modifier,
    content: @Composable DropdownMenuBoxScope.() -> Unit
) {
    var menuMaxHeight by remember { mutableStateOf(Dp.Unspecified) }
    DropdownMenuMeasureBox(menuMaxHeight = { menuMaxHeight = it }) {
        BoxWithConstraints(modifier = modifier) {
            val currentConstraints = constraints
            val currentMaxHeight = maxHeight
            val currentMaxWidth = maxWidth
            val currentMinHeight = minHeight
            val currentMinWidth = minWidth
            fun Modifier.currentAlign(alignment: Alignment) = align(alignment).then(modifier)
            fun Modifier.currentMatchParentSize() = matchParentSize().then(modifier)
            object : DropdownMenuBoxScope {
                override val menuMaxHeight = menuMaxHeight
                override val constraints get() = currentConstraints
                override val maxHeight get() = currentMaxHeight
                override val maxWidth get() = currentMaxWidth
                override val minHeight get() = currentMinHeight
                override val minWidth get() = currentMinWidth
                override fun Modifier.align(alignment: Alignment) = currentAlign(alignment)
                override fun Modifier.matchParentSize() = currentMatchParentSize()
            }.content()
        }
    }
}

/**
 * Flexi UI dropdown menu item.
 * @see DropdownList
 * @see DropdownMenu
 * @param onClick the callback when the dropdown menu item is clicked.
 * @param modifier the [Modifier] to be applied to this dropdown menu item.
 * @param contentColor the color of the content.
 * @param activeColor the color of the active item.
 * @param contentPadding the padding of the content.
 * @param contentShape the shape of the content.
 * @param enabled whether the dropdown menu item is enabled, default is true.
 * @param actived whether the dropdown menu item is actived, default is false.
 * @param interactionSource the interaction source of the dropdown menu item.
 * @param content the content of the [DropdownMenuItem], should typically be [Icon] or [Text].
 */
@Composable
fun DropdownMenuItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentColor: Color = Color.Unspecified,
    activeColor: Color = Color.Unspecified,
    contentPadding: ComponentPadding? = null,
    contentShape: Shape? = null,
    enabled: Boolean = true,
    actived: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit
) {
    val currentColor = contentColor.orNull()
        ?: LocalDropdownMenuContentColor.current.orNull()
        ?: DropdownMenuDefaults.colors().contentColor
    val currentActiveColor = activeColor.orNull()
        ?: LocalDropdownMenuActiveColor.current.orNull()
        ?: DropdownMenuDefaults.colors().activeColor
    val currentPadding = contentPadding
        ?: LocalDropdownMenuContentPadding.current
        ?: DropdownMenuDefaults.style().contentPadding
    val currentShape = contentShape
        ?: LocalDropdownMenuContentShape.current
        ?: DropdownMenuDefaults.style().contentShape
    AreaRow(
        modifier = Modifier
            .componentState(enabled)
            .then(modifier)
            .fillMaxWidth()
            .sizeIn(
                minWidth = MenuItemMinWidth,
                maxWidth = MenuItemMaxWidth,
                minHeight = MenuItemMinHeight
            )
            .rippleClickable(
                enabled = enabled,
                role = Role.DropdownList,
                interactionSource = interactionSource,
                onClick = onClick
            ),
        colors = AreaBoxDefaults.colors(backgroundColor = if (actived) currentActiveColor else Color.Transparent),
        style = AreaBoxDefaults.style(padding = currentPadding, shape = currentShape),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CompositionLocalProvider(
            LocalIconStyle provides LocalIconStyle.current.copy(tint = currentColor),
            LocalTextStyle provides LocalTextStyle.current.copy(color = currentColor)
        ) { content() }
    }
}

@Composable
internal expect fun DropdownMenuMeasureBox(
    menuMaxHeight: (Dp) -> Unit,
    content: @Composable BoxScope.() -> Unit
)

/**
 * A scope for dropdown menu box.
 */
@Stable
interface DropdownMenuBoxScope : BoxWithConstraintsScope {
    val menuMaxHeight: Dp
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
    val transition = updateTransition(expandedStates, label = "DropdownMenu")
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
        modifier = modifier
            .width(IntrinsicSize.Max)
            .verticalScroll(scrollState),
        initializer = {
            graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
                transformOrigin = transformOriginState.value
            }
        },
        colors = AreaBoxDefaults.colors(
            backgroundColor = colors.backgroundColor,
            borderColor = colors.borderColor
        ),
        style = AreaBoxDefaults.style(
            padding = style.padding,
            shape = style.shape,
            borderWidth = style.borderWidth,
            shadowSize = style.shadowSize
        )
    ) {
        CompositionLocalProvider(
            LocalDropdownMenuContentColor provides colors.contentColor,
            LocalDropdownMenuActiveColor provides colors.activeColor,
            LocalDropdownMenuContentPadding provides style.contentPadding,
            LocalDropdownMenuContentShape provides style.contentShape
        ) { content() }
    }
}

private fun Modifier.dropdownList(
    enabled: Boolean,
    colors: DropdownListColors,
    style: DropdownListStyle,
    borderColor: Color,
    borderWidth: Dp,
    focusRequester: FocusRequester,
    interactionSource: MutableInteractionSource,
    then: Modifier
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "dropdownList"
        properties["enabled"] = enabled
        properties["colors"] = colors
        properties["style"] = style
        properties["borderColor"] = borderColor
        properties["borderWidth"] = borderWidth
    }
) {
    componentState(enabled)
        .focusRequester(focusRequester)
        .focusable(enabled, interactionSource)
        .hoverable(interactionSource, enabled)
        .clip(style.shape)
        .background(colors.backgroundColor, style.shape)
        .borderOrElse(borderWidth, borderColor, style.shape)
        .then(then)
        .padding(style.padding)
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

/**
 * Defaults of dropdown list.
 */
object DropdownListDefaults {

    /**
     * Creates a [DropdownListColors] with the default values.
     * @param endIconInactiveTint the tint of the end icon when inactive.
     * @param endIconActiveTint the tint of the end icon when active.
     * @param backgroundColor the background color.
     * @param borderInactiveColor the color of the border when inactive.
     * @param borderActiveColor the color of the border when active.
     * @return [DropdownListColors]
     */
    @Composable
    fun colors(
        endIconInactiveTint: Color = DropdownListProperties.EndIconInactiveTint.toColor(),
        endIconActiveTint: Color = DropdownListProperties.EndIconActiveTint.toColor(),
        backgroundColor: Color = DropdownListProperties.BackgroundColor,
        borderInactiveColor: Color = DropdownListProperties.BorderInactiveColor.toColor(),
        borderActiveColor: Color = DropdownListProperties.BorderActiveColor.toColor()
    ) = DropdownListColors(
        endIconInactiveTint = endIconInactiveTint,
        endIconActiveTint = endIconActiveTint,
        backgroundColor = backgroundColor,
        borderInactiveColor = borderInactiveColor,
        borderActiveColor = borderActiveColor
    )

    /**
     * Creates a [DropdownListStyle] with the default values.
     * @param padding the padding of the content.
     * @param shape the shape.
     * @param endIconSize the size of the end icon.
     * @param borderInactiveWidth the width of the border when inactive.
     * @param borderActiveWidth the width of the border when active.
     * @return [DropdownListStyle]
     */
    @Composable
    fun style(
        padding: ComponentPadding = DropdownListProperties.Padding.toPadding(),
        shape: Shape = AreaBoxDefaults.childShape(),
        endIconSize: Dp = DropdownListProperties.EndIconSize.toDp(),
        borderInactiveWidth: Dp = DropdownListProperties.BorderInactiveWidth.toDp(),
        borderActiveWidth: Dp = DropdownListProperties.BorderActiveWidth.toDp()
    ) = DropdownListStyle(
        padding = padding,
        shape = shape,
        endIconSize = endIconSize,
        borderInactiveWidth = borderInactiveWidth,
        borderActiveWidth = borderActiveWidth
    )
}

/**
 * Defaults of dropdown menu.
 */
object DropdownMenuDefaults {

    /**
     * Creates a [DropdownMenuColors] with the default values.
     * @param contentColor the color of the content.
     * @param activeColor the color of the active item.
     * @param backgroundColor the background color.
     * @param borderColor the color of the border.
     * @return [DropdownMenuColors]
     */
    @Composable
    fun colors(
        contentColor: Color = DropdownMenuProperties.ContentColor.toColor(),
        activeColor: Color = DropdownMenuProperties.ActiveColor.toColor().copy(alpha = 0.3f),
        backgroundColor: Color = DropdownMenuProperties.BackgroundColor.toColor(),
        borderColor: Color = DropdownMenuProperties.BorderColor.toColor()
    ) = DropdownMenuColors(
        contentColor = contentColor,
        activeColor = activeColor,
        backgroundColor = backgroundColor,
        borderColor = borderColor
    )

    /**
     * Creates a [DropdownMenuStyle] with the default values.
     * @param padding the menu padding.
     * @param shape the menu shape.
     * @param borderWidth the menu border width.
     * @param contentPadding the content padding.
     * @param contentShape the content shape.
     * @param shadowSize the shadow size.
     * @param inTransitionDuration the duration of the in transition.
     * @param outTransitionDuration the duration of the out transition.
     * @return [DropdownMenuStyle]
     */
    @Composable
    fun style(
        padding: ComponentPadding = DropdownMenuProperties.Padding.toPadding(),
        shape: Shape = DropdownMenuProperties.Shape.toShape(),
        borderWidth: Dp = DropdownMenuProperties.BorderWidth.toDp(),
        contentPadding: ComponentPadding = DropdownMenuProperties.ContentPadding,
        contentShape: Shape = DropdownMenuProperties.ContentShape.toShape(),
        shadowSize: Dp = DropdownMenuProperties.ShadowSize.toDp(),
        inTransitionDuration: Int = DropdownMenuProperties.InTransitionDuration,
        outTransitionDuration: Int = DropdownMenuProperties.OutTransitionDuration
    ) = DropdownMenuStyle(
        padding = padding,
        shape = shape,
        borderWidth = borderWidth,
        contentPadding = contentPadding,
        contentShape = contentShape,
        shadowSize = shadowSize,
        inTransitionDuration = inTransitionDuration,
        outTransitionDuration = outTransitionDuration
    )
}

@Stable
internal object DropdownListProperties {
    val EndIconInactiveTint = ColorsDescriptor.ThemeSecondary
    val EndIconActiveTint = ColorsDescriptor.ThemePrimary
    val BackgroundColor = Color.Transparent
    val BorderInactiveColor = ColorsDescriptor.ThemeSecondary
    val BorderActiveColor = ColorsDescriptor.ThemePrimary
    val Padding = PaddingDescriptor(SizesDescriptor.SpacingSecondary)
    val EndIconSize = SizesDescriptor.IconSizeTertiary
    val BorderInactiveWidth = SizesDescriptor.BorderSizeSecondary
    val BorderActiveWidth = SizesDescriptor.BorderSizePrimary
}

@Stable
internal object DropdownMenuProperties {
    val ContentColor = ColorsDescriptor.TextPrimary
    val ActiveColor = ColorsDescriptor.ThemePrimary
    val BackgroundColor = AreaBoxProperties.BackgroundColor
    val BorderColor = AreaBoxProperties.BorderColor
    val Padding = PaddingDescriptor(SizesDescriptor.SpacingTertiary)
    val Shape = ShapesDescriptor.Primary
    val BorderWidth = AreaBoxProperties.BorderWidth
    val ContentPadding = ComponentPadding(horizontal = 16.dp)
    val ContentShape = ShapesDescriptor.Secondary
    val ShadowSize = SizesDescriptor.ZoomSizeTertiary
    const val InTransitionDuration = 120
    const val OutTransitionDuration = 90
}

private val LocalDropdownMenuActiveColor = compositionLocalOf { Color.Unspecified }

private val LocalDropdownMenuContentColor = compositionLocalOf { Color.Unspecified }
private val LocalDropdownMenuContentPadding = compositionLocalOf<ComponentPadding?> { null }
private val LocalDropdownMenuContentShape = compositionLocalOf<Shape?> { null }

private val MenuItemMinWidth = 112.dp
private val MenuItemMaxWidth = 280.dp
private val MenuItemMinHeight = 32.dp