package dev.shufei.budgetmanage_android.ui.category

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import dev.shufei.budgetmanage_android.data.Category
import dev.shufei.budgetmanage_android.ui.shared.compose.DeleteConfirmDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreenTopAppBar(
    category: Category?,
    onClickBack: () -> Unit,
    onClickDelete: () -> Unit,
    onClickEdit: () -> Unit
) {
    var expandedMenu by remember { mutableStateOf(false) }
    var openDialog by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = category?.name ?: "カテゴリ") },
        navigationIcon = { IconButton(onClick = { onClickBack() }) {
            Icon(Icons.Default.ArrowBack, "back")
        } },
        actions = {
            IconButton(onClick = { expandedMenu = true }) {
                Icon(Icons.Default.MoreVert, "open menu")
            }
            DropdownMenu(
                expanded = expandedMenu,
                onDismissRequest = { expandedMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text(text = "編集") },
                    leadingIcon = { Icon(Icons.Default.Edit, "edit") },
                    onClick = {
                        onClickEdit()
                        expandedMenu = false
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "削除") },
                    leadingIcon = { Icon(Icons.Default.Delete, "delete") },
                    onClick = {
                        openDialog = true
                        expandedMenu = false
                    }
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(8.dp),
            scrolledContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(8.dp)
        )
    )

    if (openDialog) {
        DeleteConfirmDialog(
            title = "カテゴリを削除しますか？",
            description = "カテゴリに紐づいた出費はすべて未分類になります。",
            onClickDelete = { onClickDelete() },
            onDismiss = { openDialog = false }
        )
    }
}