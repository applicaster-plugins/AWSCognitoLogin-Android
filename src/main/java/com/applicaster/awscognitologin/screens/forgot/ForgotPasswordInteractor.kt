package com.applicaster.awscognitologin.screens.forgot

import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ForgotPasswordContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.ForgotPasswordHandler
import com.applicaster.awscognitologin.data.AWSCognitoManager
import java.lang.Exception

class ForgotPasswordInteractor {

    lateinit var listener: OnForgotPasswordFinishedListener

    interface OnForgotPasswordFinishedListener {
        fun onForgotPasswordContinuation(continuation: ForgotPasswordContinuation)
        fun onForgotPasswordSuccess()
        fun onForgotPasswordFail(error: String)
    }

    fun forgotPassword(username: String, listener: OnForgotPasswordFinishedListener) {
        this.listener = listener

        AWSCognitoManager.INSTANCE.userPool.getUser(username).forgotPasswordInBackground(forgotPasswordHandler)
    }

    private var forgotPasswordHandler: ForgotPasswordHandler = object : ForgotPasswordHandler {
        override fun onSuccess() {
            listener.onForgotPasswordSuccess()
        }

        override fun onFailure(exception: Exception?) {
            exception?.message?.let { listener.onForgotPasswordFail(it) }
        }

        override fun getResetCode(continuation: ForgotPasswordContinuation?) {
            continuation?.let { listener.onForgotPasswordContinuation(it) }
        }

    }
}