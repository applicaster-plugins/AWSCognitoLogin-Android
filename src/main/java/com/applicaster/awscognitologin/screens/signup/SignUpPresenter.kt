package com.applicaster.awscognitologin.screens.signup

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails

class SignUpPresenter(var signUpView: SignUpView, var signUpInteractor: SignUpInteractor):
        SignUpInteractor.OnSignUpFinishedListener {
    override fun onSignUpSuccess(userIsConfirmed: Boolean,
                                 cognitoUserCodeDeliveryDetails: CognitoUserCodeDeliveryDetails) {
        if (userIsConfirmed) {
            signUpView.onSignUpSuccess()
        } else {
            signUpView.onUserIsNotConfirmed(cognitoUserCodeDeliveryDetails)
        }

        signUpView.hideProgress()
    }

    override fun onSignUpFail(exception: Exception) {
        signUpView.onSignUpFailed(exception)
        signUpView.hideProgress()
    }

    fun signUp(username: String, email: String, password: String) {
        signUpView.showProgress()
        signUpInteractor.signUp(username, email, password, this)
    }
}