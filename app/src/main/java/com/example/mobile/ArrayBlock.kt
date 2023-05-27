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
import com.example.mobile.ui.theme.ButtonSize
import com.example.mobile.ui.theme.DarkBlue
import com.example.mobile.ui.theme.DarkBrown
import com.example.mobile.ui.theme.DarkOrange
import com.example.mobile.ui.theme.Elevetaion
import com.example.mobile.ui.theme.LightBlue
import com.example.mobile.ui.theme.LightBrown
import com.example.mobile.ui.theme.LightOrange
import com.example.mobile.ui.theme.Orange
import com.example.mobile.ui.theme.RoundingSize
import com.example.mobile.ui.theme.SFDistangGalaxy
import com.example.mobile.ui.theme.TextFS
import com.example.mobile.ui.theme.TextFieldFS
import com.example.mobile.ui.theme.TextFieldHeight
import java.util.Hashtable
import java.util.UUID

class ArrayBlock(var name: String = "", var arrayBlocks: SnapshotStateList<ComposeBlock>) {
    private val members = Hashtable<UUID, String>()
    private var ID = UUID.randomUUID()
    fun setMember(id: UUID, value: String): Unit {
        if (members.contains(id)) {
            members.replace(id, value)
        } else
            members.put(id, value)
    }

    fun stringMember(nameA: String, memberV: String): String {
        val sb = StringBuilder()
        sb.append('(').append(nameA).append("#(").append(memberV).append("))")
        return sb.toString()
    }

    fun GetData(): String {
        val sb = StringBuilder()
        sb.append("(")
        var i = 0
        while (i < arrayBlocks.size) {
            if (members.containsKey(arrayBlocks[i].id)) {
                sb.append(stringMember(name, members.getValue(arrayBlocks[i].id)))
            }
            i++
        }
        sb.append(")")
        return sb.toString()
    }

    @Composable
    fun Array(index: UUID, blocks: MutableList<ComposeBlock>) {
        val arrayName = rememberSaveable(index) { mutableStateOf(this.name) }
        val arrayVariables = remember { arrayBlocks }
        ID = index
        Card(
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 20.dp)
                .height(70.dp)
                .wrapContentWidth(),
            shape = RoundedCornerShape(RoundingSize.dp),
            colors = CardDefaults.cardColors(containerColor = Brown),
            elevation = CardDefaults.cardElevation(defaultElevation = Elevetaion),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "ARR", fontFamily = SFDistangGalaxy, modifier = Modifier
                            .padding(horizontal = 10.dp),
                        fontSize = TextFS, color = DarkBrown
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
                                    .background(
                                        LightBrown,
                                        RoundedCornerShape(percent = RoundingSize)
                                    )
                                    .width(IntrinsicSize.Min)
                                    .defaultMinSize(minWidth = 50.dp)
                                    .height(TextFieldHeight)
                                    .wrapContentHeight(),
                            ) {
                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = 15.dp)
                                ) {
                                    innerTextField()
                                }
                            }
                        },
                        cursorBrush = SolidColor(Color.Transparent),
                        textStyle = TextStyle(
                            fontSize = TextFieldFS,
                            fontWeight = FontWeight.Bold,
                            color = DarkBrown
                        ),
                        singleLine = true
                    )
                    Text(
                        text = "= [", fontFamily = SFDistangGalaxy, modifier = Modifier
                            .padding(horizontal = 10.dp),
                        fontSize = 40.sp, color = DarkBrown
                    )
                    Box(
                        modifier = Modifier
                            .padding(end = 5.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier
                                //.fillMaxWidth()
                                .width(IntrinsicSize.Max)
                                .background(LightBrown)
                        ) {
                            arrayBlocks.forEach() {
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
                            members.put(id, "")
                            arrayBlocks.add(
                                ComposeBlock(
                                    id,
                                    { ArrayVariable(index = id) },
                                    "arrayVariable",
                                    {})
                            )
                            setVariable(index, GetData())
                        },
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .size(ButtonSize),
                        contentPadding = PaddingValues(5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LightBrown,
                            contentColor = Brown
                        )
                    ) {
                        Icon(Icons.Filled.Add, "")
                    }
                    Button(
                        onClick = {
                            val id = UUID.randomUUID()
                            if (arrayBlocks.size > 0)
                                arrayBlocks.removeLast()
                        },
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .size(ButtonSize),
                        contentPadding = PaddingValues(5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LightBrown,
                            contentColor = Brown
                        )
                    ) {
                        Icon(Icons.Filled.Remove, "")
                    }
                    Button(
                        onClick = {
                            blocks.remove(blocks.find { it.id == index })
                        },
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .size(ButtonSize),
                        contentPadding = PaddingValues(5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LightBrown,
                            contentColor = Brown
                        )
                    ) {
                        Icon(Icons.Filled.Close, "")
                    }
                }
            }
        }
    }

    private fun setVariable(name: UUID, value: String) {
        if (blocksData.contains(name)) {
            blocksData.replace(name, value)
        } else
            blocksData.put(name, value)
    }

    @Composable
    fun ArrayVariable(index: UUID) {
        val variableValue = rememberSaveable(index) { mutableStateOf("") }
        Box(
            modifier = Modifier
                .background(Brown)
                .padding(horizontal = 5.dp)
                //.fillMaxSize()
                .width(50.dp),
            contentAlignment = Alignment.Center
        ) {
            BasicTextField(
                value = variableValue.value,
                onValueChange = {
                    variableValue.value = it
                    setMember(index, variableValue.value)
                    setVariable(ID, GetData())
                },
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .background(LightBrown, RoundedCornerShape(percent = 10))
                            //.width(IntrinsicSize.Min)
                            //.defaultMinSize(minWidth = 80.dp)
                            .width(50.dp)
                            .height(50.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 5.dp)
                        ) {
                            innerTextField()
                        }
                    }
                },
                cursorBrush = SolidColor(Color.Unspecified),
                textStyle = TextStyle(
                    fontSize = TextFieldFS,
                    fontWeight = FontWeight.Bold,
                    color = DarkBrown,
                    textAlign = TextAlign.Center
                ),
                singleLine = true
            )
        }

    }
}

