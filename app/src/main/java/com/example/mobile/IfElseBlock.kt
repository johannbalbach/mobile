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
import com.example.mobile.ui.theme.Blue
import com.example.mobile.ui.theme.DarkBlue
import com.example.mobile.ui.theme.Green
import com.example.mobile.ui.theme.DarkGreen
import com.example.mobile.ui.theme.LightGreen
import com.example.mobile.ui.theme.SFDistangGalaxy
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import java.util.UUID

class IfElseBlock(var condition: String = "", var ifBlocks: SnapshotStateList<ComposeBlock>, var elseBlocks: SnapshotStateList<ComposeBlock>) {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "MutableCollectionMutableState",
        "SuspiciousIndentation"
    )
    @Composable
    fun IfElse(index: UUID, blocks: MutableList<ComposeBlock>){
        val whileCondition = rememberSaveable(index) { mutableStateOf(this.condition) }
        val blocksInIf = remember { ifBlocks }
        val blocksInElse = remember { elseBlocks }

        Box(modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 20.dp)
            .background(Green, RoundedCornerShape(10.dp))
            .fillMaxWidth()
        ){
            Column(
                modifier = Modifier
                    .width(IntrinsicSize.Max)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Green),
                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "IF", fontFamily = SFDistangGalaxy, modifier = Modifier
                                    .padding(horizontal = 10.dp),
                                fontSize = 30.sp, color = DarkGreen
                            )
                            BasicTextField(
                                value = whileCondition.value,
                                onValueChange = {
                                    whileCondition.value = it
                                    condition = whileCondition.value
                                    setVariable(index, GetData())
                                },
                                decorationBox = { innerTextField ->
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                LightGreen,
                                                RoundedCornerShape(percent = 10)
                                            )
                                            .width(IntrinsicSize.Min)
                                            .defaultMinSize(minWidth = 150.dp)
                                            .height(50.dp)
                                            .wrapContentHeight()
                                    ) {
                                        innerTextField()
                                    }
                                },
                                textStyle = TextStyle(
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkGreen,
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
                                    containerColor = DarkGreen,
                                    contentColor = Green
                                )
                            ) {
                                Icon(Icons.Filled.Close, "")
                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            //.padding(top = 5.dp)
                            .background(LightGreen)
                            //.align(Alignment.Center)
                    ) {
                        blocksInIf.forEach(){
                            it.compose()
                        }
                    }
                }
                Button(
                    onClick = {
                        val id = UUID.randomUUID()
                        val variable = VariableBlock()
                        ifBlocks = blocksInIf
                        blocksInIf.add(ComposeBlock(id, {
                            variable.Variable(
                                index = id,
                                blocks = blocksInIf
                            )
                        }, "variable", { setVariable(id, variable.GetData()) }))
                    },
                    modifier = Modifier
                        .padding(vertical = 5.dp, horizontal = 10.dp)
                        .size(30.dp),
                    contentPadding = PaddingValues(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkGreen,
                        contentColor = Green
                    )
                ) {
                    Icon(Icons.Filled.Add, "")
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Green),
                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .padding(vertical = 18.dp)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "ELSE", fontFamily = SFDistangGalaxy, modifier = Modifier
                                    .padding(horizontal = 10.dp),
                                fontSize = 30.sp, color = DarkGreen
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    val state = rememberReorderableLazyListState(onMove = { from, to ->
                        blocksInElse.add(to.index, blocksInElse.removeAt(from.index))
                    })
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(LightGreen)
                    ) {
                        blocksInElse.forEach(){
                            it.compose()
                        }
                    }
                }
                Row {
                    Box() {
                        Button(
                            onClick = {
                                val id = UUID.randomUUID()
                                blocksData.put(id, "")
                                val variable = VariableBlock()
                                blocksInElse.add(ComposeBlock(id, {
                                    variable.Variable(
                                        index = id,
                                        blocks = blocksInElse
                                    )
                                }, "variable", { setVariable(id, variable.GetData()) }))
                            },
                            modifier = Modifier
                                .padding(vertical = 5.dp, horizontal = 10.dp)
                                .size(30.dp)
                                .align(Alignment.CenterStart),
                            contentPadding = PaddingValues(5.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DarkGreen,
                                contentColor = Green
                            )
                        ) {
                            Icon(Icons.Filled.Add, "")
                        }
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
                            containerColor = DarkGreen,
                            contentColor = Green
                        )
                    ) {
                        Icon(Icons.Filled.Done, "")
                    }
                }
            }
        }
    }
    fun GetData():String{
        console.print("GETDATA CALLED")
        val sb = StringBuilder()
        val ifContent = BlockConcatenation(ifBlocks)
        val elseContent = BlockConcatenation(elseBlocks)
        if (condition == ""){
            console.print("NO CONDITION")
        }
        else{
            console.print("OKEY ITS GOOD")
            sb.append('(').append(condition).append(")?{(").append(ifContent).append(")}:{(").append(elseContent).append(")}")
        }
        return sb.toString()
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun IfElsePreview() {
    val id = UUID.randomUUID()
    val blocks = mutableStateListOf<ComposeBlock>()
    val ifElseBlock = IfElseBlock("",  mutableStateListOf(), mutableStateListOf())
    ifElseBlock.IfElse(index = id, blocks = blocks)
}