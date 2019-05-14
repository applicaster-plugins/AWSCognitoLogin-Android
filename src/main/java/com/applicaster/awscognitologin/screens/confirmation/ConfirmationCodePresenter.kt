package com.applicaster.awscognitologin.screens.confirmation

class ConfirmationCodePresenter(var confirmationCodeView: ConfirmationCodeView,
                                var confirmationCodeInteractor: ConfirmationCodeInteractor)
    : ConfirmationCodeInteractor.OnConfirmationCodeFinishedListener {

    fun sendConfirmationCode(username: String) {
        confirmationCodeView.showProgress()
        confirmationCodeInteractor.sendActivationCode(username, this)
    }

    override fun onActivationCodeSuccess() {
        confirmationCodeView.hideProgress()
        confirmationCodeView.onConfirmationCodeSuccess()
    }

    override fun onActivationCodeFail(error: String) {
        confirmationCodeView.hideProgress()
        confirmationCodeView.onConfirmationCodeFail(error)
    }

}