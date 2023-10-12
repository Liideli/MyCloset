package com.example.mycloset.components


import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.R
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import com.example.mycloset.ui.theme.fontFamilyText

@Composable
fun NormalTextComponent(value: String){
    Text(text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
        textAlign = TextAlign.Center
    )
}

@Composable
fun HeadingTextComponent(value: String){
    Text(text = value,
        modifier = Modifier.fillMaxWidth(),
        style = TextStyle(
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal
        ),
        textAlign = TextAlign.Center
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextField(lableValue: String, modifier: Modifier = Modifier, onTextSelected: (String) -> Unit, errorStatus: Boolean = false, imageVector: ImageVector) {
    var textInput by remember { mutableStateOf("") }
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = textInput,
        onValueChange = { newValue ->
            textInput = newValue
            onTextSelected(newValue)
        },
        leadingIcon = { Icon(imageVector = imageVector, contentDescription = null)},
        label = { Text(text = lableValue) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        isError = !errorStatus,


    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    labelValue: String,
    modifier: Modifier = Modifier,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean = false
) {
    val localFocusManager = LocalFocusManager.current
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = password,
        onValueChange = { newValue ->
            password = newValue
            onTextSelected(newValue)
        },
        leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
        trailingIcon = {
            val visibilityIcon = if (passwordVisible) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }
            val description = if (passwordVisible) {
                "Hide password"
            } else {
                "Show password"
            }

            IconButton(
                onClick = {
                    passwordVisible = !passwordVisible
                }
            ) {
                Icon(imageVector = visibilityIcon, contentDescription = description)
            }
        },
        label = { Text(text = labelValue) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
        singleLine = true,
        keyboardActions = KeyboardActions{
            localFocusManager.clearFocus()
        },
        isError = !errorStatus,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
    )
}


@Composable
fun ButtonComponent(value: String, onButtonClicked: () -> Unit, isEnabled: Boolean = false) {
    Button(
        onClick = { onButtonClicked.invoke() },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        contentPadding = PaddingValues(),
        enabled = isEnabled

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(48.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun  ClickableTextComponent(tryingToLogin: Boolean = true, onTextSelected: (String) -> Unit) {
    val initialText = if(tryingToLogin) "Already have an account?" else "Don't have account yet?"
    val loginText = if (tryingToLogin)"Login" else "Register"
    val space = " "
    val annotatedString = buildAnnotatedString {
        append(initialText)
        append(space)
        withStyle(style = SpanStyle(color = Color.Blue)) {
            pushStringAnnotation(tag = loginText, annotation = loginText)
            append(loginText)
        }
    }
    Column(modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp)) {

        ClickableText(modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
            style = TextStyle(
                fontSize = 21.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
                fontFamily = fontFamilyText

            ),
            text = annotatedString,
            onClick = { offset ->
                annotatedString.getStringAnnotations(offset, offset)
                    .firstOrNull()?.also { span ->
                        if (span.item == loginText) {
                            onTextSelected(span.item)
                        }
                    }
            })
    }
}

@Composable
fun DividerTextComponent(){
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Divider(modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
            thickness = 1.dp
        )
        Text(modifier = Modifier.padding(8.dp), text = "or", fontSize = 18.sp )
        Divider(modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
            thickness = 1.dp)
    }
}