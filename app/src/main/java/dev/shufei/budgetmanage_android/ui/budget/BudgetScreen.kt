package dev.shufei.budgetmanage_android.ui.budget

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.shufei.budgetmanage_android.BudgetManageRoute


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun BudgetScreen(
    navController: NavController
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
                            items(100) { count ->
                                Text(
                                    text = "Item ${count + 1}",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(30.dp)
                                        .padding(20.dp, 4.dp)
                                )
                            }
                        }
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
                    Surface(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(text = "budget list")
                    }
                }
            }
        }
    }


}