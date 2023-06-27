package com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.externship.kotlinexternshipteamproject.domain.model.Expense
import com.externship.kotlinexternshipteamproject.domain.model.InvalidExpenseException
import com.externship.kotlinexternshipteamproject.domain.use_cases.other.ExpenseUseCases
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
class AddEditExpenseViewModel @Inject constructor(
    private val expenseUseCases: ExpenseUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel(), PaymentStatusListener {
    val sdf = SimpleDateFormat("YYYY-MM-DD", Locale.getDefault())

    var isSnackBarShowing: Boolean by mutableStateOf(false)
        private set

    private val _expenseType = mutableStateOf(
        ExpenseTextFieldState(
            text = Expense.expanseType[1]
        )
    )
    val expenseType: State<ExpenseTextFieldState> = _expenseType

    private val _date = mutableStateOf(
        ExpenseTextFieldState(
            hint = "Date",
            date = ""
        )
    )
    val date: State<ExpenseTextFieldState> = _date

    private val _amount = mutableStateOf(
        ExpenseTextFieldState(
            hint = "Amount"
        )
    )
    val amount: State<ExpenseTextFieldState> = _amount

    private val _category = mutableStateOf(
        ExpenseTextFieldState(
            hint = "Category"
        )
    )
    val category: State<ExpenseTextFieldState> = _category

    private val _paymentMode = mutableStateOf(
        ExpenseTextFieldState(
            hint = "Payment Upi"
        )
    )
    val paymentMode: State<ExpenseTextFieldState> = _paymentMode

    private val _tags = mutableStateOf(
        ExpenseTextFieldState(
            hint = "Tags"
        )
    )
    val tags: State<ExpenseTextFieldState> = _tags
    private val _note = mutableStateOf(
        ExpenseTextFieldState(
            hint = "Receiver Name"
        )
    )
    val note: State<ExpenseTextFieldState> = _note


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentExpanseId: Int? = null

    var onPaymentSuccess: ((responseCode: String, approvalRefNo: String) -> Unit)? = null
    var onPaymentFailure: (() -> Unit)? = null

    init {
        savedStateHandle.get<Int>("expanseId")?.let { expanseId ->
            if (expanseId != -1) {
                viewModelScope.launch {
                    expenseUseCases.getExpense(expanseId)?.also { expanse ->
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

    fun onEvent(event: AddEditExpenseEvent) {
        when (event) {
            is AddEditExpenseEvent.EnteredDate -> {
                _date.value = date.value.copy(
                    text = event.value
                )
            }

            is AddEditExpenseEvent.EnteredAmount -> {
                _amount.value = amount.value.copy(
                    text = event.value,
                    amount = event.value.toIntOrNull() ?: 0
                )
            }

            is AddEditExpenseEvent.EnteredCategory -> {
                _category.value = category.value.copy(
                    text = event.value
                )
            }

            is AddEditExpenseEvent.EnteredPaymentMode -> {
                _paymentMode.value = paymentMode.value.copy(
                    text = event.value
                )
            }

            is AddEditExpenseEvent.EnteredTags -> {
                _tags.value = tags.value.copy(
                    tagsList = event.value
                )
            }

            is AddEditExpenseEvent.EnteredNote -> {
                _note.value = note.value.copy(
                    text = event.value
                )
            }

            is AddEditExpenseEvent.ChangeExpenseType -> {
                _expenseType.value = expenseType.value.copy(
                    text = event.value
                )
            }

            is AddEditExpenseEvent.SaveExpense -> {
                println(amount.value.amount)
                viewModelScope.launch {
                    println()
                    try {
                        expenseUseCases.addExpense(
                            Expense(
                                amount = amount.value.amount,
                                category = category.value.text,
                                paymentMode = paymentMode.value.text,
                                tags = tags.value.tagsList,
                                note = note.value.text,
                                type = expenseType.value.text
                            )
                        )
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                message = "Expense saved"
                            )
                        )
                    } catch (e: InvalidExpenseException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                message = e.message ?: "Couldn't save Expense"
                            )
                        )
                    }

                }
//                viewModelScope.launch {
//                    try {
//                        expenseUseCases.addExpense(
//                            Expense(
//                                amount = amount.value.amount,
//                                category = category.value.text,
//                                paymentMode = paymentMode.value.text,
//                                tags = tags.value.tagsList,
//                                note = note.value.text,
//                                type = expenseType.value.text
//                            )
//                        )
//                        _eventFlow.emit(
//                            UiEvent.ShowSnackBar(
//                                message = "Expense saved"
//                            )
//                        )
//                    } catch (e: InvalidExpenseException) {
//                        _eventFlow.emit(
//                            UiEvent.ShowSnackBar(
//                                message = e.message ?: "Couldn't save Expense"
//                            )
//                        )
//                    }
//
//                }
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