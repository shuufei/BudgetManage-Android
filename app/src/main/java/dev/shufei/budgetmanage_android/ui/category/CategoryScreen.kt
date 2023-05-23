package dev.shufei.budgetmanage_android.ui.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dev.shufei.budgetmanage_android.ui.shared.compose.CustomSystemUiController
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    appScope: CoroutineScope = rememberCoroutineScope(),
    viewModel: CategoryScreenViewModel = hiltViewModel()
) {
    var selectedTabState by remember {
        mutableStateOf(0)
    }
    val categoryWithBudget by viewModel.category.collectAsStateWithLifecycle(initialValue = null)

    CustomSystemUiController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = categoryWithBudget?.category?.name ?: "カテゴリ") },
                navigationIcon = { IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, "back")
                } },
                actions = { IconButton(onClick = {  }) {
                    Icon(Icons.Default.MoreVert, "open menu")
                } },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(8.dp),
                    scrolledContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(8.dp)
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
        contentColor = MaterialTheme.colorScheme.onSurface,
        content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                TabRow(
                    selectedTabIndex = selectedTabState,
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(8.dp)
                ) {
                    Tab(
                        selected = selectedTabState == 0,
                        onClick = { selectedTabState = 0 },
                    ) {
                        Text(text = "出費", modifier = Modifier.padding(vertical = 12.dp))
                    }
                    Tab(selected = selectedTabState == 1, onClick = { selectedTabState = 1 }) {
                        Text(text = "詳細", modifier = Modifier.padding(vertical = 12.dp))
                    }
                }
                Text(text = "カテゴリ詳細")
                categoryWithBudget?.let {
                    Text(text = it.budget.title)
                    Text(text = it.category.name)
                }
            }
        }
    )
}
