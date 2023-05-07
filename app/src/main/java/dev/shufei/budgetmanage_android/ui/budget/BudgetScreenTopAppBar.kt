package dev.shufei.budgetmanage_android.ui.budget

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetScreenTopAppBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior,
    onClickBack: () -> Unit = {}
) {
    var expandedMenu by remember { mutableStateOf(false) }
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = { onClickBack() }) {
                Icon(Icons.Default.ArrowBack, "back")
            }
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
                    text = { Text(text = "編集") },
                    onClick = {
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "削除") },
                    onClick = {
                    }
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}
