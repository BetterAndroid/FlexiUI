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
 * This file is created by fankes on 2023/11/9.
 */
@file:Suppress("unused", "MemberVisibilityCanBePrivate", "ABSTRACT_COMPOSABLE_DEFAULT_PARAMETER_VALUE")

package com.highcapable.flexiui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.highcapable.betterandroid.compose.extension.ui.ComponentPadding
import com.highcapable.betterandroid.compose.extension.ui.componentState
import com.highcapable.betterandroid.compose.extension.ui.orNull
import com.highcapable.flexiui.ColorsDescriptor
import com.highcapable.flexiui.PaddingDescriptor
import com.highcapable.flexiui.ShapesDescriptor
import com.highcapable.flexiui.SizesDescriptor
import com.highcapable.flexiui.component.interaction.rippleClickable
import com.highcapable.flexiui.toColor
import com.highcapable.flexiui.toShape
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Colors defines for tab.
 * @see TabDefaults.colors
 */
@Immutable
data class TabColors(
    val indicatorColor: Color,
    val selectedContentColor: Color,
    val unselectedContentColor: Color
)

/**
 * Style defines for tab.
 * @see TabDefaults.style
 */
@Immutable
data class TabStyle(
    val contentPadding: ComponentPadding,
    val contentShape: Shape,
    val indicatorWidth: Dp,
    val indicatorHeight: Dp,
    val indicatorShape: Shape
)

/**
 * Flexi UI fixed tabs.
 * @see ScrollableTabRow
 * @see Tab
 * @param selectedTabIndex the selected tab index.
 * @param modifier the [Modifier] to be applied to tabs.
 * @param colors the colors of tabs, default is [TabDefaults.colors].
 * @param style the style of tabs, default is [TabDefaults.style].
 * @param indicator the indicator of the [TabRow], see [TabRowScope.TabIndicator].
 * @param tabs the tabs of the [TabRow], should typically be [Tab].
 */
@Composable
fun TabRow(
    selectedTabIndex: Int = 0,
    modifier: Modifier = Modifier,
    colors: TabColors = TabDefaults.colors(),
    style: TabStyle = TabDefaults.style(),
    indicator: @Composable TabRowScope.() -> Unit = { TabIndicator(modifier = Modifier.tabIndicatorOffset()) },
    tabs: @Composable () -> Unit
) {
    TabStyleBox(modifier, colors, style) {
        SubcomposeLayout(
            modifier = Modifier
                .fillMaxWidth()
                .selectableGroup()
        ) { constraints ->
            val maximumConstraints = Constraints()
            val tabRowWidth = constraints.maxWidth
            val tabMeasurables = subcompose(TabSlots.Tabs, tabs)
            val tabAverageMeasurables = subcompose(TabSlots.TabsAverage, tabs)
            val tabCount = tabAverageMeasurables.size
            val tabAverageWidth = (tabRowWidth / tabCount)
            val tabPlaceables = tabMeasurables.map { it.measure(maximumConstraints) }
            val tabAveragePlaceables = tabAverageMeasurables.map {
                it.measure(constraints.copy(minWidth = tabAverageWidth, maxWidth = tabAverageWidth))
            }
            val tabRowHeight = tabAveragePlaceables.maxByOrNull { it.height }?.height ?: 0
            val tabPositions = List(tabCount) { index ->
                val tabWidth = tabPlaceables[index].width - style.contentPadding.horizontal.toPx()
                TabPosition(tabAverageWidth.toDp() * index, tabAverageWidth.toDp(), tabWidth.toDp())
            }
            layout(tabRowWidth, tabRowHeight) {
                tabAveragePlaceables.forEachIndexed { index, placeable ->
                    placeable.placeRelative(x = index * tabAverageWidth, y = 0)
                }
                subcompose(TabSlots.Indicator) {
                    indicator(TabRowImpl(selectedTabIndex, colors, style, tabPositions))
                }.forEach {
                    it.measure(Constraints.fixed(tabRowWidth, tabRowHeight)).placeRelative(x = 0, y = 0)
                }
            }
        }
    }
}

