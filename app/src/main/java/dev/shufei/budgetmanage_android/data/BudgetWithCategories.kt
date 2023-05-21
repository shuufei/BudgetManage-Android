package dev.shufei.budgetmanage_android.data

import androidx.room.Embedded
import androidx.room.Relation

data class BudgetWithCategories(
    @Embedded val budget: Budget,
    @Relation(
        parentColumn = "id",
        entityColumn = "budgetId"
    )
    val categories: List<Category>
)
