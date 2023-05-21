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

object Red: CategoryThemeValue {
    override val id: String = "red"
    override val name: String = "red"
    override fun getColor(darkTheme: Boolean): Color {
        return Color(0xffDC4747)
    }
}

object Gray: CategoryThemeValue {
    override val id: String = "gray"
    override val name: String = "gray"
    override fun getColor(darkTheme: Boolean): Color {
        return Color(0xffa0a0a0)
    }
}

object CategoryTheme {
    val red: CategoryThemeValue = Red
    val gray: CategoryThemeValue = Gray

    fun getById(id: String): CategoryThemeValue {
        return when(id) {
            "red" -> this.red
            else -> this.gray
        }
    }
}

