package dev.shufei.budgetmanage_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import dev.shufei.budgetmanage_android.data.Budget
import dev.shufei.budgetmanage_android.ui.theme.BudgetManageAndroidTheme
import dev.shufei.budgetmanage_android.view.budget.BudgetScreen
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* StatusBarやNavigationBarの裏側にも描画する */
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            BudgetManageAndroidTheme {
                BudgetScreen()
//                SampleRoom()
            }
        }
    }
}

@Composable
fun SampleRoom(
    viewModel: AppViewModel = hiltViewModel()
) {
    val budgetsState by viewModel.uiState.collectAsStateWithLifecycle()

    Column() {
        Row {
            Text(text = "budget: ${budgetsState.budgets.count()}")
        }
        Row() {
            Button(
                onClick = {
                    val budget = Budget(
                        title = UUID.randomUUID().toString(),
                        startDate = Date().toString(),
                        endDate = Date().toString()
                    )
                    viewModel.addBudget(budget)
                    println("-- insert")
                },
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary
                )
            ) {
                Text(text = "Insert")
            }
            Button(onClick = { /*TODO*/ }, ) {
                Text(text = "Delete")
            }
        }
        Column {
            budgetsState.budgets.forEach { budget ->
                Text(text = "${budget.title}")
            }
        }
    }
}
