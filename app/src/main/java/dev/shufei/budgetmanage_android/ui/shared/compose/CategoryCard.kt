package dev.shufei.budgetmanage_android.ui.shared.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.shufei.budgetmanage_android.data.Category
import dev.shufei.budgetmanage_android.data.CategoryTheme
import dev.shufei.budgetmanage_android.ui.shared.formatter.formatAmountOfMoney
import dev.shufei.budgetmanage_android.ui.theme.BudgetManageAndroidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryCard(
    category: Category,
    balanceAmount: Int
) {
    Card(
        onClick = {},
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(8.dp),
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = MaterialTheme.shapes.small,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "残り",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        modifier = Modifier.padding(start = 2.dp),
                        text = formatAmountOfMoney(balanceAmount),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            Box(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .height(17.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(3.dp))
                    .background(Color(0xffc2c2c2)),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth((balanceAmount.toFloat() / category.categoryBudgetAmount.toFloat()))
                        .fillMaxHeight()
                        .background(
                            CategoryTheme
                                .getById(category.themeId)
                                .getColor(
                                    isSystemInDarkTheme()
                                )
                        )
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formatAmountOfMoney(0),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = formatAmountOfMoney(category.categoryBudgetAmount),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryCardPreview() {
    BudgetManageAndroidTheme {
        CategoryCard(
            category = Category(
                name = "category 001",
                themeId = CategoryTheme.red.id,
                categoryBudgetAmount = 14000,
                budgetId = "budget001"
            ),
            balanceAmount = 12000
        )
    }
}