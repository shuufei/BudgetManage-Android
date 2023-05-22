package dev.shufei.budgetmanage_android.ui.budget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dev.shufei.budgetmanage_android.BudgetManageRoute
import dev.shufei.budgetmanage_android.ui.shared.compose.CustomSystemUiController
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    appScope: CoroutineScope = rememberCoroutineScope(),
    viewModel: BudgetScreenViewModel = hiltViewModel()
) {
    CustomSystemUiController()

    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)

    val budget by viewModel.budgetStream.collectAsStateWithLifecycle(initialValue = null)
    val budgetWithCategory by viewModel.budgetWithCategoriesStream.collectAsStateWithLifecycle(initialValue = null)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            BudgetScreenTopAppBar(
                title = budget?.title ?: "予算",
                scrollBehavior = scrollBehavior,
                onClickBack = {
                    navController.popBackStack()
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = "出費") },
                icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                onClick = { /*TODO*/ }
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
        content = { paddingValues ->
            Column(modifier = Modifier.padding(
                paddingValues
            )) {
                Column(modifier = Modifier.padding(
                    vertical = 8.dp,
                    horizontal = 16.dp
                )) {
                    budget?.let { BudgetDetailCard(budget = it) }
                }
                Button(onClick = { navController.navigate(BudgetManageRoute.CREATE_CATEGORY) }) {
                    Text(text = "カテゴリを追加")
                }
                Text(text = "${budgetWithCategory?.categories?.count() ?: 0}")
                LazyColumn() {
                    items(budgetWithCategory?.categories ?: emptyList()) {category -> 
                        Text(text = "${category.name}")
                    }
                    items(100) {count ->
                        Text(text = "item ${count + 1}", modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                            .padding(20.dp, 4.dp))
                    }
                }
            }
        }
    )


}