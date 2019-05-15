package com.applicaster.awscognitologin.screens.activate

import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler
import com.applicaster.awscognitologin.data.AWSCognitoManager

class ActivateAccountInteractor() {

    lateinit var listener: OnActivateAccountFinishedListener

    interface OnActivateAccountFinishedListener {
        fun onActivateAccountSuccess()

        fun onActivateAccountFail(exception: Exception?)
    }

    fun activateAccount(confirmationCode: String, listener: OnActivateAccountFinishedListener) {
        this.listener = listener
        // the alias creation is not forced
        AWSCognitoManager.INSTANCE.cognitoUser?.confirmSignUpInBackground(confirmationCode, false,
                confirmationCallback)
    }

    var confirmationCallback: GenericHandler = object : GenericHandler {
        override fun onSuccess() {
            listener.onActivateAccountSuccess()
        }

        override fun onFailure(exception: Exception?) {
            listener.onActivateAccountFail(exception)
        }

    }
}