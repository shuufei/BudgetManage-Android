package dev.shufei.budgetmanage_android.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class CategoryWithBudget(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "budgetId",
        entityColumn = "id"
    )
    val budget: Budget
)
