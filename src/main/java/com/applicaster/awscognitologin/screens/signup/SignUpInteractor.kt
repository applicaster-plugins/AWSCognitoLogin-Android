package com.applicaster.awscognitologin.screens.signup

import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler
import com.applicaster.awscognitologin.data.AWSCognitoManager
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser


class SignUpInteractor {

    lateinit var listener: OnSignUpFinishedListener

    interface OnSignUpFinishedListener {
        fun onSignUpSuccess(userIsConfirmed: Boolean,
                            cognitoUserCodeDeliveryDetails: CognitoUserCodeDeliveryDetails)

        fun onSignUpFail(exception: Exception)
    }

    fun signUp(username: String, email: String, password: String, listener: OnSignUpFinishedListener) {
        this.listener = listener
        val userAttributes = AWSCognitoManager.getUserAttributes(email)
        AWSCognitoManager.INSTANCE.userPool?.signUpInBackground(username, password, userAttributes, null, signupCallback)
    }

    private var signupCallback: SignUpHandler = object : SignUpHandler {
        override fun onSuccess(cognitoUser: CognitoUser, userConfirmed: Boolean,
                               cognitoUserCodeDeliveryDetails: CognitoUserCodeDeliveryDetails) {
            AWSCognitoManager.INSTANCE.cognitoUser = cognitoUser
            listener.onSignUpSuccess(userConfirmed, cognitoUserCodeDeliveryDetails)
        }

        override fun onFailure(exception: Exception) {
            listener.onSignUpFail(exception)
        }
    }
}