package dev.shufei.budgetmanage_android.ui.budget

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.compose.material3.rememberSheetState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun BudgetScreen() {
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
    val navController = rememberNavController(bottomSheetNavigator)

    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberSheetState(skipHalfExpanded = true)

    val scrim = MaterialTheme.colorScheme.scrim

    LaunchedEffect(bottomSheetState.targetValue) {
        if (bottomSheetState.targetValue != SheetValue.Hidden) {
            systemUiController.setStatusBarColor(
                color = scrim.copy(alpha = 0.3F)
            )
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            BudgetScreenTopAppBar(
                scrollBehavior = scrollBehavior,
                onClickShowBudgets = {
                    openBottomSheet = !openBottomSheet
                },
                onClickCreateBudget = { /*TODO*/ }
            )
        },
        bottomBar = {
            NavigationBar() {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, "home") },
                    label = { Text(text = "Home") },
                    selected = true,
                    onClick = { /*TODO*/ }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.List, "list") },
                    label = { Text(text = "出費") },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
            }
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
            if (openBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { openBottomSheet = false },
                    sheetState = bottomSheetState,
                ) {
                    Surface(
                        modifier = Modifier
                            .statusBarsPadding()
                            .fillMaxHeight(),
                        color = MaterialTheme.colorScheme.background,
                        tonalElevation = BottomSheetDefaults.Elevation
                    ) {
                        Surface(modifier = Modifier.navigationBarsPadding()) {
                            Text(text = "budget list")
                        }
                    }
                }
            }
        }
    )
}