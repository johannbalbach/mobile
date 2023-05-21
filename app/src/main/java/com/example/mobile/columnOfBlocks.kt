package com.example.mobile

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Code
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.ui.theme.Orange
import com.example.mobile.ui.theme.White
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable
import java.util.UUID

data class ComposeBlock(
    val id: UUID,
    var compose: @Composable () -> Unit
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "MutableCollectionMutableState",
    "SuspiciousIndentation"
)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemList() {
    val blocks = remember { mutableStateListOf<ComposeBlock>() }

    Scaffold(
        topBar = {},
        bottomBar = {
            BottomAppBar(
                contentColor = Color(0xFF818181),
                containerColor = Color(0xFFF2F2F2)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { /* doSomething() */ },
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = White,
                            contentColor = Color(0xFF616161)
                        )
                    ) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = null,
                        )
                    }
                    FileMenu()
                }
                Row(
                    modifier = Modifier.fillMaxWidth(1f),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            Icons.Outlined.Settings,
                            contentDescription = null,
                        )
                    }
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            Icons.Rounded.Code,
                            contentDescription = null,
                        )
                    }
                    FloatingActionButton(
                        onClick = {
                            val id = UUID.randomUUID()
                            if(blocks.size % 2 == 0)
                                blocks.add(ComposeBlock(id, {Variable(id, blocks)}))
                            else
                                blocks.add(ComposeBlock(id, { Output(id, blocks) }))
                            println("variableID: $id")
                        },
                        shape = RoundedCornerShape(16.dp),
                        containerColor = Color(0xFFBF720F),
                        contentColor = White,
                        modifier = Modifier.size(40.dp),
                        elevation = FloatingActionButtonDefaults.elevation(3.dp)
                    ) {
                        Icon(Icons.Filled.Add, "Localized description")
                    }
                }
            }
            Divider(color = Color(0xFFE3E3E3))
        }
    ) {
        val state = rememberReorderableLazyListState(onMove = { from, to ->
            blocks.add(to.index, blocks.removeAt(from.index))
        })
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null
        ) {
            LazyColumn(
                state = state.listState,
                modifier = Modifier
                    .background(White)
                    .fillMaxSize()
                    .reorderable(state)
                    .detectReorderAfterLongPress(state)
            ) {
                items(items = blocks, key = { it.id }) { block ->
                    ReorderableItem(state, key = block.id) { isDragging ->
                        //val elevation = animateDpAsState(if (isDragging) 1.dp else 0.dp)
                        //val colorDrag = animateColorAsState(if (isDragging) LightGray else White)
                        val animateScale by animateFloatAsState(if (isDragging) 1.05f else 1f)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                //.background(colorDrag.value)
                                .scale(scale = animateScale)
                                //.shadow(elevation.value)
                        ) {
                            block.compose()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FileMenu() {
    var expanded by remember { mutableStateOf(false) }

    Box() {
        TextButton(onClick = { expanded = true }) {
            Text(
                text = "My Code",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 21.sp,
                color = Color.Black
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Edit") },
                onClick = { /* Handle edit! */ },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Edit,
                        contentDescription = null
                    )
                })
            DropdownMenuItem(
                text = { Text("Settings") },
                onClick = { /* Handle settings! */ },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Settings,
                        contentDescription = null
                    )
                })
            Divider()
            DropdownMenuItem(
                text = { Text("Send Feedback") },
                onClick = { /* Handle send feedback! */ },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Email,
                        contentDescription = null
                    )
                },
                trailingIcon = { Text("F11", textAlign = TextAlign.Center) })
        }
    }
}

@Preview
@Composable
fun ScaffoldPreview() {
    ItemList()
}