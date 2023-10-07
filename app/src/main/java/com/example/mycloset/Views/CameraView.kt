package com.example.mycloset.Views

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mycloset.BarcodeModel
import com.example.mycloset.ProductViewModel
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CameraView(
    cameraController: LifecycleCameraController, // Controller for managing the camera's lifecycle
    barcodesFlow: StateFlow<BarcodeModel>, // Flow of barcode data
    torchEnabledFlow: StateFlow<Boolean>, // Flow of torch (flashlight) status
    onTorchButtonClicked: () -> Unit // Callback function for when the torch button is clicked
) {

    // Collect the latest barcode data and torch status
    val barcode: State<BarcodeModel> = barcodesFlow.collectAsState()
    val torchEnabled: State<Boolean> = torchEnabledFlow.collectAsState()
    val productViewModel = viewModel<ProductViewModel>()
    productViewModel.getInfo(barcode.value.barcode)

    // AndroidView to display the camera preview
    AndroidView(factory = { context ->
        PreviewView(context).apply {
            layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            scaleType = PreviewView.ScaleType.FILL_START
            implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            this.controller = cameraController
        }
    })

    // Column to organize UI elements vertically with space between them
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        // First Column inside the main Column
        Column(
            modifier = Modifier.weight(1f, false)
        ) {

            // Box composable to display barcode information
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.tertiary)
                    .padding(16.dp)
            ) {
                // Text displaying barcode information
                Text(
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.onTertiary,
                    text = "Barcode: ${barcode.value.barcode}\n"
                )
            }

            // Spacer to add some space between barcode info and the button
            Spacer(modifier = Modifier
                .padding(16.dp)
                .weight(1f))
        }

        // Button to toggle the torch
        Button(
            onClick = onTorchButtonClicked,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onPrimary),
            modifier = Modifier
                .padding(16.dp),
        ) {
            val text = if (torchEnabled.value) "Turn Off Flashlight" else "Turn On Flashlight"

            // Text composable inside the Button
            Text(
                text = text,
                Modifier.padding(2.dp), color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

// Preview function to visualize CameraView in Android Studio's preview panel
/*
@Composable
@Preview
fun CameraViewPreview() {
    CameraView(
        cameraController = LifecycleCameraController(LocalContext.current),
        barcodesFlow = MutableStateFlow(BarcodeModel("")),
        torchEnabledFlow = MutableStateFlow(false),
        onTorchButtonClicked = {}
    )
}*/
