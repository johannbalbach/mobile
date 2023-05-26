package com.example.mobile

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import java.util.UUID

class ForBlock(var variableName: String = "", var condition: String = "", var iteration: String = "", var forBlocks: SnapshotStateList<ComposeBlock>) {
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
            .background(Blue, RoundedCornerShape(10.dp))
            .fillMaxWidth()
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .width(IntrinsicSize.Max),
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Blue),
                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
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
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 5.dp)
                            ) {
                                BasicTextField(
                                    value = forVariable.value,
                                    onValueChange = {
                                        variableName = it
                                        forVariable.value = variableName
                                        setVariable(index, GetData())
                                    },
                                    decorationBox = { innerTextField ->
                                        Box(
                                            modifier = Modifier
                                                .background(
                                                    LightBlue,
                                                    RoundedCornerShape(percent = 10)
                                                )
                                                .width(IntrinsicSize.Min)
                                                .defaultMinSize(minWidth = 70.dp)
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
                            }
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 5.dp)
                            ) {
                                BasicTextField(
                                    value = forCondition.value,
                                    onValueChange = {
                                        condition = it
                                        forCondition.value = condition
                                        setVariable(index, GetData())
                                    },
                                    decorationBox = { innerTextField ->
                                        Box(
                                            modifier = Modifier
                                                .background(
                                                    LightBlue,
                                                    RoundedCornerShape(percent = 10)
                                                )
                                                .width(IntrinsicSize.Min)
                                                .defaultMinSize(minWidth = 70.dp)
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
                            }
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 5.dp)
                            ) {
                                BasicTextField(
                                    value = forIteration.value,
                                    onValueChange = {
                                        iteration = it
                                        forIteration.value = iteration
                                        setVariable(index, GetData())
                                    },
                                    decorationBox = { innerTextField ->
                                        Box(
                                            modifier = Modifier
                                                .background(
                                                    LightBlue,
                                                    RoundedCornerShape(percent = 10)
                                                )
                                                .width(IntrinsicSize.Min)
                                                .defaultMinSize(minWidth = 70.dp)
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
                            }
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
                        .background(LightBlue)
                        .fillMaxSize()
                        .wrapContentHeight(),
                    contentAlignment = Alignment.Center
                ) {

                    val state = rememberReorderableLazyListState(onMove = { from, to ->
                        forBlocks.add(to.index, forBlocks.removeAt(from.index))
                    })
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(LightBlue)
                    ) {
                        forBlocks.forEach(){
                            it.compose()
                        }
                    }
                }
                Row {
                    Button(
                        onClick = {
                            val id = UUID.randomUUID()
                            blocksData.put(id, "")
                            if (forBlocks.size % 4 != 0) {
                                val variable = VariableBlock()
                                forBlocks.add(
                                    ComposeBlock(
                                        id,
                                        { variable.Variable(index = id, blocks = forBlocks) },
                                        "variable",
                                        { setVariable(id, variable.GetData()) })
                                )
                            } else {
                                val forBlock = ForBlock("", "", "", mutableStateListOf())
                                forBlocks.add(
                                    ComposeBlock(
                                        id,
                                        { forBlock.For(id, forBlocks) },
                                        "for",
                                        { setVariable(id, forBlock.GetData()) })
                                )
                            }
                            setVariable(index, GetData())
                        },
                        modifier = Modifier
                            .padding(vertical = 5.dp, horizontal = 10.dp)
                            .size(30.dp),
                        contentPadding = PaddingValues(5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DarkBlue,
                            contentColor = Blue
                        )
                    ) {
                        Icon(Icons.Filled.Add, "")
                    }
                    Button(
                        onClick = {
                            GetData()
                        },
                        modifier = Modifier
                            .padding(vertical = 5.dp, horizontal = 10.dp)
                            .size(30.dp),
                        contentPadding = PaddingValues(5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DarkBlue,
                            contentColor = Blue
                        )
                    ) {
                        Icon(Icons.Filled.Done, "")
                    }
                }
            }
        }
    }
    fun GetData(): String {
        val sb = StringBuilder()
        val content = BlockConcatenation(forBlocks)
        println(content.length.toString())
        if (variableName == ""){

        }
        else if (condition == ""){

        }
        else if(content == ""){

        }
        else if(iteration == ""){
            sb.append('(').append(variableName).append(")@(").append(condition).append(")?{(").append(content)
            sb.append('(').append(variableName).append('=').append(variableName).append("+1").append("))}")
        }
        else{
            sb.append('(').append(variableName).append(")@(").append(condition).append(")?{(").append(content).append('(').append(iteration).append("))}")
        }
        return sb.toString()
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