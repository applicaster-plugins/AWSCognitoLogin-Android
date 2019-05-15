package com.applicaster.awscognitologin.screens.forgot

import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ForgotPasswordContinuation

class ForgotPasswordPresenter(var forgotPasswordView: ForgotPasswordView,
                              var forgotPasswordInteractor: ForgotPasswordInteractor)
    : ForgotPasswordInteractor.OnForgotPasswordFinishedListener {
    override fun onForgotPasswordSuccess() {
        forgotPasswordView.hideProgress()
        forgotPasswordView.onForgotPasswordSuccess()
    }

    fun forgotPassword(username: String) {
        forgotPasswordView.showProgress()
        forgotPasswordInteractor.forgotPassword(username, this)
    }

    override fun onForgotPasswordContinuation(continuation: ForgotPasswordContinuation) {
        forgotPasswordView.hideProgress()
        forgotPasswordView.onForgotPasswordContinuation(continuation)
    }

    override fun onForgotPasswordFail(error: String) {
        forgotPasswordView.hideProgress()
        forgotPasswordView.onForgotPasswordFail(error)
    }
}