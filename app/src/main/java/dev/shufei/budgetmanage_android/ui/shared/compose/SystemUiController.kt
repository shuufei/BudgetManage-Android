package dev.shufei.budgetmanage_android.ui.shared.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun CustomSystemUiController() {
    val systemUiController = rememberSystemUiController()
    val isDark = isSystemInDarkTheme()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = !isDark
        )
        systemUiController.setNavigationBarColor(
            color = Color.Transparent
        )
    }
}