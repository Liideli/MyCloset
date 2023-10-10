# MyCloset

## Overview

MyCloset is an Android application built using Kotlin Compose, MLKit, and CameraX, following Material Design principles. It enables users to effortlessly organize their wardrobe by scanning clothing barcodes and retrieving detailed information from [BarcodeLookup.com](https://www.barcodelookup.com/). 

## Features

- **Barcode Scanning**: Utilizes MLKit and CameraX to scan clothing barcodes with high accuracy and speed.
- **Data Retrieval**: Fetches comprehensive information about the scanned clothing from [BarcodeLookup.com](https://www.barcodelookup.com/).
- **Organize Your Wardrobe**: Seamlessly manage and categorize your clothing items for easy access.
- **Intuitive UI**: The application boasts an intuitive user interface designed with Kotlin Compose and adhering to Material Design guidelines for an optimal user experience.
- **Offline Mode**: Allows users to save clothing information for offline access when an internet connection is not available.

## Requirements

- Android Studio
- Android device or emulator with API level 21 or higher

## Getting Started

1. Clone the repository:
   git clone https://github.com/yourusername/MyCloset.git
2. Open the project in Android Studio.
3. Get your API key from barcodelookup by creating a free test account and insert the key in the API_KEY field in the BarcodeRepository.kt file.
4. Build and run the application on your Android device or emulator.

## Usage
Launch the MyCloset app on your Android device.
Create an account.
Use the camera to scan the barcode on a clothing item.
The app will retrieve detailed information about the scanned clothing when clicking the Fetch button.

## Dependencies
- Kotlin Compose
- MLKit
- CameraX
- BarcodeLookup.com API

## Contributors
- [Roope Laine](https://github.com/Liideli)
- [Nafisul Nazrul](https://github.com/nafitus)
- [Mattia Trapletti](https://github.com/MattiaTraple)
- [Yana Krylova](https://github.com/jankry1)
