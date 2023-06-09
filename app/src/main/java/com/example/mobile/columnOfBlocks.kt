package com.example.mobile

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
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

var AllBlocks = mutableStateListOf<ComposeBlock>()
var blocksData = Hashtable<UUID, String>()
val console = Console(mutableListOf())
val statusField = CompilationStatus("Waiting For Code")

@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter", "MutableCollectionMutableState",
    "SuspiciousIndentation", "UnrememberedMutableState", "CoroutineCreationDuringComposition"
)
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ListOfBlocks(
    onToggleTheme: () -> Unit
) {
    val blocks = remember { AllBlocks }
    val currentState = remember { mutableStateOf("New file") }
    val mutableConsoleValue = remember { mutableStateOf(false) }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val selectedBlock = remember { mutableStateOf<ComposeBlock?>(null) }
    val uuidArray = Array(6) { UUID.randomUUID() }
    val blocksList = mutableStateListOf(
        ComposeBlock(uuidArray[0], { IfElseBlock("", mutableStateListOf(), mutableStateListOf()).IfElse(index = uuidArray[0], blocks = blocks) }, "ifElse", {setVariable(
            uuidArray[0],
            IfElseBlock("", mutableStateListOf(), mutableStateListOf()).GetData())}),
        ComposeBlock(uuidArray[1], { OutputBlock().Output(index = uuidArray[1], blocks = blocks) }, "output", {setVariable(
            uuidArray[1], OutputBlock().GetData())}),
        ComposeBlock(uuidArray[2], { WhileBlock("", mutableStateListOf()).While(uuidArray[2], blocks) }, "while", {setVariable(
            uuidArray[2], WhileBlock("", mutableStateListOf()).GetData())}),
        ComposeBlock(uuidArray[3], { ArrayBlock("", mutableStateListOf()).Array(uuidArray[3], blocks) }, "array", { setVariable(
            uuidArray[3], ArrayBlock("", mutableStateListOf()).GetData()) }),
        ComposeBlock(uuidArray[4], { VariableBlock().Variable(index = uuidArray[4], blocks = blocks) }, "variable", {setVariable(
            uuidArray[4], VariableBlock().GetData())}),
        ComposeBlock(uuidArray[5], { ForBlock("", "", "", mutableStateListOf()).For(uuidArray[5], blocks) }, "for", {setVariable(
            uuidArray[5],
            ForBlock("", "", "", mutableStateListOf()).GetData())})
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            val horizontalState = rememberScrollState()
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .horizontalScroll(horizontalState)
                    .padding(top = 70.dp, bottom = 70.dp),
            ) {
                items(blocksList) { block ->
                    Box(
                        modifier = Modifier.clickable(
                            onClick = {
                                selectedBlock.value = block
                            })
                    ) {
                        if (selectedBlock.value == block) {
                            scope.launch { drawerState.close() }
                            AddBlock(blocks, block)
                        }
                        block.compose()
                    }
                }
            }
        },
    ) {
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
                                scope.launch { drawerState.open() }
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
}
@SuppressLint("UnrememberedMutableState")
@Composable
fun AddBlock(blocksList: SnapshotStateList<ComposeBlock>, block: ComposeBlock) {
    val id = UUID.randomUUID()
    blocksData.put(id, "")
    if (block.blockType == "ifElse") {
        val ifElseBlock = IfElseBlock("", mutableStateListOf(), mutableStateListOf())
        blocksList.add(ComposeBlock(id, { ifElseBlock.IfElse(index = id, blocks = blocksList) }, "ifElse", {setVariable(id, ifElseBlock.GetData())}))
        statusField.newStatus("^ↀᴥↀ^")
    } else if (block.blockType == "output") {
        val output = OutputBlock()
        blocksList.add(ComposeBlock(id, { output.Output(index = id, blocks = blocksList) }, "output", {setVariable(id, output.GetData())}))
        statusField.newStatus("ฅ•ω•ฅ")
    } else if (block.blockType == "while") {
        val whileBlock = WhileBlock("", mutableStateListOf())
        blocksList.add(ComposeBlock(id, { whileBlock.While(id, blocksList) }, "while", {setVariable(id, whileBlock.GetData())}))
        statusField.newStatus("(=^･ｪ･^=)")
    } else if (block.blockType == "array") {
        val arrayBlock = ArrayBlock("", mutableStateListOf())
        blocksList.add(ComposeBlock(id, { arrayBlock.Array(id, blocksList) }, "array", { setVariable(id, arrayBlock.GetData()) }))
        statusField.newStatus("(=｀ェ´=)")
    } else if (block.blockType == "variable") {
        val variableBlock = VariableBlock("","")
        blocksList.add(ComposeBlock(id, { variableBlock.Variable(id, blocksList) }, "variable", { setVariable(id, variableBlock.GetData()) }))
        statusField.newStatus("(=^-ω-^=)")
    } else {
        val forBlock = ForBlock("", "", "", mutableStateListOf())
        blocksList.add(ComposeBlock(id, { forBlock.For(id, blocksList) }, "for", {setVariable(id, forBlock.GetData())}))
        statusField.newStatus("(ฅ'ω'ฅ)")
    }
}

@Preview
@Composable
fun ScaffoldPreview() {
    ListOfBlocks(onToggleTheme = { true })
}