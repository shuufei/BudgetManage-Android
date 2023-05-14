package dev.shufei.budgetmanage_android.ui.budget_list

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetListScreenTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onClickCreateBudget: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = "予算")
        },
        actions = {
            IconButton(onClick = { onClickCreateBudget() }) {
                Icon(Icons.Filled.Add, "add budget")
            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
            scrolledContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(8.dp)
        )
    )
}
