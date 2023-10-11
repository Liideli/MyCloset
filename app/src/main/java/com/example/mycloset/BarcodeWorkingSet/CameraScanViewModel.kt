package com.example.mycloset.BarcodeWorkingSet

import androidx.lifecycle.ViewModel
import com.example.mycloset.BarcodeWorkingSet.BarcodeModel
import com.google.mlkit.vision.barcode.common.Barcode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CameraScanViewModel: ViewModel() {

    // StateFlow for broadcasting barcode data
    val barcodesFlow: StateFlow<BarcodeModel> get() = _barcodesFlow

    // Private mutable state flow for storing barcode data
    private val _barcodesFlow = MutableStateFlow(BarcodeModel(""))

    // StateFlow for broadcasting torch status
    val torchFlow: StateFlow<Boolean> get() = _torchFlow

    // Private mutable state flow for storing torch status
    private val _torchFlow = MutableStateFlow(false)

    // Function called when barcodes are detected
    fun barcodesDetected(barcodeResults: List<Barcode>) {
        // Assuming there is at least one barcode result
        val barcode = barcodeResults[0]

        // Create a BarcodeModel from the barcode data
        val barcodeModel = BarcodeModel(
            barcode = barcode.rawValue ?: ""
        )

        // Update the _barcodesFlow with the new barcode data
        _barcodesFlow.value = barcodeModel
    }

    // Function to toggle torch status
    fun updateTorchEnabled() {
        // Toggle the value of _torchFlow
        _torchFlow.value = !_torchFlow.value
    }
}
