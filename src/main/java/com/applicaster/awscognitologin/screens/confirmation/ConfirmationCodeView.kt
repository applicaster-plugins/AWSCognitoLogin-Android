package com.applicaster.awscognitologin.screens.confirmation

import com.applicaster.awscognitologin.screens.base.View

interface ConfirmationCodeView : View {
    fun onConfirmationCodeSuccess()
    fun onConfirmationCodeFail(error: String)
}