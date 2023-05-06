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
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialNavigationApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun BudgetScreen(
    navController: NavController,
    viewModel: BudgetScreenViewModel = hiltViewModel()
) {
    val systemUiController = rememberSystemUiController()
    val isDark = isSystemInDarkTheme()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = !isDark
        )
        systemUiController.setNavigationBarColor(
            color = Color.Transparent
        )
    }

    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)

    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val bottomSheetNavController = rememberNavController(bottomSheetNavigator)

    val scope = rememberCoroutineScope()

    val budgets by viewModel.budgetsStream.collectAsStateWithLifecycle(initialValue = emptyList())

    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        sheetElevation = 0.dp,
        sheetBackgroundColor = Color.Transparent
    ) {
        NavHost(navController = bottomSheetNavController, "main") {
            composable(route = "main") {
                Scaffold(
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
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
                        LazyColumn(contentPadding = paddingValues) {
                            items(budgets) { budget ->
                                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = budget.title ?: "")
                                    Text(text = budget.startDate ?: "")
                                }
                            }
                        }
//                        LazyColumn(contentPadding = paddingValues) {
//                            items(100) { count ->
//                                Text(
//                                    text = "Item ${count + 1}",
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .height(30.dp)
//                                        .padding(20.dp, 4.dp)
//                                )
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
                            val shape = when (index) {
                                0 -> RoundedCornerShape(
                                        MaterialTheme.shapes.small.topStart,
                                        MaterialTheme.shapes.small.topStart,
                                        CornerSize(0.dp),
                                        CornerSize(0.dp)
                                    )
                                budgets.lastIndex -> RoundedCornerShape(
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
                                    }
                                }
                            )
                            if (budgets.lastIndex != index) {
                                Divider()
                            }
                        }
                    }
                }
            }
        }
    }


}