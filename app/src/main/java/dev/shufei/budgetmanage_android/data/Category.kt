package dev.shufei.budgetmanage_android.data

import androidx.compose.ui.graphics.Color
import java.util.*

data class Category(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val themeId: String
) {}

interface CategoryTheme {
    val id: String
    val name: String
    fun getColor(darkTheme: Boolean): Color
}

object RedCategoryTheme: CategoryTheme {
    override val id: String = "red"
    override val name: String = "red"
    override fun getColor(darkTheme: Boolean): Color {
        return Color.Red
    }
}

enum class CategoryThemeList(
    val categoryTheme: CategoryTheme
) {
    RED(RedCategoryTheme)
}
