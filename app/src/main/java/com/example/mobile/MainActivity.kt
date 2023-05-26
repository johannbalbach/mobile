package com.example.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import com.example.mobile.ui.theme.AppTheme
import java.util.Hashtable

var table = Hashtable<String, Value>()
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isDark = mutableStateOf(false)
        fun toggleLightTheme(){
            isDark.value = !isDark.value
        }

        setContent {
            AppTheme(
                darkTheme = isDark.value
            ) {
                ListOfBlocks(onToggleTheme = {
                    toggleLightTheme()
                })
            }
        }
    }
}