package com.highcapable.flexiui.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Stable
fun Modifier.borderOrNot(border: BorderStroke, shape: Shape = RectangleShape) = border.takeIf { it.width > 0.dp }?.let { border(it, shape) } ?: this