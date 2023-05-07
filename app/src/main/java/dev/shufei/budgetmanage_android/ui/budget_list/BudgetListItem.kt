package dev.shufei.budgetmanage_android.ui.budget_list

import androidx.compose.foundation.clickable
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
        headlineText = {
            Text(
                text = budget.title ?: "Untitled",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
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
        AlertDialog(
            onDismissRequest = { openDialog = false },
            icon = {
                   Icon(Icons.Outlined.Delete, "delete budget")
            },
            confirmButton = {
                TextButton(onClick = {
                    onClickDelete()
                    openDialog = false
                }) {
                    Text(text = "削除")
                }
            },
            dismissButton = {
                TextButton(onClick = { openDialog = false }) {
                    Text(text = "キャンセル")
                }
            },
            title = {
                Text(text = "予算を削除しますか？")
            },
            text = {
                Text(text = "予算に登録された出費記録は全て削除されます。")
            }
        )
    }
}