package com.example.mobile

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.LibraryAdd
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Code
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.ui.theme.DarkOrange
import com.example.mobile.ui.theme.Nunito
import com.example.mobile.ui.theme.Orange
import com.example.mobile.ui.theme.Red
import com.example.mobile.ui.theme.White
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable
import java.util.UUID

data class ComposeBlock(
    val id: UUID,
    var compose: @Composable () -> Unit,
    val blockType: String,
    val Data: String
)

val AllBlocks = mutableStateListOf<ComposeBlock>()

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "MutableCollectionMutableState",
    "SuspiciousIndentation"
)
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ListOfBlocks(
    onToggleTheme: () -> Unit
) {
    val blocks = remember { AllBlocks }
    val currentState = remember { mutableStateOf("New file") }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        PlainTooltipBox(
                            tooltip = { Text(currentState.value) }
                        ) {
                            Text(
                                text = currentState.value,
                                fontWeight = FontWeight.Bold,
                                fontFamily = Nunito,
                                fontSize = 21.sp,
                                //color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier
                                    .padding(start = 0.dp)
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { /* doSomething() */ },
                            //colors = IconButtonDefaults.iconButtonColors(
                            //    contentColor = MaterialTheme.colorScheme.onSurface)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Menu,
                                contentDescription = null,
                            )
                        }
                    },
                    actions = {
                        FilledTonalButton(
                            onClick = { /* doSomething() */ },
                            modifier = Modifier
                                .height(36.dp)
                                .width(112.dp)
                                .padding(end = 6.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.onSecondary
                            ),
                            shape = RoundedCornerShape(12.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.LibraryAdd,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "New",
                                fontWeight = FontWeight.Bold,
                                fontFamily = Nunito,
                                modifier = Modifier.padding(start = 6.dp)
                            )
                        }
                    },
                    colors = topAppBarColors(
                        titleContentColor = MaterialTheme.colorScheme.onSurface,
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
                //Divider(color = Color(0xFFDEE2E6))
            }
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(64.dp),
                contentColor = MaterialTheme.colorScheme.onSurface,
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FloatingActionButton(
                        onClick = { BuildProject() },
                        modifier = Modifier
                            .padding(start = 8.dp, end = 12.dp)
                            .size(40.dp),
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary,
                        shape = RoundedCornerShape(16.dp),
                        elevation = FloatingActionButtonDefaults.elevation(3.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.PlayArrow,
                            contentDescription = null,
                        )
                    }
                    Status()
                }
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { /* doSomething() */ },
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    IconButton(
                        onClick = onToggleTheme,
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Palette,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    FloatingActionButton(
                        onClick = {
                            val id = UUID.randomUUID()
                            if(blocks.size % 3 == 0) {
                                val variable = VariableBlock()
                                blocks.add(ComposeBlock(id, { variable.Variable(index = id, blocks = blocks) }, "variable", variable.GetData()))
                            }
                            else if(blocks.size % 3 == 1) {
                                val output = OutputBlock()
                                blocks.add(ComposeBlock(id, { output.Output(index = id, blocks = blocks) }, "output", output.GetData()))
                            }
                            else {
                                val forBlock = ForBlock("", "", "", mutableStateListOf())
                                blocks.add(ComposeBlock(id, { forBlock.For(id, blocks) }, "for", forBlock.GetData()))
                            }
                            println("variableID: $id")
                        },
                        shape = RoundedCornerShape(16.dp),
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.size(40.dp),
                        elevation = FloatingActionButtonDefaults.elevation(3.dp)
                    ) {
                        Icon(Icons.Filled.Add, "Localized description")
                    }
                }
            }
            //Divider(color = White)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 70.dp, bottom = 70.dp)
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
                        //.background(Red)
                        .background(MaterialTheme.colorScheme.background)
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
                    item{ Spacer(modifier = Modifier.padding(50.dp)) }
                }
            }
        }
        ConsoleBottomSheet()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Status() {
    val currentState = remember { mutableStateOf("Waiting For Code") }

    PlainTooltipBox(
        tooltip = { Text(currentState.value) }
    ) {
        Text(
            text = currentState.value,
            modifier = Modifier.tooltipAnchor(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            softWrap = true,
            fontWeight = FontWeight.Bold,
            fontFamily = Nunito,
            fontSize = 21.sp,
            //color = Color(0xFF212529)
        )
    }
}

@Preview
@Composable
fun ScaffoldPreview() {
    ListOfBlocks(onToggleTheme = { true })
}