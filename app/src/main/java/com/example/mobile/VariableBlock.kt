package com.example.mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.ComposeBlock
import com.example.mobile.ui.theme.DarkOrange
import com.example.mobile.ui.theme.LightOrange
import com.example.mobile.ui.theme.Orange
import java.util.UUID

class VariableBlock(val name: String = "", val value: String = "") {
    @Composable
    fun Variable(index: UUID, blocks: MutableList<ComposeBlock>){
        val variableName = rememberSaveable(index) { mutableStateOf(this.name) }
        val variableValue = rememberSaveable(index) { mutableStateOf(this.value) }
        Card(
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 20.dp)
                .width(250.dp)
                .height(70.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = Orange),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        ) {
            Box(modifier = Modifier
                .fillMaxSize(),
                contentAlignment = Alignment.Center){
                Row(modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicTextField(
                        value = variableName.value,
                        onValueChange = {
                            variableName.value = it
                        },
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier
                                    .background(LightOrange, RoundedCornerShape(percent = 10))
                                    .width(50.dp)
                                    .height(50.dp)
                                    .wrapContentHeight(),
                            ){
                                innerTextField()
                            }
                        },
                        textStyle = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold, color = DarkOrange, textAlign = TextAlign.Center),
                        singleLine = true
                    )
                    Text(text = "=", fontSize = 30.sp, color = LightOrange)
                    BasicTextField(
                        value = variableValue.value,
                        onValueChange = {
                            variableValue.value = it
                        },
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier
                                    .background(LightOrange, RoundedCornerShape(percent = 10))
                                    .width(80.dp)
                                    .height(50.dp)
                                    .wrapContentHeight(),
                            ){
                                innerTextField()
                            }
                        },
                        textStyle = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold, color = DarkOrange, textAlign = TextAlign.Center),
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
                        colors = ButtonDefaults.buttonColors(containerColor = DarkOrange, contentColor = Orange)
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
}