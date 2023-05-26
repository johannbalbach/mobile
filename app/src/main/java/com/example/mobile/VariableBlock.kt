package com.example.mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Close
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
import com.example.mobile.ui.theme.DarkOrange
import com.example.mobile.ui.theme.LightOrange
import com.example.mobile.ui.theme.Orange
import java.util.UUID

class VariableBlock(var name: String = "", var value: String = "") {
    @Composable
    fun Variable(index: UUID, blocks: MutableList<ComposeBlock>){
        var variableName by rememberSaveable(index) { mutableStateOf(this.name) }
        var variableValue by rememberSaveable(index) { mutableStateOf(this.value) }
        Card(
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 20.dp)
                //.width(250.dp)
                //.defaultMinSize(minWidth = 50.dp)
                //.requiredWidth(350.dp)
                .height(70.dp)
                .wrapContentWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = Orange),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        ) {
            Box(modifier = Modifier
                .fillMaxHeight(),
                contentAlignment = Alignment.Center){
                Row(modifier = Modifier
                    .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicTextField(
                        value = variableName,
                        onValueChange = {
                            variableName = it
                        },
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier
                                    .background(DarkOrange, RoundedCornerShape(percent = 10))
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
                        textStyle = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold, color = LightOrange,),
                        singleLine = true
                    )
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                    ) {
                        Text(text = "=", fontSize = 30.sp, color = DarkOrange)
                    }
                    BasicTextField(
                        value = variableValue,
                        onValueChange = {
                            variableValue = it
                        },
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier
                                    .background(DarkOrange, RoundedCornerShape(percent = 10))
                                    .width(IntrinsicSize.Min)
                                    .defaultMinSize(minWidth = 80.dp)
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
                        cursorBrush = SolidColor(Color.Unspecified),
                        textStyle = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold, color = LightOrange),
                        singleLine = true
                    )
                    Button(
                        onClick = {
                            blocks.remove(blocks.find { it.id == index })
                            println()
                            blocks.forEach{
                                print(it.id)
                                print(' ')
                            }
                        },
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .size(30.dp),
                        contentPadding = PaddingValues(5.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = LightOrange, contentColor = Orange)
                    ) {
                        Icon(Icons.Filled.Close,"")
                    }
                }
            }
        }
        name = variableName.toString()
        value = variableValue.toString()
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
}