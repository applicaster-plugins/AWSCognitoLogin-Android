package com.applicaster.awscognitologin.screens.forgot

import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ForgotPasswordContinuation
import com.applicaster.awscognitologin.screens.base.View

interface ForgotPasswordView : View {
    fun onForgotPasswordContinuation(continuation: ForgotPasswordContinuation)
    fun onForgotPasswordSuccess()
    fun onForgotPasswordFail(error: String)
}