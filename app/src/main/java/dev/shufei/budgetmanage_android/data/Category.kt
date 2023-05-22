package dev.shufei.budgetmanage_android.data

import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Category(
    @ColumnInfo(name = "id") @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "themeId") val themeId: String,
    @ColumnInfo(name = "budgetId") val budgetId: String
) {}

interface CategoryThemeValue {
    val id: String
    val name: String
    fun getColor(darkTheme: Boolean): Color
}

internal object Red: CategoryThemeValue {
    override val id: String = "red"
    override val name: String = "レッド"
    override fun getColor(darkTheme: Boolean): Color {
        return Color(0xffDC4747)
    }
}

internal object Blue: CategoryThemeValue {
    override val id: String = "blue"
    override val name: String = "ブルー"
    override fun getColor(darkTheme: Boolean): Color {
        return Color(0xff4584E4)
    }
}

internal object Green: CategoryThemeValue {
    override val id: String = "green"
    override val name: String = "グリーン"
    override fun getColor(darkTheme: Boolean): Color {
        return Color(0xff6DBB60)
    }
}

internal object Yellow: CategoryThemeValue {
    override val id: String = "yellow"
    override val name: String = "イエロー"
    override fun getColor(darkTheme: Boolean): Color {
        return Color(0xff6DBB60)
    }
}

internal object Gray: CategoryThemeValue {
    override val id: String = "gray"
    override val name: String = "グレー"
    override fun getColor(darkTheme: Boolean): Color {
        return Color(0xffa0a0a0)
    }
}

object CategoryTheme {
    val red: CategoryThemeValue = Red
    val green: CategoryThemeValue = Green
    val blue: CategoryThemeValue = Blue
    val yellow: CategoryThemeValue = Yellow
    val gray: CategoryThemeValue = Gray

    val default: CategoryThemeValue = gray

    val list = listOf(red, green, blue, yellow, gray)

    fun getById(id: String): CategoryThemeValue {
        return when(id) {
            "red" -> this.red
            "blue" -> this.blue
            "green" -> this.green
            "yellow" -> this.yellow
            else -> this.gray
        }
    }
}

