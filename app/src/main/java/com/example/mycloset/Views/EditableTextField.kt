package com.example.mycloset.Views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

//Composable used for keep track from the user modification

@Composable
fun EditableTextField(initialValue: String, onValueChange: (String) -> Unit,label:String) {
    var text by remember { mutableStateOf(initialValue) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant) // Colore di sfondo personalizzato per Model
    ) {
        TextField(
            value = text,
            onValueChange = {
                text = it
                onValueChange(it) // Aggiorna il valore esterno
            },
            label = { Text(label) }
        )
    }

}