package dev.shufei.budgetmanage_android.ui.shared.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteConfirmDialog(
    title: String,
    description: String,
    onClickDelete: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = { Icon(Icons.Default.Delete, "delete") },
        confirmButton = {
            TextButton(onClick = {
                onClickDelete()
                onDismiss()
            }) {
                Text(text = "削除")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismiss()
            }) {
                Text(text = "キャンセル")
            }
        },
        title = {
            Text(text = title)
        },
        text = {
            Text(text = description)
        }
    )
}
