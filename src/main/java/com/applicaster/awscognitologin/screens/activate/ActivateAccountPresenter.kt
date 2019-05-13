package com.applicaster.awscognitologin.screens.activate

import java.lang.Exception


class ActivateAccountPresenter(var activateAccountView: ActivateAccountView,
                               var activateAccountInteractor: ActivateAccountInteractor) :
        ActivateAccountInteractor.OnActivateAccountFinishedListener {

    fun activateAccount(confirmationCode: String) {
        activateAccountView.showProgress()
        activateAccountInteractor.activateAccount(confirmationCode, this)
    }

    override fun onActivateAccountSuccess() {
        activateAccountView.hideProgress()
        activateAccountView.onActivateAccountSuccess()
    }

    override fun onActivateAccountFail(exception: Exception?) {
        activateAccountView.hideProgress()
        activateAccountView.onActivateAccountFail(exception)
    }


}