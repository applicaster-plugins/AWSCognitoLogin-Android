package com.applicaster.awscognitologin.screens.confirmation

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.VerificationHandler
import com.applicaster.awscognitologin.data.AWSCognitoManager
import java.lang.Exception

class ConfirmationCodeInteractor {

    lateinit var listener: OnConfirmationCodeFinishedListener

    interface OnConfirmationCodeFinishedListener {
        fun onActivationCodeSuccess()
        fun onActivationCodeFail(error: String)
    }

    fun sendActivationCode(username: String, listener: OnConfirmationCodeFinishedListener) {
        this.listener = listener

        AWSCognitoManager.INSTANCE.userPool.getUser(username)
                .resendConfirmationCodeInBackground(confirmationCodeHandler)
    }

    private var confirmationCodeHandler: VerificationHandler = object : VerificationHandler {
        override fun onSuccess(verificationCodeDeliveryMedium: CognitoUserCodeDeliveryDetails?) {
            listener.onActivationCodeSuccess()
        }

        override fun onFailure(exception: Exception?) {
            exception?.message?.let { listener.onActivationCodeFail(it) }
        }

    }
}