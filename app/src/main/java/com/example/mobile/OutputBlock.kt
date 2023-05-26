package com.example.mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Unspecified
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.ui.theme.DarkRed
import com.example.mobile.ui.theme.LightRed
import com.example.mobile.ui.theme.Red
import com.example.mobile.ui.theme.SFDistangGalaxy
import java.util.UUID

class OutputBlock(var variableName: String = "") {
    @Composable
    fun Output(index: UUID, blocks: MutableList<ComposeBlock>){
        val outputExpression = rememberSaveable(index) { mutableStateOf(this.variableName) }
        Card(
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 20.dp)
                //.width(300.dp)
                .height(70.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = Red),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        ) {
            Box(modifier = Modifier
                .fillMaxSize(),
                contentAlignment = Alignment.Center){
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "OUT", fontFamily = SFDistangGalaxy, modifier = Modifier
                        .padding(horizontal = 10.dp),
                        fontSize = 30.sp, color = DarkRed)
                    BasicTextField(
                        value = outputExpression.value,
                        onValueChange = {
                            outputExpression.value = it
                        },
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier
                                    .background(LightRed, RoundedCornerShape(percent = 10))
                                    .width(IntrinsicSize.Min)
                                    .defaultMinSize(minWidth = 150.dp)
                                    .height(50.dp)
                                    .wrapContentHeight()
                            ){
                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = 15.dp)
                                ) {
                                    innerTextField()
                                }
                            }
                        },
                        cursorBrush = SolidColor(Unspecified),
                        textStyle = TextStyle(
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkRed,
                            textAlign = TextAlign.Center
                        ),
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
                        colors = ButtonDefaults.buttonColors(containerColor = DarkRed, contentColor = Red)
                    ) {
                        Icon(Icons.Filled.Close,"")
                    }
                }
            }
        }
        variableName = outputExpression.toString()
    }

    fun GetData(): String{
        return variableName
    }
}