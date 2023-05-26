package com.example.mobile

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.mobile.ui.theme.Nunito

class CompilationStatus(defaultStatus: String) {
    private var mutableStatus = mutableStateOf(defaultStatus)
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
    @Composable
    fun Status() {
        PlainTooltipBox(
            tooltip = { Text(mutableStatus.value) }
        ) {
            AnimatedContent(
                targetState = mutableStatus.value,
                transitionSpec = {
                    slideInVertically { height -> height } + fadeIn() with
                            slideOutVertically { height -> -height } + fadeOut()
                }
            ) { targetCount ->
                Text(
                    text = targetCount,
                    modifier = Modifier.tooltipAnchor(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = true,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Nunito,
                    fontSize = 21.sp,
                )
            }
        }
    }

    fun newStatus(message: String) {
        this.mutableStatus.value = message
    }
}