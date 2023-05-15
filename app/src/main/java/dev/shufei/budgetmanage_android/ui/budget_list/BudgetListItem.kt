package dev.shufei.budgetmanage_android.ui.budget_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.shufei.budgetmanage_android.data.Budget
import dev.shufei.budgetmanage_android.ui.shared.compose.DeleteConfirmDialog
import dev.shufei.budgetmanage_android.ui.shared.formatter.formatAmountOfMoney
import dev.shufei.budgetmanage_android.ui.shared.formatter.formatBudgetDuration
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun BudgetListItem(
    modifier: Modifier = Modifier,
    budget: Budget,
    onClickItem: ()  -> Unit = {},
    onClickDelete: () -> Unit = {},
    onClickEdit: () -> Unit = {},
) {
    var expandedMenu by remember { mutableStateOf(false) }
    var openDialog by remember { mutableStateOf(false) }
    ListItem(
        modifier = modifier.clickable { onClickItem() },
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
        ),
        headlineText = {
            Text(
                text = budget.title ?: "Untitled",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        supportingText = {
            val budgetAmount = formatAmountOfMoney(budget.budgetAmount)
            val duration = formatBudgetDuration(budget)
            Row() {
                Text(text = "$budgetAmount")
                if (duration.isNotEmpty()) {
                    Text(
                        text = "$duration",
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }
            }
        },
        trailingContent = {
            IconButton(onClick = { expandedMenu = true }) {
                Icon(Icons.Default.MoreVert, "open budget menu")
            }
            DropdownMenu(
                expanded = expandedMenu,
                onDismissRequest = { expandedMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text(text = "編集") },
                    leadingIcon = {
                        Icon(Icons.Default.Edit, "edit")
                    },
                    onClick = {
                        onClickEdit()
                        expandedMenu = false
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "削除") },
                    leadingIcon = {
                        Icon(Icons.Default.Delete, "delete")
                    },
                    onClick = {
                        openDialog = true
                        expandedMenu = false
                    }
                )
            }
        }
    )
    if (openDialog) {
        DeleteConfirmDialog(
            title = "予算を削除しますか？",
            description = "予算に登録された出費記録は全て削除されます。",
            onClickDelete = { onClickDelete() },
            onDismiss = { openDialog = false }
        )
    }
}