package com.example.mobile

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color.Companion.Unspecified
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.ui.theme.DarkBlue
import com.example.mobile.ui.theme.DarkOrange
import com.example.mobile.ui.theme.Green
import com.example.mobile.ui.theme.LightBlue
import com.example.mobile.ui.theme.Orange
import com.example.mobile.ui.theme.Red
import com.example.mobile.ui.theme.Blue
import com.example.mobile.ui.theme.SFDistangGalaxy
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable
import java.util.UUID

class ForBlock(val variableName: String = "", val condition: String = "", val iteration: String = "", val forBlocks: SnapshotStateList<ComposeBlock>) {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "MutableCollectionMutableState",
        "SuspiciousIndentation"
    )
    @Composable
    fun For(index: UUID, blocks: MutableList<ComposeBlock>){
        val forVariable = rememberSaveable(index) { mutableStateOf(this.variableName) }
        val forCondition = rememberSaveable(index) { mutableStateOf(this.variableName) }
        val forIteration = rememberSaveable(index) { mutableStateOf(this.variableName) }
        val blocksFor = remember { forBlocks }

        Box(modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 20.dp)
            .background(Red, RoundedCornerShape(percent = 8))
        ){
            Column(
                modifier = Modifier
                    .width(300.dp)
                //.wrapContentWidth()
                //.height(570.dp)
            ) {
                Card(
                    //modifier = Modifier
                    //    .padding(vertical = 10.dp, horizontal = 20.dp)
                    //    .width(300.dp)
                    //    .height(70.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Blue),
                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .padding(10.dp),
                        //.height(100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "FOR", fontFamily = SFDistangGalaxy, modifier = Modifier
                                    .padding(horizontal = 10.dp),
                                fontSize = 30.sp, color = DarkBlue
                            )
                            BasicTextField(
                                value = forVariable.value,
                                onValueChange = {
                                    forVariable.value = it
                                },
                                decorationBox = { innerTextField ->
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                LightBlue,
                                                RoundedCornerShape(percent = 10)
                                            )
                                            .width(50.dp)
                                            .height(50.dp)
                                            .wrapContentHeight()
                                    ) {
                                        innerTextField()
                                    }
                                },
                                textStyle = TextStyle(
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkBlue,
                                    textAlign = TextAlign.Center
                                ),
                                singleLine = true,
                                cursorBrush = SolidColor(Unspecified)
                            )
                            BasicTextField(
                                value = forCondition.value,
                                onValueChange = {
                                    forCondition.value = it
                                },
                                decorationBox = { innerTextField ->
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                LightBlue,
                                                RoundedCornerShape(percent = 10)
                                            )
                                            .width(50.dp)
                                            .height(50.dp)
                                            .wrapContentHeight()
                                    ) {
                                        innerTextField()
                                    }
                                },
                                textStyle = TextStyle(
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkBlue,
                                    textAlign = TextAlign.Center
                                ),
                                singleLine = true,
                                cursorBrush = SolidColor(Unspecified)
                            )
                            BasicTextField(
                                value = forIteration.value,
                                onValueChange = {
                                    forIteration.value = it
                                },
                                decorationBox = { innerTextField ->
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                LightBlue,
                                                RoundedCornerShape(percent = 10)
                                            )
                                            .width(50.dp)
                                            .height(50.dp)
                                            .wrapContentHeight()
                                    ) {
                                        innerTextField()
                                    }
                                },
                                textStyle = TextStyle(
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkBlue,
                                    textAlign = TextAlign.Center
                                ),
                                singleLine = true,
                                cursorBrush = SolidColor(Unspecified)
                            )
                            Button(
                                onClick = {
                                    blocks.remove(blocks.find { it.id == index })
                                },
                                modifier = Modifier
                                    .padding(horizontal = 10.dp)
                                    .size(30.dp),
                                contentPadding = PaddingValues(5.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = DarkBlue,
                                    contentColor = Blue
                                )
                            ) {
                                Icon(Icons.Filled.Close, "")
                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .background(Green)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    contentAlignment = Alignment.Center
                ) {

                    val state = rememberReorderableLazyListState(onMove = { from, to ->
                        blocksFor.add(to.index, blocksFor.removeAt(from.index))
                    })
                    //CompositionLocalProvider(
                    //    LocalOverscrollConfiguration provides null
                    //) {
                    LazyColumn(
                        state = state.listState,
                        modifier = Modifier
                            .height((blocksFor.size * 90).dp)
                            .background(Green)
                            .reorderable(state)
                            .detectReorderAfterLongPress(state)
                    ) {
                        items(items = blocksFor, key = { it.id }) { block ->
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
                Button(
                    onClick = {
                        val id = UUID.randomUUID()
                        if(blocksFor.size % 4 != 0) {
                            val variable = VariableBlock()
                            blocksFor.add(ComposeBlock(id, { variable.Variable(
                                index = id,
                                blocks = blocks
                            ) }, "variable"))
                        }
                        else {
                            val forBlock = ForBlock("", "", "", mutableStateListOf())
                            blocksFor.add(ComposeBlock(id, { forBlock.For(id, blocks) }, "for"))
                        }
                    },
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .size(30.dp),
                    contentPadding = PaddingValues(5.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkOrange, contentColor = Orange)
                ) {
                    Icon(Icons.Filled.Add,"")
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun ForPreview() {
    val id = UUID.randomUUID()
    val blocks = mutableStateListOf<ComposeBlock>()
    val forBlock = ForBlock("", "", "", mutableStateListOf())
    forBlock.For(index = id, blocks = blocks)
}