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
 * This file is created by fankes on 2023/11/9.
 */
@file:Suppress("unused", "MemberVisibilityCanBePrivate")

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
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.ReadOnlyComposable
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
import com.highcapable.flexiui.LocalColors
import com.highcapable.flexiui.LocalShapes
import com.highcapable.flexiui.LocalSizes
import com.highcapable.flexiui.extension.horizontal
import com.highcapable.flexiui.extension.orElse
import com.highcapable.flexiui.extension.status
import com.highcapable.flexiui.interaction.rippleClickable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Immutable
data class TabColors(
    val indicatorColor: Color,
    val selectedContentColor: Color,
    val unselectedContentColor: Color
)

@Immutable
data class TabStyle(
    val contentPadding: PaddingValues,
    val contentShape: Shape,
    val indicatorWidth: Dp,
    val indicatorHeight: Dp,
    val indicatorShape: Shape
)

@Composable
fun TabRow(
    selectedTabIndex: Int = 0,
    modifier: Modifier = Modifier,
    colors: TabColors = Tab.colors,
    style: TabStyle = Tab.style,
    indicator: @Composable TabRow.() -> Unit = { TabIndicator(modifier = Modifier.tabIndicatorOffset()) },
    tabs: @Composable () -> Unit
) {
    TabStyleBox(modifier, colors, style) {
        SubcomposeLayout(Modifier.fillMaxWidth().selectableGroup()) { constraints ->
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
                    indicator(TabRow(selectedTabIndex, colors, style, tabPositions))
                }.forEach {
                    it.measure(Constraints.fixed(tabRowWidth, tabRowHeight)).placeRelative(x = 0, y = 0)
                }
            }
        }
    }
}

@Composable
fun ScrollableTabRow(
    selectedTabIndex: Int = 0,
    modifier: Modifier = Modifier,
    colors: TabColors = Tab.colors,
    style: TabStyle = Tab.style,
    scrollState: ScrollState = rememberScrollState(),
    indicator: @Composable TabRow.() -> Unit = { TabIndicator(modifier = Modifier.tabIndicatorOffset()) },
    tabs: @Composable () -> Unit
) {
    TabStyleBox(modifier, colors, style) {
        val scrollableTabData = rememberScrollableTabData(scrollState)
        SubcomposeLayout(
            modifier = Modifier.fillMaxWidth()
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
                    indicator(TabRow(selectedTabIndex, colors, style, tabPositions))
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

@Composable
fun Tab(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selectedContentColor: Color = Color.Unspecified,
    unselectedContentColor: Color = Color.Unspecified,
    contentPadding: PaddingValues? = null,
    contentShape: Shape? = null,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit
) {
    val currentSelectedContentColor = selectedContentColor.orElse()
        ?: LocalTabSelectedContentColor.current.orElse() ?: Tab.colors.selectedContentColor
    val currentUnselectedContentColor = unselectedContentColor.orElse()
        ?: LocalTabUnselectedContentColor.current.orElse() ?: Tab.colors.unselectedContentColor
    val currentContentPadding = contentPadding ?: LocalTabContentPadding.current ?: Tab.style.contentPadding
    val currentContentShape = contentShape ?: LocalTabContentShape.current ?: Tab.style.contentShape
    val contentColor by animateColorAsState(if (selected) currentSelectedContentColor else currentUnselectedContentColor)
    val contentStyle = LocalTextStyle.current.default(contentColor)
    CompositionLocalProvider(
        LocalIconTint provides contentColor,
        LocalTextStyle provides contentStyle
    ) {
        Row(
            modifier = Modifier.status(enabled)
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

@Immutable
data class TabPosition(val left: Dp, val width: Dp, val tabWidth: Dp) {

    val right get() = left + width

    fun calculateCenter(currentWidth: Dp) = left + width / 2 - currentWidth / 2
}

@Stable
class TabRow internal constructor(
    val selectedTabIndex: Int,
    val colors: TabColors,
    val style: TabStyle,
    val tabPositions: List<TabPosition>
) {

    @Composable
    fun TabIndicator(
        modifier: Modifier = Modifier,
        color: Color = colors.indicatorColor,
        height: Dp = style.indicatorHeight,
        shape: Shape = style.indicatorShape
    ) {
        Box(modifier.height(height).background(color, shape))
    }

    fun Modifier.tabIndicatorOffset(
        currentTabPosition: TabPosition = tabPositions[selectedTabIndex],
        indicatorWidth: Dp = style.indicatorWidth
    ) = composed(
        inspectorInfo = debugInspectorInfo {
            name = "tabIndicatorOffset"
            properties["currentTabPosition"] = currentTabPosition
            properties["indicatorWidth"] = indicatorWidth
        }
    ) {
        val currentWidth = indicatorWidth.orElse() ?: currentTabPosition.tabWidth
        val animatedWidh by animateDpAsState(
            targetValue = currentWidth,
            animationSpec = tween(DefaultTabIndicatorDuration, easing = FastOutSlowInEasing)
        )
        val animatedOffsetX by animateDpAsState(
            targetValue = currentTabPosition.calculateCenter(currentWidth),
            animationSpec = tween(DefaultTabIndicatorDuration, easing = FastOutSlowInEasing)
        )
        fillMaxWidth()
            .wrapContentSize(Alignment.BottomStart)
            .offset(x = animatedOffsetX)
            .width(animatedWidh)
    }

    fun Modifier.pagerTabIndicatorOffset(
        pagerState: PagerState,
        tabPositions: List<TabPosition> = this@TabRow.tabPositions,
        indicatorWidth: Dp = style.indicatorWidth
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
            val currentWidth = indicatorWidth.orElse() ?: currentTab.tabWidth
            val nextWidth = indicatorWidth.orElse() ?: nextTab?.tabWidth ?: currentWidth
            val previousWidth = indicatorWidth.orElse() ?: previousTab?.tabWidth ?: currentWidth
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

@Stable
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
                            animationSpec = tween(DefaultTabIndicatorDuration, easing = FastOutSlowInEasing)
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

object Tab {
    val colors: TabColors
        @Composable
        @ReadOnlyComposable
        get() = defaultTabColors()
    val style: TabStyle
        @Composable
        @ReadOnlyComposable
        get() = defaultTabStyle()
}

private val LocalTabSelectedContentColor = compositionLocalOf { Color.Unspecified }

private val LocalTabUnselectedContentColor = compositionLocalOf { Color.Unspecified }

private val LocalTabContentPadding = compositionLocalOf<PaddingValues?> { null }

private val LocalTabContentShape = compositionLocalOf<Shape?> { null }

@Composable
@ReadOnlyComposable
private fun defaultTabColors() = TabColors(
    indicatorColor = LocalColors.current.themePrimary,
    selectedContentColor = LocalColors.current.themePrimary,
    unselectedContentColor = LocalColors.current.textSecondary
)

@Composable
@ReadOnlyComposable
private fun defaultTabStyle() = TabStyle(
    contentPadding = PaddingValues(
        horizontal = LocalSizes.current.spacingPrimary,
        vertical = LocalSizes.current.spacingSecondary
    ),
    contentShape = withAreaBoxShape(),
    indicatorWidth = Dp.Unspecified,
    indicatorHeight = DefaultTabIndicatorHeight,
    indicatorShape = LocalShapes.current.tertiary
)

private const val DefaultTabIndicatorDuration = 250
private val DefaultTabIndicatorHeight = 3.dp