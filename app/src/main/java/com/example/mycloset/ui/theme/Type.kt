package com.example.mycloset.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.example.mycloset.R

// Set of Material typography styles to start with


val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val fontFamilyText = FontFamily(
    Font(
        googleFont = GoogleFont("Saira Condensed"),
        fontProvider = provider
    )
)

val fontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Satisfy"),
        fontProvider = provider
    )
)

// Set of Material typography styles to start with
val textType = Typography(

    bodyLarge = TextStyle(
        fontFamily = fontFamilyText,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 15.sp,
        letterSpacing = 0.5.sp
    ),

    titleLarge = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 50.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = fontFamilyText,
        fontWeight = FontWeight.Thin,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)