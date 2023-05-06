package dev.shufei.budgetmanage_android.ui.budget

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
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
    onClickDelete: () -> Unit = {},
    onClickEdit: () -> Unit = {},
) {
    var expandedMenu by remember { mutableStateOf(false) }
    ListItem(
        modifier = modifier,
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(6.dp)
        ),
        headlineText = {
            Text(
                text = budget.title ?: "Untitled",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        leadingContent = {
            if (budget.isActive) {
                Icon(Icons.Default.Check, "is active")
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
                        onClickDelete()
                        expandedMenu = false
                    }
                )
            }
        }
    )
}