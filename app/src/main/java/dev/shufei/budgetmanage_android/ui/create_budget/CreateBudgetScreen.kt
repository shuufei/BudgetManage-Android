package dev.shufei.budgetmanage_android.ui.create_budget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.shufei.budgetmanage_android.data.Budget
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateBudgetScreen(
    navController: NavController,
    viewModel: CreateBudgetScreenViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    var title by remember {
        mutableStateOf("")
    }
    var startDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var endDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var budgetAmount by remember {
        mutableStateOf<Int?>(null)
    }
    val enabledCreate by remember {
        derivedStateOf { title.isNotEmpty() && budgetAmount != null }
    }
    Scaffold(
        topBar = {
                 TopAppBar(
                    title = { Text(text = "新規予算") },
                     navigationIcon = {
                         IconButton(onClick = { navController.popBackStack() }) {
                             Icon(Icons.Filled.Close, "close dialog")
                         }
                     },
                     actions = {
                         Button(
                             onClick = {
                                 scope.launch {
                                    val budget = Budget(
                                        title = title,
                                        startDate = startDate.toString(),
                                        endDate = endDate.toString(),
                                        budgetAmount = budgetAmount ?: 0
                                    )
                                    viewModel.addBudget(budget)
                                    navController.popBackStack()
                                 }
                            },
                             enabled = enabledCreate
                         ) {
                             Text(text = "作成")
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