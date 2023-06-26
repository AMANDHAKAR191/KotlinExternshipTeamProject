//package com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse
//
//import android.content.Context
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//
//@Composable
//fun MyApp(context: Context) {
//    var upiId by remember { mutableStateOf("") }
//    var merchantName by remember { mutableStateOf("") }
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        QrCodeScanner(
//            onQRCodeDetected = { detectedUpiId, detectedMerchantName ->
//                upiId = detectedUpiId
//                merchantName = detectedMerchantName
//            },
//            modifier = Modifier.fillMaxSize(),
//            mContext = context
//        )
//
//        Text(text = "UPI ID: $upiId")
//        Text(text = "Merchant Name: $merchantName")
//    }
//}
