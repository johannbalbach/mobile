package com.example.mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.ui.theme.DarkOrange
import com.example.mobile.ui.theme.LightOrange
import com.example.mobile.ui.theme.Orange

@Composable
fun Variable(){
    val fontSize = 20.dp
    val variableName = remember { mutableStateOf("a") }
    val variableValue = remember { mutableStateOf("5") }
    Card(
        modifier = Modifier
            .padding(10.dp)
            .width(170.dp)
            .height(70.dp),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(containerColor = Orange),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
    ) {
        Box(modifier = Modifier
            .fillMaxSize(),
            contentAlignment = Alignment.Center){
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = variableName.value,
                    onValueChange = {
                        variableName.value = it
                    },
                    modifier = Modifier.background(LightOrange).width(50.dp).height(50.dp).wrapContentHeight(),
                    textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = DarkOrange, textAlign = TextAlign.Center),
                    singleLine = true
                )
                Text(text = "=", fontSize = 20.sp)
                BasicTextField(
                    value = variableValue.value,
                    onValueChange = {
                        variableValue.value = it
                    },
                    modifier = Modifier.background(LightOrange).width(80.dp).height(50.dp).wrapContentHeight(),
                    textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = DarkOrange, textAlign = TextAlign.Center),
                    singleLine = true
                )
            }
        }
    }
}