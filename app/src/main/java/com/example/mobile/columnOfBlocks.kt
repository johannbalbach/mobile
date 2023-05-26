package com.example.mobile

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.LibraryAdd
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.WbCloudy
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.ui.theme.DragScale
import com.example.mobile.ui.theme.Nunito
import kotlinx.coroutines.launch
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable
import java.lang.Integer.max
import java.util.Hashtable
import java.util.UUID

data class ComposeBlock(
    val id: UUID,
    var compose: @Composable () -> Unit,
    val blockType: String,
    val onUpdate: () -> Unit
)

val AllBlocks = mutableStateListOf<ComposeBlock>()
var blocksData = Hashtable<UUID, String>()
val console = Console(mutableListOf())
val statusField = CompilationStatus("Waiting For Code")

@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter", "MutableCollectionMutableState",
    "SuspiciousIndentation"
)
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ListOfBlocks(
    onToggleTheme: () -> Unit
) {
    val blocks = remember { AllBlocks }
    val currentState = remember { mutableStateOf("New file") }
    val mutableConsoleValue = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

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
                                modifier = Modifier
                                    .padding(start = 0.dp)
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { /* doSomething() */ },
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
                        onClick = {
                            BuildProject()
                            mutableConsoleValue.value = true
                        },
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
                    statusField.Status()
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
                            imageVector = Icons.Rounded.WbSunny,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    FloatingActionButton(
                        onClick = {
                            val id = UUID.randomUUID()
                            blocksData.put(id, "")
                            if (blocks.size % 5 == 0) {
                                val ifelseBlock =
                                    IfElseBlock("", mutableStateListOf(), mutableStateListOf())
                                blocks.add(
                                    ComposeBlock(
                                        id,
                                        { ifelseBlock.IfElse(index = id, blocks = blocks) },
                                        "ifElse",
                                        { setVariable(id, ifelseBlock.GetData()) })
                                )
                                statusField.newStatus("If-Else Block")
                            } else if (blocks.size % 5 == 1) {
                                val output = OutputBlock()
                                blocks.add(
                                    ComposeBlock(
                                        id,
                                        { output.Output(index = id, blocks = blocks) },
                                        "output",
                                        { setVariable(id, output.GetData()) })
                                )
                                statusField.newStatus("Output Block")
                            } else if (blocks.size % 5 == 2) {
                                val whileBlock = WhileBlock("", mutableStateListOf())
                                blocks.add(
                                    ComposeBlock(
                                        id,
                                        { whileBlock.While(id, blocks) },
                                        "while",
                                        { setVariable(id, whileBlock.GetData()) })
                                )
                                statusField.newStatus("While Block")
                            } else if (blocks.size % 5 == 3) {
                                val arrayBlock = ArrayBlock("", mutableStateListOf())
                                blocks.add(
                                    ComposeBlock(
                                        id,
                                        { arrayBlock.Array(id, blocks) },
                                        "array",
                                        { setVariable(id, arrayBlock.GetData()) })
                                )
                            } else {
                                val forBlock = ForBlock("", "", "", mutableStateListOf())
                                blocks.add(
                                    ComposeBlock(
                                        id,
                                        { forBlock.For(id, blocks) },
                                        "for",
                                        { setVariable(id, forBlock.GetData()) })
                                )
                                statusField.newStatus("For Block")
                            }
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
                val horizontalState = rememberScrollState()
                LazyColumn(
                    state = state.listState,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize()
                        .horizontalScroll(horizontalState)
                        .reorderable(state)
                        .detectReorderAfterLongPress(state)
                ) {
                    items(items = blocks, key = { it.id }) { block ->
                        ReorderableItem(state, key = block.id) { isDragging ->
                            val animateScale by animateFloatAsState(if (isDragging) DragScale else 1f)
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .scale(scale = animateScale)
                            ) {
                                block.compose()
                            }
                        }
                    }
                }
            }
        }
        console.ConsoleBottomSheet(mutableConsoleValue)
    }
}

@Preview
@Composable
fun ScaffoldPreview() {
    ListOfBlocks(onToggleTheme = { true })
}