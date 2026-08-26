package com.civil.shield.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.civil.shield.core.ui.theme.CivilShieldTheme
import com.civil.shield.core.ui.theme.SpacingXXSmall

/**
 * Reusable Romanian Tricolor flag accent divider (Blue, Yellow, Red).
 */
@Composable
fun TricolorFlagDivider(
    modifier: Modifier = Modifier,
    width: Dp = 240.dp,
    height: Dp = SpacingXXSmall
) {
    val appColors = CivilShieldTheme.colors

    Row(
        modifier = modifier
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(1.dp))
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .background(Color(appColors.flagBlue))
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .background(Color(appColors.flagYellow))
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .background(Color(appColors.flagRed))
        )
    }
}

@Preview(name = "Tricolor Flag Divider", showBackground = true, backgroundColor = 0xFF0A192F)
@Composable
private fun TricolorFlagDividerPreview() {
    CivilShieldTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            TricolorFlagDivider()
        }
    }
}
