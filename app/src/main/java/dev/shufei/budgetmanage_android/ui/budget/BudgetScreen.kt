package dev.shufei.budgetmanage_android.ui.budget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.shufei.budgetmanage_android.BudgetManageRoute
import dev.shufei.budgetmanage_android.ui.shared.CustomSystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialNavigationApi::class,
    ExperimentalFoundationApi::class
)
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

    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val bottomSheetNavController = rememberNavController(bottomSheetNavigator)

    val scope = rememberCoroutineScope()

    val budgets by viewModel.budgetsStream.collectAsStateWithLifecycle(initialValue = emptyList())
    val activeBudgetId by viewModel.activeBudgetIdStream.collectAsStateWithLifecycle(initialValue = null)
    val activeBudget by viewModel.activeBudgetStream.collectAsStateWithLifecycle(initialValue = null)

    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        sheetElevation = 0.dp,
        sheetBackgroundColor = Color.Transparent
    ) {
        NavHost(navController = bottomSheetNavController, "main") {
            composable(route = "main") {
                Scaffold(
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                    topBar = {
                        BudgetScreenTopAppBar(
                            scrollBehavior = scrollBehavior,
                            onClickShowBudgets = {
                                bottomSheetNavController.navigate("sheet")
                            },
                            onClickCreateBudget = {
                                navController.navigate(BudgetManageRoute.CREATE_BUDGET)
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
                    content = { paddingValues ->
                        Column(modifier = Modifier.padding(paddingValues)) {
                            Text(text = "active budget id: ${activeBudgetId ?: "null"}")
                            Text(text = activeBudget?.title ?: "untitled")
                            Text(text = activeBudget?.startDate ?: "-")
                            Text(text = activeBudget?.endDate ?: "-")
                            Text(text = activeBudget?.budgetAmount.toString())
                            Button(onClick = {
                                scope.launch {
                                    viewModel.setActiveBudgetId(budgetId = budgets.last().id)
                                }
                            }) {
                                Text(text = "set active budget id")
                            }
                        }
//                        LazyColumn(contentPadding = paddingValues) {
//                            items(budgets) { budget ->
//                                Row(horizontalArrangement = Arrangement.SpaceBetween) {
//                                    Text(text = budget.title ?: "")
//                                    Text(text = budget.startDate ?: "")
//                                }
//                            }
//                        }
                    }
                )
            }
            bottomSheet(route = "sheet") {
                Surface(
                    modifier = Modifier
                        .statusBarsPadding()
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                MaterialTheme.shapes.extraLarge.topStart,
                                MaterialTheme.shapes.extraLarge.topStart,
                                CornerSize(0.dp),
                                CornerSize(0.dp)
                            )
                        ),
                    color = MaterialTheme.colorScheme.background,
                    tonalElevation = BottomSheetDefaults.Elevation,
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(top = 16.dp, bottom = 40.dp, start = 16.dp, end = 16.dp)
                            .fillMaxWidth()
                    ) {
                        stickyHeader {
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(horizontal = 8.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(text = "予算一覧", style = MaterialTheme.typography.titleSmall)
                                    IconButton(onClick = {
                                        bottomSheetNavController.navigate("main") {
                                            popUpTo(navController.graph.findStartDestination().id)
                                        }
                                        navController.navigate(BudgetManageRoute.CREATE_BUDGET)
                                    }) {
                                        Icon(Icons.Default.Add, "add budget")
                                    }
                                }
                            }
                        }
                        itemsIndexed(budgets) { index, budget ->
                            val shape = when {
                                index == 0 && index == budgets.lastIndex -> RoundedCornerShape(
                                        MaterialTheme.shapes.small.topStart,
                                        MaterialTheme.shapes.small.topStart,
                                        MaterialTheme.shapes.small.topStart,
                                        MaterialTheme.shapes.small.topStart,
                                    )
                                index == 0 -> RoundedCornerShape(
                                        MaterialTheme.shapes.small.topStart,
                                        MaterialTheme.shapes.small.topStart,
                                        CornerSize(0.dp),
                                        CornerSize(0.dp)
                                    )
                                index == budgets.lastIndex -> RoundedCornerShape(
                                        CornerSize(0.dp),
                                        CornerSize(0.dp),
                                        MaterialTheme.shapes.small.topStart,
                                        MaterialTheme.shapes.small.topStart
                                    )
                                else -> RoundedCornerShape(0.dp)
                            }
                            BudgetListItem(
                                modifier = Modifier.clip(shape),
                                budget = budget,
                                onClickDelete = {
                                    scope.launch {
                                        viewModel.delete(budget)
                                        snackbarHostState.showSnackbar(
                                            message = "予算を削除しました",
                                            withDismissAction = true
                                        )
                                    }
                                }
                            )
                            if (budgets.lastIndex != index) {
                                Divider()
                            }
                        }
                        if (budgets.isEmpty()) {
                            item {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                ) {
                                    Text(
                                        text = "予算が登録されていません",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }


}