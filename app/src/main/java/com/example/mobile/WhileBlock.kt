package com.example.mobile

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.example.mobile.ui.theme.LightBlue
import com.example.mobile.ui.theme.Blue
import com.example.mobile.ui.theme.ButtonSize
import com.example.mobile.ui.theme.Elevetaion
import com.example.mobile.ui.theme.RoundingSize
import com.example.mobile.ui.theme.SFDistangGalaxy
import com.example.mobile.ui.theme.TextFS
import com.example.mobile.ui.theme.TextFieldFS
import com.example.mobile.ui.theme.TextFieldHeight
import java.util.UUID

class WhileBlock(var condition: String = "", var whileBlocks: SnapshotStateList<ComposeBlock>) {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "MutableCollectionMutableState",
        "SuspiciousIndentation", "UnrememberedMutableState"
    )
    @Composable
    fun While(index: UUID, blocks: MutableList<ComposeBlock>){
        val whileCondition = rememberSaveable(index) { mutableStateOf(this.condition) }

        val skipPartiallyExpanded by remember { mutableStateOf(false) }
        val bottomSheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = skipPartiallyExpanded
        )
        var openBottomSheet by rememberSaveable { mutableStateOf(false) }
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

        Box(modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 20.dp)
            .background(Blue, RoundedCornerShape(RoundingSize.dp))
            .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .width(IntrinsicSize.Max),
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(RoundingSize.dp),
                    colors = CardDefaults.cardColors(containerColor = Blue),
                    elevation = CardDefaults.cardElevation(defaultElevation = Elevetaion),
                ) {
                    Box(
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "WHILE", fontFamily = SFDistangGalaxy, modifier = Modifier
                                    .padding(horizontal = 10.dp),
                                fontSize = TextFS, color = DarkBlue
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
                                                LightBlue,
                                                RoundedCornerShape(percent = 10)
                                            )
                                            .width(IntrinsicSize.Min)
                                            .defaultMinSize(minWidth = 150.dp)
                                            .height(TextFieldHeight)
                                            .wrapContentHeight()
                                    ) {
                                        innerTextField()
                                    }
                                },
                                textStyle = TextStyle(
                                    fontSize = TextFieldFS,
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
                                    .size(ButtonSize),
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
                        .fillMaxSize()
                        .wrapContentHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(LightBlue)
                    ) {
                        whileBlocks.forEach() {
                            it.compose()
                        }
                    }
                }
                Row {
                    Button(
                        onClick = {
                            openBottomSheet = true
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
                        Icon(Icons.Filled.Done, "")
                    }
                }
            }
        }

        if (openBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { openBottomSheet = false },
                sheetState = bottomSheetState
            ) {
                val horizontalState = rememberScrollState()
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .horizontalScroll(horizontalState)
                ) {
                    items(blocksList) { block ->
                        Box(
                            modifier = Modifier.clickable(
                                onClick = {
                                    selectedBlock.value = block
                                })
                        ) {
                            if (selectedBlock.value == block) {
                                AddBlock(whileBlocks, block)
                                openBottomSheet = false
                            }
                            block.compose()
                        }
                    }
                }
            }
        }
    }
    fun GetData():String{
        println("GETDATA CALLED")
        val sb = StringBuilder()
        val content = BlockConcatenation(whileBlocks)
        if (condition == ""){

        }
        else{
            sb.append("@(").append(condition).append(")?{(").append(content).append(")}")
        }
        return sb.toString()
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun WhilePreview() {
    val id = UUID.randomUUID()
    val blocks = mutableStateListOf<ComposeBlock>()
    val whileBlock = WhileBlock("", mutableStateListOf())
    whileBlock.While(index = id, blocks = blocks)
}