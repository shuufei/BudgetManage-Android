package dev.shufei.budgetmanage_android.ui.shared.formatter

import dev.shufei.budgetmanage_android.data.Budget
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun formatBudgetDuration(budget: Budget) : String {
    return if (budget.startDate != null && budget.endDate != null) {
        val dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd")
        val start = LocalDate.parse(budget.startDate).format(dtf)
        val end = LocalDate.parse(budget.endDate).format(dtf)
        "$start - $end"
    } else {
        ""
    }
}