/**
 * Flexi UI scrollable tabs.
 * @see TabRow
 * @see Tab
 * @param selectedTabIndex the selected tab index.
 * @param modifier the [Modifier] to be applied to tabs.
 * @param colors the colors of tabs, default is [TabDefaults.colors].
 * @param style the style of tabs, default is [TabDefaults.style].
 * @param scrollState the scroll state of tabs.
 * @param indicator the indicator of the [ScrollableTabRow], see [TabRowScope.TabIndicator].
 * @param tabs the tabs of the [ScrollableTabRow], should typically be [Tab].
 */
@Composable
fun ScrollableTabRow(
    selectedTabIndex: Int = 0,
    modifier: Modifier = Modifier,
    colors: TabColors = TabDefaults.colors(),
    style: TabStyle = TabDefaults.style(),
    scrollState: ScrollState = rememberScrollState(),
    indicator: @Composable TabRowScope.() -> Unit = { TabIndicator(modifier = Modifier.tabIndicatorOffset()) },
    tabs: @Composable () -> Unit
) {
    TabStyleBox(modifier, colors, style) {
        val scrollableTabData = rememberScrollableTabData(scrollState)
        SubcomposeLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(align = Alignment.CenterStart)
                .horizontalScroll(scrollState)
                .selectableGroup()
                .clipToBounds()
        ) { constraints ->
            val maximumConstraints = Constraints()
            val tabMeasurables = subcompose(TabSlots.Tabs, tabs)
            val tabAverageMeasurables = subcompose(TabSlots.TabsAverage, tabs)
            val tabPlaceables = tabMeasurables.map { it.measure(maximumConstraints) }
            val tabAveragePlaceables = tabAverageMeasurables.map { it.measure(constraints) }
            var layoutWidth = 0
            var layoutHeight = 0
            tabAveragePlaceables.forEach {
                layoutWidth += it.width
                layoutHeight = maxOf(layoutHeight, it.height)
            }
            layout(layoutWidth, layoutHeight) {
                var tabLeft = 0
                val tabPositions = mutableListOf<TabPosition>()
                tabAveragePlaceables.forEachIndexed { index, placeables ->
                    val tabWidth = tabPlaceables[index].width - style.contentPadding.horizontal.toPx()
                    placeables.placeRelative(x = tabLeft, y = 0)
                    tabPositions.add(TabPosition(tabLeft.toDp(), placeables.width.toDp(), tabWidth.toDp()))
                    tabLeft += placeables.width
                }
                subcompose(TabSlots.Indicator) {
                    indicator(TabRowImpl(selectedTabIndex, colors, style, tabPositions))
                }.forEach {
                    it.measure(Constraints.fixed(layoutWidth, layoutHeight)).placeRelative(x = 0, y = 0)
                }
                scrollableTabData.onLaidOut(
                    density = this@SubcomposeLayout,
                    tabPositions = tabPositions,
                    selectedTab = selectedTabIndex
                )
            }
        }
    }
}

/**
 * Flexi UI tab.
 * @see TabRow
 * @see ScrollableTabRow
 * @param selected whether this tab is selected.
 * @param onClick the callback when this tab is clicked.
 * @param modifier the [Modifier] to be applied to this tab.
 * @param selectedContentColor the selected content color.
 * @param unselectedContentColor the unselected content color.
 * @param contentPadding the content padding.
 * @param contentShape the content shape.
 * @param enabled whether this tab is enabled.
 * @param interactionSource the interaction source.
 * @param content the content of the [Tab], should typically be [Icon] or [Text].
 */
