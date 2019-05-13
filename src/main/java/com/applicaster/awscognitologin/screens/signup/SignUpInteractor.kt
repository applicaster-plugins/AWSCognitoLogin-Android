package com.applicaster.awscognitologin.screens.signup

import android.content.Context
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler
import com.applicaster.awscognitologin.data.AWSCognitoManager
import com.applicaster.awscognitologin.screens.base.Interactor
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser


class SignUpInteractor(context: Context) : Interactor(context) {

    lateinit var listener: OnSignUpFinishedListener

    interface OnSignUpFinishedListener {
        fun onSignUpSuccess(userIsConfirmed: Boolean,
                            cognitoUserCodeDeliveryDetails: CognitoUserCodeDeliveryDetails)

        fun onSignUpFail(exception: Exception)
    }

    fun signUp(username: String, email: String, password: String, listener: OnSignUpFinishedListener) {
        this.listener = listener
        val userAttributes = AWSCognitoManager.getUserAttributes(email)
        userPool.signUpInBackground(username, password, userAttributes, null, signupCallback)
    }

    private var signupCallback: SignUpHandler = object : SignUpHandler {
        override fun onSuccess(cognitoUser: CognitoUser, userConfirmed: Boolean, cognitoUserCodeDeliveryDetails: CognitoUserCodeDeliveryDetails) {
            listener.onSignUpSuccess(userConfirmed, cognitoUserCodeDeliveryDetails)
        }

        override fun onFailure(exception: Exception) {
            listener.onSignUpFail(exception)
        }
    }
}