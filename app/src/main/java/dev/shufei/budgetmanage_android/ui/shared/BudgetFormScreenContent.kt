package dev.shufei.budgetmanage_android.ui.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.shufei.budgetmanage_android.data.Budget
import dev.shufei.budgetmanage_android.ui.create_budget.BudgetAmountField
import dev.shufei.budgetmanage_android.ui.create_budget.DateField
import java.time.LocalDate

enum class Mode {
    CREATE,
    EDIT
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetFormScreenContent(
    mode: Mode,
    defaultTitle: String = "",
    defaultStartDate: LocalDate = LocalDate.now(),
    defaultEndDate: LocalDate = LocalDate.now(),
    defaultBudgetAmount: Int? = null,
    onClickBack: () -> Unit,
    done: (budget: Budget) -> Unit,
) {
    val screenTitle = when (mode) {
        Mode.CREATE -> "予算 新規作成"
        Mode.EDIT -> "予算 編集"
    }
    val doneButtonLabel = when (mode) {
        Mode.CREATE -> "作成"
        Mode.EDIT -> "更新"
    }

    var title by remember {
        mutableStateOf(defaultTitle)
    }
    var startDate by remember {
        mutableStateOf(defaultStartDate)
    }
    var endDate by remember {
        mutableStateOf(defaultEndDate)
    }
    var budgetAmount by remember {
        mutableStateOf<Int?>(defaultBudgetAmount)
    }
    val enabledCreate by remember {
        derivedStateOf { title.isNotEmpty() && budgetAmount != null }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = screenTitle ) },
                navigationIcon = {
                    IconButton(onClick = { onClickBack() }) {
                        Icon(Icons.Filled.Close, "close dialog")
                    }
                },
                actions = {
                    Button(
                        onClick = {
                            val budget = Budget(
                                title = title,
                                startDate = startDate.toString(),
                                endDate = endDate.toString(),
                                budgetAmount = budgetAmount ?: 0
                            )
                            done(budget)
                        },
                        enabled = enabledCreate
                    ) {
                        Text(text = doneButtonLabel)
                    }
                }
            )
        },
        content = {paddingValues ->
            Surface(modifier = Modifier.padding(paddingValues)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = title,
                        label = { Text(text = "タイトル") },
                        onValueChange = { title = it },
                        trailingIcon = {
                            IconButton(onClick = { title = "" }) {
                                Icon(Icons.Default.Clear, "clear title")
                            }
                        }
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp, start = 8.dp, end = 8.dp)
                    ) {
                        DateField(
                            modifier = Modifier.weight(1f),
                            label = "開始日",
                            value = startDate,
                            onValueChange = {
                                startDate = it
                            }
                        )
                        DateField(
                            modifier = Modifier.weight(1f),
                            label = "終了日",
                            value = endDate,
                            onValueChange = {
                                endDate = it
                            }
                        )
                    }

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
        }
    )
}