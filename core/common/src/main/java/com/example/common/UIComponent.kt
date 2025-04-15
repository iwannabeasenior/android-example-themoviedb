package com.example.common

sealed class UIComponent {
    data class ToastSimple(val errorType: ErrorType) : UIComponent()
    data class DialogSimple(val errorType: ErrorType, val onConfirm: () -> Unit) : UIComponent()
    data class None(val errorType: ErrorType) : UIComponent()
}