package com.project.myapps.ui.theme.scr.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TextFieldComposable() {
    var value by remember { mutableStateOf("") }

    OutlinedTextField(
        label = {Text("Name Input")},
        value = value, onValueChange = {
            value = it
        })
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TextFieldComposablePreview() {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text("Sign Up")
            },
        )
    }) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            TextFieldComposable()
        }
    }
}
