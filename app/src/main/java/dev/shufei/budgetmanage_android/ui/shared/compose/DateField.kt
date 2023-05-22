package dev.shufei.budgetmanage_android.ui.shared.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.shufei.budgetmanage_android.ui.theme.BudgetManageAndroidTheme
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateField(
    modifier: Modifier = Modifier,
    label: String,
    value: LocalDate = LocalDate.now(),
    onValueChange: (LocalDate) -> Unit = {}
) {
    val dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd")
    var openDialog by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = value.format(dtf),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.size(8.dp))
            IconButton(
                onClick = { openDialog = true },
                colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.onSurface)
            ) {
                Icon(Icons.Default.DateRange, "select date")
            }
        }
    }
    if (openDialog) {
        val datePickerState = rememberDatePickerState()
        val confirmEnabled = remember { derivedStateOf { datePickerState.selectedDateMillis != null } }
        DatePickerDialog(
            onDismissRequest = {
                openDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (datePickerState.selectedDateMillis == null) {
                            return@TextButton
                        }
                        val ldt = LocalDateTime.ofEpochSecond(datePickerState.selectedDateMillis!! / 1000, 0, ZoneOffset.UTC)
                        onValueChange(ldt.toLocalDate())
                        openDialog = false
                    },
                    enabled = confirmEnabled.value
                ) {
                    Text(text = "完了")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                    }
                ) {
                    Text(text = "キャンセル")
                }
            },
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DateFieldPreview() {
    BudgetManageAndroidTheme() {
        DateField(
            label = "開始日",
            value = LocalDate.of(2023, 1, 23),
        )
    }
}