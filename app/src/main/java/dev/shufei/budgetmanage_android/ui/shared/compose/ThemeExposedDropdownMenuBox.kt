package dev.shufei.budgetmanage_android.ui.shared.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.shufei.budgetmanage_android.data.CategoryTheme
import dev.shufei.budgetmanage_android.data.CategoryThemeValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeExposedDropdownMenuBox(
    modifier: Modifier = Modifier,
    selectedTheme: CategoryThemeValue,
    selectTheme: (theme: CategoryThemeValue) -> Unit
) {
    var expandedThemeMenu by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expandedThemeMenu,
        onExpandedChange = { expandedThemeMenu = !expandedThemeMenu }
    ) {
        OutlinedTextField(
            modifier = modifier.menuAnchor(),
            value = selectedTheme.name,
            onValueChange = {},
            label = { Text(text = "テーマ") },
            leadingIcon = {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(
                            selectedTheme.getColor(isSystemInDarkTheme()),
                            RoundedCornerShape(3.dp)
                        )
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedThemeMenu)
            },
            readOnly = true,
        )
        ExposedDropdownMenu(
            expanded = expandedThemeMenu,
            onDismissRequest = { expandedThemeMenu = false }
        ) {
            for (theme in CategoryTheme.list) {
                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    text = { Text(text = theme.name) },
                    onClick = {
                        selectTheme(theme)
                        expandedThemeMenu = false
                    },
                    leadingIcon = {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .background(
                                    theme.getColor(isSystemInDarkTheme()),
                                    RoundedCornerShape(3.dp)
                                )
                        )
                    }
                )
            }
        }
    }
}