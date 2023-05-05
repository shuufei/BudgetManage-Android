package dev.shufei.budgetmanage_android.ui.create_budget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun BudgetAmountField(
    modifier: Modifier = Modifier,
    value: Int? = null,
    onValueChange: (Int?) -> Unit = {}
) {
    Column(modifier) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value?.toString() ?: "",
            label = { Text(text = "予算額") },
            onValueChange = {
                onValueChange(it.toIntOrNull())
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            leadingIcon = {
                Text(text = "¥")
            },
            trailingIcon = {
                IconButton(onClick = { onValueChange(null) }) {
                    Icon(Icons.Default.Clear, "clear title")
                }
            }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            AddBudgetAmountButton(
                modifier = Modifier.weight(1f),
                label = "¥10,000",
                onClick = {
                    onValueChange((value ?: 0) + 10000)
                }
            )
            Spacer(modifier = Modifier.size(8.dp))
            AddBudgetAmountButton(
                modifier = Modifier.weight(1f),
                label = "¥5,000",
                onClick = {
                    onValueChange((value ?: 0) + 5000)
                }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            AddBudgetAmountButton(
                modifier = Modifier.weight(1f),
                label = "¥1,000",
                onClick = {
                    onValueChange((value ?: 0) + 1000)
                }
            )
            Spacer(modifier = Modifier.size(8.dp))
            AddBudgetAmountButton(
                modifier = Modifier.weight(1f),
                label = "¥500",
                onClick = {
                    onValueChange((value ?: 0) + 500)
                }
            )
        }
    }
}

@Composable
fun AddBudgetAmountButton(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit
) {
    FilledTonalButton(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraSmall,
        onClick = onClick,
    ) {
        Icon(Icons.Default.Add, "", modifier = Modifier.size(ButtonDefaults.IconSize))
        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
        Text(text = label)
    }
}