@Composable
fun Tab(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selectedContentColor: Color = Color.Unspecified,
    unselectedContentColor: Color = Color.Unspecified,
    contentPadding: ComponentPadding? = null,
    contentShape: Shape? = null,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit
) {
    val currentSelectedContentColor = selectedContentColor.orNull()
        ?: LocalTabSelectedContentColor.current.orNull() ?: TabDefaults.colors().selectedContentColor
    val currentUnselectedContentColor = unselectedContentColor.orNull()
        ?: LocalTabUnselectedContentColor.current.orNull() ?: TabDefaults.colors().unselectedContentColor
    val currentContentPadding = contentPadding ?: LocalTabContentPadding.current ?: TabDefaults.style().contentPadding
    val currentContentShape = contentShape ?: LocalTabContentShape.current ?: TabDefaults.style().contentShape
    val contentColor by animateColorAsState(if (selected) currentSelectedContentColor else currentUnselectedContentColor)
    val contentIconStyle = LocalIconStyle.current.copy(tint = contentColor)
    val contentTextStyle = LocalTextStyle.current.copy(color = contentColor)
    CompositionLocalProvider(
        LocalIconStyle provides contentIconStyle,
        LocalTextStyle provides contentTextStyle
    ) {
        Row(
            modifier = Modifier
                .componentState(enabled)
                .clip(currentContentShape)
                .then(modifier)
                .rippleClickable(
                    enabled = enabled,
                    onClick = onClick,
                    role = Role.Tab,
                    interactionSource = interactionSource
                )
                .padding(currentContentPadding),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}

@Composable
private fun TabStyleBox(
    modifier: Modifier,
    colors: TabColors,
    style: TabStyle,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        CompositionLocalProvider(
            LocalTabSelectedContentColor provides colors.selectedContentColor,
            LocalTabUnselectedContentColor provides colors.unselectedContentColor,
            LocalTabContentPadding provides style.contentPadding,
            content = content
        )
    }
}

@Composable
private fun rememberScrollableTabData(scrollState: ScrollState): ScrollableTabData {
    val coroutineScope = rememberCoroutineScope()
    return remember(scrollState, coroutineScope) { ScrollableTabData(scrollState, coroutineScope) }
}

/**
 * Represents the position of a tab.
 * @param left the left position.
 * @param width the indicator width.
 * @param tabWidth the tab width.
 */
@Immutable
data class TabPosition(val left: Dp, val width: Dp, val tabWidth: Dp) {

    /**
     * Calculates the right of the tab.
     * @return [Dp]
     */
    val right get() = left + width

    /**
     * Calculates the center of the tab.
     * @param currentWidth the current indicator width.
     */
    fun calculateCenter(currentWidth: Dp) = left + width / 2 - currentWidth / 2
}

/**
 * A scope for tab.
 */
@Stable
interface TabRowScope {

    /**
     * Tab indicator.
     * @param modifier the [Modifier] to be applied to this tab indicator.
     * @param color the color of this tab indicator.
     * @param height the height of this tab indicator.
     * @param shape the shape of this tab indicator.
     */
    @Composable
    fun TabIndicator(
        modifier: Modifier = Modifier,
        color: Color = impl.colors.indicatorColor,
        height: Dp = impl.style.indicatorHeight,
        shape: Shape = impl.style.indicatorShape
    ) {
        Box(modifier.height(height).background(color, shape))
    }

    /**
     * [Modifier] that offsets the tab indicator.
     * @receiver [Modifier]
     * @param currentTabPosition the current tab position.
     * @param indicatorWidth the indicator width.
     */
    fun Modifier.tabIndicatorOffset(
        currentTabPosition: TabPosition = impl.tabPositions[impl.selectedTabIndex],
        indicatorWidth: Dp = impl.style.indicatorWidth
    ) = composed(
        inspectorInfo = debugInspectorInfo {
            name = "tabIndicatorOffset"
            properties["currentTabPosition"] = currentTabPosition
            properties["indicatorWidth"] = indicatorWidth
        }
    ) {
        val currentWidth = indicatorWidth.orNull() ?: currentTabPosition.tabWidth
        val animatedWidh by animateDpAsState(
            targetValue = currentWidth,
            animationSpec = tween(TabIndicatorDuration, easing = FastOutSlowInEasing)
        )
        val animatedOffsetX by animateDpAsState(
            targetValue = currentTabPosition.calculateCenter(currentWidth),
            animationSpec = tween(TabIndicatorDuration, easing = FastOutSlowInEasing)
        )
        fillMaxWidth()
            .wrapContentSize(Alignment.BottomStart)
            .offset(x = animatedOffsetX)
            .width(animatedWidh)
    }

    /**
     * [Modifier] that offsets the pager tab indicator.
     * @receiver [Modifier]
     * @param pagerState the pager state.
     * @param tabPositions the tab positions.
     * @param indicatorWidth the indicator width.
     */
    fun Modifier.pagerTabIndicatorOffset(
        pagerState: PagerState,
        tabPositions: List<TabPosition> = impl.tabPositions,
        indicatorWidth: Dp = impl.style.indicatorWidth
    ) = composed(
        inspectorInfo = debugInspectorInfo {
            name = "pagerTabIndicatorOffset"
            properties["pagerState"] = pagerState
            properties["tabPositions"] = tabPositions
            properties["indicatorWidth"] = indicatorWidth
        }
    ) {
        layout { measurable, constraints ->
            // If there are no pages, nothing to show.
            if (tabPositions.isEmpty()) return@layout layout(constraints.maxWidth, 0) {}
            val currentPage = minOf(tabPositions.lastIndex, pagerState.currentPage)
            val currentTab = tabPositions[currentPage]
            val previousTab = tabPositions.getOrNull(currentPage - 1)
            val nextTab = tabPositions.getOrNull(currentPage + 1)
            val currentWidth = indicatorWidth.orNull() ?: currentTab.tabWidth
            val nextWidth = indicatorWidth.orNull() ?: nextTab?.tabWidth ?: currentWidth
            val previousWidth = indicatorWidth.orNull() ?: previousTab?.tabWidth ?: currentWidth
            val fraction = pagerState.currentPageOffsetFraction
            // Calculate the width of the indicator from the current and next / previous tab.
            val movableWidth = when {
                fraction > 0 && nextTab != null -> lerp(currentWidth, nextWidth, fraction)
                fraction < 0 && previousTab != null -> lerp(currentWidth, previousWidth, -fraction)
                else -> currentWidth
            }.roundToPx()
            // Calculate the offset X of the indicator from the current and next / previous tab.
            val movableOffsetX = when {
                fraction > 0 && nextTab != null ->
                    lerp(currentTab.calculateCenter(currentWidth), nextTab.calculateCenter(nextWidth), fraction)
                fraction < 0 && previousTab != null ->
                    lerp(currentTab.calculateCenter(currentWidth), previousTab.calculateCenter(previousWidth), -fraction)
                else -> currentTab.calculateCenter(currentWidth)
            }.roundToPx()
            val placeable = measurable.measure(
                Constraints(
                    minWidth = movableWidth,
                    maxWidth = movableWidth,
                    minHeight = 0,
                    maxHeight = constraints.maxHeight
                )
            )
            val offsetY = maxOf(constraints.minHeight - placeable.height, 0)
            val measureWidth = constraints.maxWidth
            val measureHeight = maxOf(placeable.height, constraints.minHeight)
            layout(measureWidth, measureHeight) { placeable.placeRelative(movableOffsetX, offsetY) }
        }
    }
}

@Immutable
private class TabRowImpl(
    val selectedTabIndex: Int,
    val colors: TabColors,
    val style: TabStyle,
    val tabPositions: List<TabPosition>
) : TabRowScope

@Stable
private val TabRowScope.impl get() = this as? TabRowImpl? ?: error("Could not got TabRowScope's impl.")

@Immutable
private class ScrollableTabData(private val scrollState: ScrollState, private val coroutineScope: CoroutineScope) {

    private var selectedTab: Int? = null

    fun onLaidOut(density: Density, tabPositions: List<TabPosition>, selectedTab: Int) {
        // Animate if the new tab is different from the old tab, or this is called for the first
        // time (i.e selectedTab is `null`).
        if (this.selectedTab != selectedTab) {
            this.selectedTab = selectedTab
            tabPositions.getOrNull(selectedTab)?.let {
                // Scrolls to the tab with [tabPosition], trying to place it in the center of the
                // screen or as close to the center as possible.
                val calculatedOffset = it.calculateTabOffset(density, tabPositions)
                if (scrollState.value != calculatedOffset)
                    coroutineScope.launch {
                        scrollState.animateScrollTo(
                            value = calculatedOffset,
                            animationSpec = tween(TabIndicatorDuration, easing = FastOutSlowInEasing)
                        )
                    }
            }
        }
    }

    /**
     * @return the offset required to horizontally center the tab inside this TabRow.
     * If the tab is at the start / end, and there is not enough space to fully centre the tab, this
     * will just clamp to the min / max position given the max width.
     */
    private fun TabPosition.calculateTabOffset(density: Density, tabPositions: List<TabPosition>): Int =
        with(density) {
            val totalTabRowWidth = tabPositions.last().right.roundToPx()
            val visibleWidth = totalTabRowWidth - scrollState.maxValue
            val tabOffset = left.roundToPx()
            val scrollerCenter = visibleWidth / 2
            val tabWidth = tabWidth.roundToPx()
            val centeredTabOffset = tabOffset - (scrollerCenter - tabWidth / 2)
            // How much space we have to scroll. If the visible width is <= to the total width, then
            // we have no space to scroll as everything is always visible.
            val availableSpace = (totalTabRowWidth - visibleWidth).coerceAtLeast(0)
            return centeredTabOffset.coerceIn(0, availableSpace)
        }
}

@Stable
private enum class TabSlots { Tabs, TabsAverage, Indicator }

/**
 * Defaults of tab.
 */
object TabDefaults {

    /**
     * Creates a [TabColors] with the default values.
     * @param indicatorColor the indicator color.
     * @param selectedContentColor the selected content color.
     * @param unselectedContentColor the unselected content color.
     * @return [TabColors]
     */
    @Composable
    fun colors(
        indicatorColor: Color = TabProperties.IndicatorColor.toColor(),
        selectedContentColor: Color = TabProperties.SelectedContentColor.toColor(),
        unselectedContentColor: Color = TabProperties.UnselectedContentColor.toColor()
    ) = TabColors(
        indicatorColor = indicatorColor,
        selectedContentColor = selectedContentColor,
        unselectedContentColor = unselectedContentColor
    )

    /**
     * Creates a [TabStyle] with the default values.
     * @param contentPadding the content padding.
     * @param contentShape the content shape.
     * @param indicatorWidth the indicator width.
     * @param indicatorHeight the indicator height.
     * @param indicatorShape the indicator shape.
     * @return [TabStyle]
     */
    @Composable
    fun style(
        contentPadding: ComponentPadding = TabProperties.ContentPadding.toPadding(),
        contentShape: Shape = AreaBoxDefaults.childShape(),
        indicatorWidth: Dp = TabProperties.IndicatorWidth,
        indicatorHeight: Dp = TabProperties.IndicatorHeight,
        indicatorShape: Shape = TabProperties.IndicatorShape.toShape()
    ) = TabStyle(
        contentPadding = contentPadding,
        contentShape = contentShape,
        indicatorWidth = indicatorWidth,
        indicatorHeight = indicatorHeight,
        indicatorShape = indicatorShape
    )
}

@Stable
internal object TabProperties {
    val IndicatorColor = ColorsDescriptor.ThemePrimary
    val SelectedContentColor = ColorsDescriptor.ThemePrimary
    val UnselectedContentColor = ColorsDescriptor.ThemeSecondary
    val ContentPadding = PaddingDescriptor(
        horizontal = SizesDescriptor.SpacingPrimary,
        vertical = SizesDescriptor.SpacingSecondary
    )
    val IndicatorWidth = Dp.Unspecified
    val IndicatorHeight = 3.dp
    val IndicatorShape = ShapesDescriptor.Tertiary
}

private val LocalTabSelectedContentColor = compositionLocalOf { Color.Unspecified }
private val LocalTabUnselectedContentColor = compositionLocalOf { Color.Unspecified }

private val LocalTabContentPadding = compositionLocalOf<ComponentPadding?> { null }
private val LocalTabContentShape = compositionLocalOf<Shape?> { null }

private const val TabIndicatorDuration = 250