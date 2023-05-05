package dev.shufei.budgetmanage_android

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.shufei.budgetmanage_android.ui.budget.BudgetScreen
import dev.shufei.budgetmanage_android.ui.budget.CreateBudgetScreen

object BudgetManageRoute {
    const val BUDGET = "Budget"
    const val CREATE_BUDGET = "CreateBudget"
}

@Composable
fun BudgetManageNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = BudgetManageRoute.BUDGET) {
        composable(BudgetManageRoute.BUDGET) {
            BudgetScreen()
        }
        composable(BudgetManageRoute.CREATE_BUDGET) {
            CreateBudgetScreen()
        }
    }
}