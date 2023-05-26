package com.example.mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontVariation.width
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.ComposeBlock
import com.example.mobile.ui.theme.Brown
import com.example.mobile.ui.theme.DarkBlue
import com.example.mobile.ui.theme.DarkBrown
import com.example.mobile.ui.theme.DarkOrange
import com.example.mobile.ui.theme.LightBlue
import com.example.mobile.ui.theme.LightBrown
import com.example.mobile.ui.theme.LightOrange
import com.example.mobile.ui.theme.Orange
import com.example.mobile.ui.theme.SFDistangGalaxy
import java.util.UUID

class ArrayPushBlock(var name: String = "", var value: String = "", var arrayBlocks: SnapshotStateList<ComposeBlock>) {
    @Composable
    fun Array(index: UUID, blocks: MutableList<ComposeBlock>){
        val arrayName = rememberSaveable(index) { mutableStateOf(this.name) }
        val arrayVariables = remember { arrayBlocks }

        Card(
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 20.dp)
                .height(70.dp)
                .wrapContentWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = Brown),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        ) {
            Box(modifier = Modifier
                .fillMaxHeight(),
                contentAlignment = Alignment.Center){
                Row(modifier = Modifier
                    .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "ARR", fontFamily = SFDistangGalaxy, modifier = Modifier
                            .padding(horizontal = 10.dp),
                        fontSize = 30.sp, color = DarkBrown
                    )
                    BasicTextField(
                        value = arrayName.value,
                        onValueChange = {
                            name = it
                            arrayName.value = name
                            setVariable(index, GetData())
                        },
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier
                                    .background(LightBrown, RoundedCornerShape(percent = 10))
                                    .width(IntrinsicSize.Min)
                                    .defaultMinSize(minWidth = 50.dp)
                                    .height(50.dp)
                                    .wrapContentHeight(),
                            ){
                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = 15.dp)
                                ) {
                                    innerTextField()
                                }
                            }
                        },
                        cursorBrush = SolidColor(Color.Transparent),
                        //, textAlign = TextAlign.Center
                        textStyle = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold, color = DarkBrown),
                        singleLine = true
                    )
                    Text(
                        text = "= [", fontFamily = SFDistangGalaxy, modifier = Modifier
                            .padding(horizontal = 10.dp),
                        fontSize = 40.sp, color = DarkBrown
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(LightBrown)
                        ) {
                            arrayVariables.forEach() {
                                it.compose()
                            }
                        }
                    }
                    Text(
                        text = "]", fontFamily = SFDistangGalaxy, modifier = Modifier
                            .padding(horizontal = 10.dp),
                        fontSize = 40.sp, color = DarkBrown
                    )
                    Button(
                        onClick = {
                            val id = UUID.randomUUID()
                            val variable = VariableBlock()
                            arrayVariables.add(ComposeBlock(id, {
                                //ArrayVariable(
                                //    index = id
                                //)
                            }, "variable", {setVariable(id, variable.GetData())}))
                        },
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .size(30.dp),
                        contentPadding = PaddingValues(5.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = LightBrown, contentColor = Brown)
                    ) {
                        Icon(Icons.Filled.Add,"")
                    }
                    Button(
                        onClick = {
                            val id = UUID.randomUUID()
                            val variable = VariableBlock()
                            if(arrayVariables.size > 0)
                                arrayVariables.removeLast()
                        },
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .size(30.dp),
                        contentPadding = PaddingValues(5.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = LightBrown, contentColor = Brown)
                    ) {
                        Icon(Icons.Filled.Remove,"")
                    }
                    Button(
                        onClick = {
                            blocks.remove(blocks.find { it.id == index })
                        },
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .size(30.dp),
                        contentPadding = PaddingValues(5.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = LightBrown, contentColor = Brown)
                    ) {
                        Icon(Icons.Filled.Close,"")
                    }
                }
            }
        }
    }
    fun GetData(): String {
        val sb = StringBuilder()
        if (name == ""){

        }
        else if (value == ""){
            sb.append('(').append(name).append('=').append(0).append(')')
        }
        else{
            sb.append('(').append(name).append('=').append(value).append(')')
        }
        return sb.toString()
    }

    private fun setVariable(name: UUID, value: String) {
        if (blocksData.contains(name)) {
            blocksData.replace(name, value)
        } else
            blocksData.put(name, value)
    }
}