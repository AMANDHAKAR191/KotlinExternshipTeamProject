package com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse

import ChipInputField
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Size
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import com.externship.kotlinexternshipteamproject.core.Constants.EXIT_DURATION
import com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse.componants.CustomTextField
import com.externship.kotlinexternshipteamproject.presentation.navigation.EnterAnimationForFAB
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditExpanseScreen(
    viewModel: AddEditExpenseViewModel = hiltViewModel(),
    navigateToHomeScreen: () -> Unit,
    context: Context,
    activity: Activity
) {
    val expenseTypeState = viewModel.expenseType.value
    val dateState = viewModel.date.value
    val amountState = viewModel.amount.value
    val categoryState = viewModel.category.value
    val paymentModeState = viewModel.paymentMode.value
    val tagsState = viewModel.tags.value
    val noteState = viewModel.note.value
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    //for animation
    val coroutineScope = rememberCoroutineScope()
    var isVisible by remember { mutableStateOf(true) }
    // Define a separate lambda for handling back navigation
    val handleBackNavigation: () -> Unit = {
        isVisible = false
        coroutineScope.launch {
            delay(EXIT_DURATION.toLong()) // Adjust this to match your animation duration
            navigateToHomeScreen()
        }
    }

    val scanQrCode = remember { mutableStateOf(false) }
    var chipExpanseValueSelected by remember { mutableStateOf(false) }
    var chipIncomeValueSelected by remember { mutableStateOf(false) }

    // Fetching the Local Context
    val mContext = LocalContext.current

    //for qr code
    var code by remember { mutableStateOf("") }
    var hasCodeRead by remember { mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFeature = remember { ProcessCameraProvider.getInstance(context) }

    //for asking camera permission
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCameraPermission = granted
        })
    LaunchedEffect(key1 = true) {
        launcher.launch(Manifest.permission.CAMERA)
    }

    //for showing the snackBar
    LaunchedEffect(key1 = viewModel.eventFlow) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is AddEditExpenseViewModel.UiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(event.message)
                }
                else -> {}
            }
        }
    }

    EnterAnimationForFAB(visible = isVisible) {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            topBar = {
                TopAppBar(
                    title = { Text(text = "Add Expanse") },
                    actions = {
                        Image(
                            Icons.Default.QrCodeScanner,
                            contentDescription = "QR Scanner",
                            modifier = Modifier.clickable {
                                scanQrCode.value = true
                                hasCodeRead = false
                            })
                    },
                    navigationIcon = {
                        IconButton(onClick = { handleBackNavigation() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "back")
                        }
                    })
            },
            content = { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
//todo uncomment for datePicker
//                    // Declaring integer values
//                    // for year, month and day
//                    val mYear: Int
//                    val mMonth: Int
//                    val mDay: Int
//                    // Initializing a Calendar
//                    val mCalendar = Calendar.getInstance()
//                    // Fetching current year, month and day
//                    mYear = mCalendar.get(Calendar.YEAR)
//                    mMonth = mCalendar.get(Calendar.MONTH)
//                    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
//                    mCalendar.time = Date()
//                    // Declaring a string value to
//                    // store date in string format
//                    val mDate = remember { mutableStateOf("") }
//                    // Declaring DatePickerDialog and setting
//                    // initial values as current values (present year, month and day)
//                    val mDatePickerDialog = DatePickerDialog(
//                        mContext,
//                        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
//                            mDate.value = "$mYear-${mMonth + 1}-$mMonth"
//                        }, mYear, mMonth, mDay
//                    )

                    viewModel.onPaymentSuccess = { responseCode, approvalRefNo ->
                        Toast.makeText(
                            context,
                            "\"responseCode: ${responseCode}\\napprovalRefNo: ${approvalRefNo}\"",
                            Toast.LENGTH_SHORT
                        ).show()
                        println("\"responseCode: ${responseCode}\\napprovalRefNo: ${approvalRefNo}\"")
                        if (approvalRefNo.isNotBlank()) {
                            viewModel.onEvent(AddEditExpenseEvent.SaveExpense)
                        }

                        viewModel.onEvent(AddEditExpenseEvent.SaveExpense)
                        navigateToHomeScreen()
                    }
                    viewModel.onPaymentFailure = {
                        Toast.makeText(context, "Payment Failed", Toast.LENGTH_SHORT).show()
                        navigateToHomeScreen()
                    }
                    if (scanQrCode.value) {
                        if (hasCameraPermission) {
                            if (hasCodeRead) {
                                Toast.makeText(context, "check1", Toast.LENGTH_SHORT).show()
                            } else {
                                AndroidView(
                                    factory = { context ->
                                        val previewView = PreviewView(context)
                                        val preview = Preview.Builder().build()
                                        val selector = CameraSelector.Builder()
                                            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                                            .build()
                                        preview.setSurfaceProvider(previewView.surfaceProvider)
                                        val imageAnalysis = ImageAnalysis.Builder()
                                            .setTargetResolution(
                                                Size(
                                                    previewView.width,
                                                    previewView.height
                                                )
                                            )
                                            .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
                                            .build()
                                        imageAnalysis.setAnalyzer(
                                            ContextCompat.getMainExecutor(context),
                                            QRCodeAnalyzer { result ->
                                                code = result
                                                getDataFromUpi(
                                                    code,
                                                    getData = { payeeName, paymentAddress ->
                                                        println("Payment Address1: $paymentAddress")
                                                        println("Payee Name1: $payeeName")
                                                        viewModel.onEvent(
                                                            AddEditExpenseEvent.EnteredPaymentMode(
                                                                paymentAddress
                                                            )
                                                        )
                                                        viewModel.onEvent(
                                                            AddEditExpenseEvent.EnteredNote(
                                                                "$payeeName "
                                                            )
                                                        )
                                                    })
                                                hasCodeRead = true
                                            }
                                        )
                                        try {
                                            cameraProviderFeature.get().bindToLifecycle(
                                                lifecycleOwner,
                                                selector,
                                                preview,
                                                imageAnalysis
                                            )
                                        } catch (e: Exception) {
                                            e.printStackTrace()
                                        }
                                        previewView
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        InputChip(selected = chipExpanseValueSelected, onClick = {
                            viewModel.onEvent(AddEditExpenseEvent.ChangeExpenseType("Expense"))
                            chipExpanseValueSelected = !chipExpanseValueSelected
                            if (chipIncomeValueSelected) {
                                chipIncomeValueSelected = !chipIncomeValueSelected
                            }

                        }, label = { Text(text = "Expanse") },
                            modifier = Modifier
                                .padding(all = 10.dp)
                        )
                        InputChip(selected = chipIncomeValueSelected, onClick = {
                            viewModel.onEvent(AddEditExpenseEvent.ChangeExpenseType("Income"))
                            chipIncomeValueSelected = !chipIncomeValueSelected
                            if (chipExpanseValueSelected) {
                                chipExpanseValueSelected = !chipExpanseValueSelected
                            }
                        }, label = { Text(text = "Income") },
                            modifier = Modifier
                                .padding(all = 10.dp)
                        )
                    }
//todo work on this in later update
//                CustomTextField(
//                    text = dateState.text,
//                    label = dateState.hint,
//                    onValueChange = {},
//                    enabled = false,
//                    leadingIcon = { Icons.Default.Home },
//                    trailingIcon = { Icons.Default.ArrowDropDown },
//                    singleLine = true,
//                    keyboardOptions = KeyboardOptions(
//                        keyboardType = KeyboardType.Text,
//                        imeAction = ImeAction.Next
//                    ),
//                    onClick = {
//                        Toast.makeText(context, "DatePicking..", Toast.LENGTH_SHORT).show()
//                        mDatePickerDialog.show()
//                        mDatePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
//                            mDate.value = "$mYear-${mMonth + 1}-$dayOfMonth"
//                            viewModel.onEvent(AddEditExpanseEvent.EnteredDate(mDate.value))
//                        }
//                    }
//                )

                    CustomTextField(
                        text = amountState.amount.toString(),
                        label = amountState.hint,
                        onValueChange = {
                            if (it.isDigitsOnly()) {
                                viewModel.onEvent(AddEditExpenseEvent.EnteredAmount(it))
                            }
                        },
                        enabled = true,
                        leadingIcon = null,
                        trailingIcon = { Icons.Default.Close },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        onTrailingIconClick = {
                            viewModel.onEvent(AddEditExpenseEvent.EnteredAmount(""))
                        },
                        onClick = {
                            Toast.makeText(context, "Clicked..", Toast.LENGTH_SHORT).show()
                        }
                    )

                    CustomTextField(
                        text = categoryState.text,
                        label = categoryState.hint,
                        onValueChange = { viewModel.onEvent(AddEditExpenseEvent.EnteredAmount(it)) },
                        enabled = false,
                        leadingIcon = null,
                        trailingIcon = { Icons.Default.ArrowDropDown },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        onClick = {
                            Toast.makeText(
                                context,
                                "This feature is not yet implemented",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                    CustomTextField(
                        text = paymentModeState.text,
                        label = paymentModeState.hint,
                        onValueChange = {
                            viewModel.onEvent(
                                AddEditExpenseEvent.EnteredPaymentMode(
                                    it
                                )
                            )
                        },
                        enabled = true,
                        leadingIcon = null,
                        trailingIcon = { Icons.Default.Close },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        onTrailingIconClick = {
                            viewModel.onEvent(AddEditExpenseEvent.EnteredPaymentMode(""))
                        },
                        onClick = {
                            Toast.makeText(context, "Clicked..", Toast.LENGTH_SHORT).show()
                        }
                    )
                    ChipInputField(
                        tags = ListStringConverter().toListString(tagsState.tagsList.toString()),
                        onSpacePressed = {
                            println("tags: $it")
                            viewModel.onEvent(
                                AddEditExpenseEvent.EnteredTags(
                                    ListStringConverter().fromListString(
                                        it
                                    )
                                )
                            )
                        })
                    CustomTextField(
                        text = noteState.text,
                        label = noteState.hint,
                        onValueChange = { viewModel.onEvent(AddEditExpenseEvent.EnteredNote(it)) },
                        enabled = true,
                        leadingIcon = null,
                        trailingIcon = { Icons.Default.Close },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        onTrailingIconClick = {
                            viewModel.onEvent(AddEditExpenseEvent.EnteredNote(""))
                        },
                        onClick = {
                            Toast.makeText(context, "Clicked..", Toast.LENGTH_SHORT).show()
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = {
                            if (chipExpanseValueSelected) {
//todo uncomment this code to enable easyUPIPayment
//                            viewModel.makePayment(
//                                viewModel.amount.value.text,
//                                viewModel.paymentMode.value.text,
//                                viewModel.note.value.text,
//                                viewModel.tags.value.tagsList,
//                                viewModel.date.value.text,
//                                context,
//                                activity
//                            )
//                            and comment these 2 lines
                                viewModel.onEvent(AddEditExpenseEvent.SaveExpense)
                                navigateToHomeScreen()
                            }
                            if (chipIncomeValueSelected) {
                                viewModel.onEvent(AddEditExpenseEvent.SaveExpense)
                                navigateToHomeScreen()
                            }

                        }, modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 40.dp)
                    ) {
                        //write a code to change the text "Pay" if chipExpanseValueSelected else "Save"
                        Text(text = if (chipExpanseValueSelected) "Pay" else "Save")
                    }

                }
            }
        )
    }
}

private fun getDataFromUpi(qrCodeResult: String, getData: (String, String) -> Unit) {
    val urlParts = qrCodeResult.split("?")
    val baseUrl = urlParts[0]
    val queryParams = urlParts[1]

    val queryParamPairs = queryParams.split("&")

    val paramsMap = mutableMapOf<String, String>()

    for (paramPair in queryParamPairs) {
        val paramParts = paramPair.split("=")
        val paramName = paramParts[0]
        val paramValue = paramParts[1]
        paramsMap[paramName] = paramValue
    }

    val paymentAddress = paramsMap["pa"]
    val transactionNote = paramsMap["tn"]
    val payeeName = paramsMap["pn"]

// Example usage:
    println("Payment Address: $paymentAddress")
//    println("Transaction Note: $transactionNote")
    println("Payee Name: $payeeName")
    getData(payeeName!!, paymentAddress!!)
}

