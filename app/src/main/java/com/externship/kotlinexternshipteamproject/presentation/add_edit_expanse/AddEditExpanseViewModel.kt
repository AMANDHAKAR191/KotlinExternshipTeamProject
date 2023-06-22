package com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.externship.kotlinexternshipteamproject.domain.model.Expanse
import com.externship.kotlinexternshipteamproject.domain.model.InvalidExpanseException
import com.externship.kotlinexternshipteamproject.domain.use_cases.other.ExpanseUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.shreyaspatil.easyupipayment.EasyUpiPayment
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener
import dev.shreyaspatil.easyupipayment.model.PaymentApp
import dev.shreyaspatil.easyupipayment.model.TransactionDetails
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AddEditExpanseViewModel @Inject constructor(
    private val expanseUseCases: ExpanseUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel(), PaymentStatusListener {
    val sdf = SimpleDateFormat("YYYY-MM-DD", Locale.getDefault())

    private val _expanseType = mutableStateOf(
        ExpanseTextFieldState(
            text = Expanse.expanseType[1]
        )
    )
    val expanseType: State<ExpanseTextFieldState> = _expanseType

    private val _date = mutableStateOf(
        ExpanseTextFieldState(
            hint = "Date",
            date = ""
        )
    )
    val date: State<ExpanseTextFieldState> = _date

    private val _amount = mutableStateOf(
        ExpanseTextFieldState(
            hint = "Amount"
        )
    )
    val amount: State<ExpanseTextFieldState> = _amount

    private val _category = mutableStateOf(
        ExpanseTextFieldState(
            hint = "Category"
        )
    )
    val category: State<ExpanseTextFieldState> = _category

    private val _paymentMode = mutableStateOf(
        ExpanseTextFieldState(
            hint = "Payment Mode"
        )
    )
    val paymentMode: State<ExpanseTextFieldState> = _paymentMode

    private val _tags = mutableStateOf(
        ExpanseTextFieldState(
            hint = "Tags"
        )
    )
    val tags: State<ExpanseTextFieldState> = _tags
    private val _note = mutableStateOf(
        ExpanseTextFieldState(
            hint = "Note"
        )
    )
    val note: State<ExpanseTextFieldState> = _note


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentExpanseId: Int? = null

    var onPaymentSuccess: ((responseCode: String, approvalRefNo: String) -> Unit)? = null
    var onPaymentFailure: (() -> Unit)? = null

    init {
        savedStateHandle.get<Int>("expanseId")?.let { expanseId ->
            if (expanseId != -1) {
                viewModelScope.launch {
                    expanseUseCases.getExpanse(expanseId)?.also { expanse ->
                        currentExpanseId = expanse.id
                        _date.value = date.value.copy(
                            date = expanse.date.toString(),
                            isHintVisible = false
                        )
                        _amount.value = amount.value.copy(
                            amount = expanse.amount,
                            isHintVisible = false
                        )
                        _category.value = category.value.copy(
                            text = expanse.category,
                            isHintVisible = false
                        )
                        _paymentMode.value = paymentMode.value.copy(
                            text = expanse.paymentMode,
                            isHintVisible = false
                        )
                        _tags.value = tags.value.copy(
                            tagsList = expanse.tags,
                            isHintVisible = false
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditExpanseEvent) {
        when (event) {
            is AddEditExpanseEvent.EnteredDate -> {
                _date.value = date.value.copy(
                    text = event.value
                )
            }

            is AddEditExpanseEvent.EnteredAmount -> {
                _amount.value = amount.value.copy(
                    text = event.value,
                    amount = event.value.toInt()
                )
            }

            is AddEditExpanseEvent.EnteredCategory -> {
                _category.value = category.value.copy(
                    text = event.value
                )
            }

            is AddEditExpanseEvent.EnteredPaymentMode -> {
                _paymentMode.value = paymentMode.value.copy(
                    text = event.value
                )
            }

            is AddEditExpanseEvent.EnteredTags -> {
                _tags.value = tags.value.copy(
                    tagsList = event.value
                )
            }

            is AddEditExpanseEvent.EnteredNote -> {
                _note.value = note.value.copy(
                    text = event.value
                )
            }

            is AddEditExpanseEvent.ChangeExpanseType -> {
                _expanseType.value = expanseType.value.copy(
                    text = event.value
                )
            }

            is AddEditExpanseEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        expanseUseCases.addExpanse(
                            Expanse(
                                amount = amount.value.amount,
                                category = category.value.text,
                                paymentMode = paymentMode.value.text,
                                tags = tags.value.tagsList,
                                note = note.value.text,
                                type = expanseType.value.text
                            )
                        )
                    } catch (e: InvalidExpanseException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                message = e.message ?: "Couldn't save Expanse"
                            )
                        )
                    }

                }
            }

            else -> {}
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        object SaveNote : UiEvent()
    }

    fun makePayment(
        amount: String?,
        upi: String?,
        name: String?,
        desc: String?,
        transactionId: String?,
        context: Context,
        activity: Activity,
    ) {
        try {
            val easyUpiPayment = EasyUpiPayment(activity) {
                this.paymentApp = PaymentApp.ALL
                this.payeeVpa = upi
                this.payeeName = name
                this.transactionId = transactionId
                this.transactionRefId = transactionId
                this.payeeMerchantCode = transactionId
                this.description = desc
                this.amount = amount
            }
            easyUpiPayment.setPaymentStatusListener(this)
            easyUpiPayment.startPayment()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onTransactionCancelled() {
        onPaymentFailure?.invoke()
    }

    override fun onTransactionCompleted(transactionDetails: TransactionDetails) {

        onPaymentSuccess?.invoke(
            transactionDetails.responseCode.toString(),
            transactionDetails.approvalRefNo.toString()
        )
    }
}