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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Code
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobile.ui.theme.DarkOrange
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ItemList() {
    val blocks = remember { mutableStateListOf<ComposeBlock>() }

    Scaffold(
        topBar = {},
        bottomBar = {
            BottomAppBar(
                contentColor = DarkOrange,
                containerColor = Orange
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(1f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = null,
                        )
                    }
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            Icons.Rounded.Code,
                            contentDescription = null,
                        )
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = {
                val id = UUID.randomUUID()
                var color1 = DarkOrange
                if(blocks.size % 2 == 0)
                    blocks.add(ComposeBlock(id, {Variable(id, blocks)}))
                else
                    blocks.add(ComposeBlock(id, { Output(id, blocks) }))
                println("variableID: $id")
            },
            contentColor = DarkOrange,
            containerColor = Orange) {
                Icon(Icons.Filled.Add,"")
            }
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

@Preview
@Composable
fun ScaffoldPreview() {
    ItemList()
}