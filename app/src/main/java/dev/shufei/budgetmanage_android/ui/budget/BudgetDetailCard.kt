package dev.shufei.budgetmanage_android.ui.budget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.shufei.budgetmanage_android.data.Budget
import dev.shufei.budgetmanage_android.ui.shared.formatter.formatAmountOfMoney
import dev.shufei.budgetmanage_android.ui.shared.formatter.formatBudgetDuration
import dev.shufei.budgetmanage_android.ui.theme.BudgetManageAndroidTheme
import java.time.LocalDate

@Composable
fun BudgetProperty(
    modifier: Modifier = Modifier,
    label: String,
    value: String
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
} 

@Composable
fun BudgetDetailCard(
    budget: Budget
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(0.dp),
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 12.dp),
        ) {
            BudgetProperty(label = "予算名", value = budget.title ?: "")
            BudgetProperty(
                modifier = Modifier.padding(top = 8.dp),
                label = "期間",
                value = formatBudgetDuration(budget)
            )
            Row(modifier = Modifier.padding(top = 8.dp)) {
                BudgetProperty(
                    label = "予算額",
                    value = formatAmountOfMoney(amount = budget.budgetAmount)
                )
                BudgetProperty(
                    modifier = Modifier.padding(start = 24.dp),
                    label = "利用額",
                    value = formatAmountOfMoney(amount = 0)
                )
                BudgetProperty(
                    modifier = Modifier.padding(start = 24.dp),
                    label = "残額",
                    value = formatAmountOfMoney(amount = 0)
                )
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun BudgetDetailCardPreview() {
    BudgetManageAndroidTheme {
        BudgetDetailCard(budget = Budget(
            title = "テスト予算001",
            startDate = LocalDate.now().toString(),
            endDate = LocalDate.now().toString(),
            budgetAmount = 10000
        ))
    }
}
