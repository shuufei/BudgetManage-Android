package dev.shufei.budgetmanage_android.ui.budget

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetScreenTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onClickShowBudgets: () -> Unit,
    onClickCreateBudget: () -> Unit,
) {
    var expandedMenu by remember { mutableStateOf(false) }
    CenterAlignedTopAppBar(
        title = {
            Text(text = "予算")
        },
        actions = {
            IconButton(onClick = { expandedMenu = true }) {
                Icon(Icons.Filled.Menu, "menu")
            }
            DropdownMenu(
                expanded = expandedMenu,
                onDismissRequest = { expandedMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text(text = "新しい予算を作成") },
                    onClick = {
                        onClickCreateBudget()
                        expandedMenu = false
                    },
                    trailingIcon = {
                        Icon(Icons.Filled.Add, "create budget")
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "予算一覧を表示") },
                    onClick = {
                        onClickShowBudgets()
                        expandedMenu = false
                    }
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}
