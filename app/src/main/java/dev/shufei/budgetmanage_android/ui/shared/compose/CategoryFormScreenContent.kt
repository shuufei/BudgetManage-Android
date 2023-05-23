package dev.shufei.budgetmanage_android.ui.shared.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.shufei.budgetmanage_android.data.Category
import dev.shufei.budgetmanage_android.data.CategoryTheme
import dev.shufei.budgetmanage_android.ui.create_budget.BudgetAmountField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryFormScreenContent(
    mode: Mode,
    budgetId: String,
    category: Category? = null,
    onClickBack: () -> Unit,
    done: (category: Category) -> Unit,
) {
    val screenTitle = when (mode) {
        Mode.CREATE -> "カテゴリ 新規作成"
        Mode.EDIT -> "カテゴリ 編集"
    }
    val doneButtonLabel = when (mode) {
        Mode.CREATE -> "作成"
        Mode.EDIT -> "更新"
    }

    var title by remember {
        mutableStateOf(category?.name ?: "")
    }
    var selectedTheme by remember {
        mutableStateOf(category?.let { CategoryTheme.getById(it.themeId) } ?: CategoryTheme.default)
    }
    var budgetAmount by remember {
        mutableStateOf<Int?>(category?.categoryBudgetAmount)
    }
    val enabledCreate by remember {
        derivedStateOf { title.isNotEmpty() }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = screenTitle) },
                navigationIcon = {
                    IconButton(onClick = { onClickBack() }) {
                        Icon(Icons.Default.Close, "close dialog")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
                    scrolledContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(8.dp)
                ),
                actions = {
                    Button(
                        onClick = {
                            val newCategory = category?.copy(
                                name = title,
                                themeId = selectedTheme.id,
                                categoryBudgetAmount = budgetAmount ?: 0
                            ) ?: Category(
                                name = title,
                                themeId = selectedTheme.id,
                                budgetId = budgetId,
                                categoryBudgetAmount = budgetAmount ?: 0
                            )
                            done(newCategory)
                        },
                        enabled = enabledCreate
                    ) {
                        Text(text = doneButtonLabel)
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                    ,
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(text = "タイトル") },
                    trailingIcon = {
                        IconButton(onClick = { title = "" }) {
                            Icon(Icons.Default.Clear, "clear title")
                        }
                    },
                    singleLine = true
                )
                ThemeExposedDropdownMenuBox(
                    modifier = Modifier.padding(top = 16.dp),
                    selectedTheme = selectedTheme,
                    selectTheme = { selectedTheme = it }
                )
                BudgetAmountField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                    value = budgetAmount,
                    onValueChange = {
                        budgetAmount = it
                    }
                )
            }
        }
    )
}