package dev.shufei.budgetmanage_android.ui.shared.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.shufei.budgetmanage_android.data.Category
import dev.shufei.budgetmanage_android.data.CategoryTheme

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CategoryListBottomSheet(
    categories: List<Category>,
    onClickCreate: () -> Unit
) {
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
        color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
        tonalElevation = BottomSheetDefaults.Elevation,
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(top = 16.dp, bottom = 40.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth()
        ) {
            stickyHeader {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "カテゴリ一覧", style = MaterialTheme.typography.titleSmall)
                    IconButton(onClick = { onClickCreate() }) {
                        Icon(Icons.Default.Add, "add category")
                    }
                }
            }
            itemsIndexed(categories) { index, category ->
                val shape = when {
                    index == 0 && index == categories.lastIndex -> RoundedCornerShape(
                        MaterialTheme.shapes.small.topStart,
                        MaterialTheme.shapes.small.topStart,
                        MaterialTheme.shapes.small.topStart,
                        MaterialTheme.shapes.small.topStart,
                    )
                    index == 0 -> RoundedCornerShape(
                        MaterialTheme.shapes.small.topStart,
                        MaterialTheme.shapes.small.topStart,
                        CornerSize(0.dp),
                        CornerSize(0.dp)
                    )
                    index == categories.lastIndex -> RoundedCornerShape(
                        CornerSize(0.dp),
                        CornerSize(0.dp),
                        MaterialTheme.shapes.small.topStart,
                        MaterialTheme.shapes.small.topStart
                    )
                    else -> RoundedCornerShape(0.dp)
                }
                CategoryListItem(
                    modifier = Modifier.clip(shape),
                    category = category
                )
                if (categories.lastIndex != index) {
                    Divider()
                }
            }
            if (categories.isEmpty()) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Text(
                            text = "カテゴリが登録されていません",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryListItem(
    modifier: Modifier = Modifier,
    category: Category,
    onClickDelete: () -> Unit = {},
    onClickEdit: () -> Unit = {},
) {
    var expandedMenu by remember { mutableStateOf(false) }
    var openDialog by remember { mutableStateOf(false) }
    
    ListItem(
        modifier = modifier,
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(6.dp)
        ),
        headlineText = {
            Text(
                text = category.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        leadingContent = {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        CategoryTheme.getById(category.themeId).getColor(isSystemInDarkTheme()),
                        RoundedCornerShape(3.dp)
                    )
            )
        },
        trailingContent = {
            IconButton(onClick = { expandedMenu = true }) {
                // TODO: Iconを水平方向のdotにする
                Icon(Icons.Default.MoreVert, "open category item menu")
            }
            DropdownMenu(expanded = expandedMenu, onDismissRequest = { expandedMenu = false }) {
                DropdownMenuItem(
                    text = { Text(text = "編集") },
                    leadingIcon = {
                        Icon(Icons.Default.Edit, "edit")
                    },
                    onClick = {
                        onClickEdit()
                        expandedMenu = false
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "削除") },
                    leadingIcon = {
                        Icon(Icons.Default.Delete, "delete")
                    },
                    onClick = {
                        openDialog = true
                        expandedMenu = false
                    }
                )
            }
        }
    )
    if (openDialog) {
        DeleteConfirmDialog(
            title = "カテゴリを削除しますか？",
            description = "削除したカテゴリに紐づく出費はすべて未分類になります。",
            onClickDelete = { onClickDelete() },
            onDismiss = { openDialog = false }
        )
    }
}
