package dev.shufei.budgetmanage_android.ui.create_category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.shufei.budgetmanage_android.data.Category
import dev.shufei.budgetmanage_android.data.CategoryTheme
import dev.shufei.budgetmanage_android.data.CategoryThemeValue
import dev.shufei.budgetmanage_android.ui.create_budget.BudgetAmountField
import dev.shufei.budgetmanage_android.ui.shared.compose.CustomSystemUiController
import dev.shufei.budgetmanage_android.ui.shared.compose.ThemeExposedDropdownMenuBox
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CreateCategoryScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    appScope: CoroutineScope = rememberCoroutineScope(),
    viewModel: CreateCategoryScreenViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    var title by remember {
        mutableStateOf("")
    }
    var selectedTheme by remember {
        mutableStateOf<CategoryThemeValue>(CategoryTheme.default)
    }
    var budgetAmount by remember {
        mutableStateOf<Int?>(null)
    }
    val enabledCreate by remember {
        derivedStateOf { title.isNotEmpty() }
    }

    CustomSystemUiController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "カテゴリ 新規作成") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
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
                                  scope.launch {
                                      val category = Category(
                                          name = title,
                                          themeId = selectedTheme.id,
                                          budgetId = viewModel.budgetId,
                                          categoryBudgetAmount = budgetAmount ?: 0
                                      )
                                      viewModel.addCategory(category)
                                      appScope.launch {
                                          snackbarHostState.showSnackbar(
                                              message = "新しいカテゴリを作成しました",
                                              withDismissAction = true
                                          )
                                      }
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