package dev.shufei.budgetmanage_android

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.shufei.budgetmanage_android.ui.budget.BudgetScreen
import dev.shufei.budgetmanage_android.ui.budget_list.BudgetListScreen
import dev.shufei.budgetmanage_android.ui.category.CategoryScreen
import dev.shufei.budgetmanage_android.ui.create_budget.CreateBudgetScreen
import dev.shufei.budgetmanage_android.ui.create_category.CreateCategoryScreen
import dev.shufei.budgetmanage_android.ui.edit_budget.EditBudgetScreen

object BudgetManageScreens {
    const val BUDGET_LIST_SCREEN = "budgetList"
    const val BUDGET_SCREEN = "budget"
    const val CREATE_BUDGET_SCREEN = "createBudget"
    const val EDIT_BUDGET_SCREEN = "editBudget"
    const val CREATE_CATEGORY_SCREEN = "createCategory"
    const val CATEGORY_SCREEN = "category"
}

object BudgetManageDestinationsArgs {
    const val BUDGET_ID = "budgetId"
    const val CATEGORY_ID = "categoryId"
}

object BudgetManageRoute {
    const val BUDGET_LIST = "${BudgetManageScreens.BUDGET_LIST_SCREEN}"
    const val BUDGET = "${BudgetManageScreens.BUDGET_SCREEN}/{${BudgetManageDestinationsArgs.BUDGET_ID}}"
    const val CREATE_BUDGET = "${BudgetManageScreens.CREATE_BUDGET_SCREEN}"
    const val EDIT_BUDGET = "${BudgetManageScreens.EDIT_BUDGET_SCREEN}/{${BudgetManageDestinationsArgs.BUDGET_ID}}"
    const val CREATE_CATEGORY = "${BudgetManageScreens.CREATE_CATEGORY_SCREEN}?${BudgetManageDestinationsArgs.BUDGET_ID}={${BudgetManageDestinationsArgs.BUDGET_ID}}"
    const val CATEGORY = "${BudgetManageScreens.CATEGORY_SCREEN}/{${BudgetManageDestinationsArgs.CATEGORY_ID}}"
}

@Composable
fun BudgetManageNavHost() {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val appScope = rememberCoroutineScope()
    NavHost(navController = navController, startDestination = BudgetManageRoute.BUDGET_LIST) {
        composable(BudgetManageRoute.BUDGET_LIST) {
            BudgetListScreen(
                navController,
                snackbarHostState,
                appScope,
            )
        }
        composable(
            BudgetManageRoute.BUDGET,
            arguments = listOf(navArgument(BudgetManageDestinationsArgs.BUDGET_ID) { type = NavType.StringType })
        ) { _ ->
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
        composable(
            BudgetManageRoute.EDIT_BUDGET,
            arguments = listOf(navArgument(BudgetManageDestinationsArgs.BUDGET_ID) { type = NavType.StringType })
        ) {
            EditBudgetScreen(
                navController,
                snackbarHostState,
                appScope
            )
        }
        composable(
            BudgetManageRoute.CREATE_CATEGORY,
            arguments = listOf(navArgument(BudgetManageDestinationsArgs.BUDGET_ID) { type = NavType.StringType })
        ) {
            CreateCategoryScreen(
                navController,
                snackbarHostState,
                appScope
            )
        }
        composable(
            BudgetManageRoute.CATEGORY,
            arguments = listOf(
                navArgument(BudgetManageDestinationsArgs.CATEGORY_ID) { type = NavType.StringType }
            )
        ) {
            CategoryScreen(
                navController,
                snackbarHostState,
                appScope
            )
        }
    }
}
