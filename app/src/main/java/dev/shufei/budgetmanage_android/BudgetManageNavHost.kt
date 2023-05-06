package dev.shufei.budgetmanage_android

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.shufei.budgetmanage_android.ui.budget.BudgetScreen
import dev.shufei.budgetmanage_android.ui.create_budget.CreateBudgetScreen

object BudgetManageRoute {
    const val BUDGET = "Budget"
    const val CREATE_BUDGET = "CreateBudget"
}

@Composable
fun BudgetManageNavHost() {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val appScope = rememberCoroutineScope()
    NavHost(navController = navController, startDestination = BudgetManageRoute.BUDGET) {
        composable(BudgetManageRoute.BUDGET) {
            BudgetScreen(
                navController,
                snackbarHostState,
                appScope
            )
        }
        composable(BudgetManageRoute.CREATE_BUDGET) {
            CreateBudgetScreen(
                navController,
                snackbarHostState,
                appScope
            )
        }
    }
}