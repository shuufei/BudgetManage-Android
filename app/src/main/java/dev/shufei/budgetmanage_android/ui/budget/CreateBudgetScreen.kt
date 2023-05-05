package dev.shufei.budgetmanage_android.ui.budget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.shufei.budgetmanage_android.data.Budget
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateBudgetScreen(
    navController: NavController,
    viewModel: CreateBudgetScreenViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
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
                         Button(onClick = {
                             scope.launch {
                                val budget = Budget(
                                    title = "2023年4月",
                                    startDate = Date().toString(),
                                    endDate = Date().toString(),
                                )
                                viewModel.addBudget(budget)
                                navController.popBackStack()
                             }
                         }) {
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
                        value = "2023年5月",
                        label = { Text(text = "タイトル") },
                        onValueChange = {}
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp)
                    ) {
                        /* TODO: text fieldでなく、text buttonにする? */
                        OutlinedTextField(
                            modifier = Modifier.weight(1f),
                            value = "2023/4/25",
                            label = { Text(text = "開始日") },
                            trailingIcon = {
                                IconButton(onClick = { /*TODO*/ }) {
                                    Icon(Icons.Default.DateRange, "select date")
                                }
                            },
                            onValueChange = {}
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedTextField(
                            modifier = Modifier.weight(1f),
                            value = "2023/5/24",
                            label = { Text(text = "終了日") },
                            trailingIcon = {
                                IconButton(onClick = { /*TODO*/ }) {
                                    Icon(Icons.Default.DateRange, "select date")
                                }
                            },
                            onValueChange = {}
                        )
                    }

                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp)) {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = "¥15000",
                            label = { Text(text = "予算額") },
                            onValueChange = {},
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp)
                        ) {
                            FilledTonalButton(
                                modifier = Modifier.weight(1f),
                                shape = MaterialTheme.shapes.extraSmall,
                                onClick = { /*TODO*/ },
                            ) {
                                Icon(Icons.Default.Add, "", modifier = Modifier.size(ButtonDefaults.IconSize))
                                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                                Text(text = "¥10,000")
                            }
                            Spacer(modifier = Modifier.size(8.dp))
                            FilledTonalButton(
                                modifier = Modifier.weight(1f),
                                shape = MaterialTheme.shapes.extraSmall,
                                onClick = { /*TODO*/ },
                            ) {
                                Icon(Icons.Default.Add, "", modifier = Modifier.size(ButtonDefaults.IconSize))
                                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                                Text(text = "¥5,000")
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            FilledTonalButton(
                                modifier = Modifier.weight(1f),
                                shape = MaterialTheme.shapes.extraSmall,
                                onClick = { /*TODO*/ },
                            ) {
                                Icon(Icons.Default.Add, "", modifier = Modifier.size(ButtonDefaults.IconSize))
                                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                                Text(text = "¥1,000")
                            }
                            Spacer(modifier = Modifier.size(8.dp))
                            FilledTonalButton(
                                modifier = Modifier.weight(1f),
                                shape = MaterialTheme.shapes.extraSmall,
                                onClick = { /*TODO*/ },
                            ) {
                                Icon(Icons.Default.Add, "", modifier = Modifier.size(ButtonDefaults.IconSize))
                                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                                Text(text = "¥500")
                            }
                        }
                    }
                }
            }
        }
    )
}