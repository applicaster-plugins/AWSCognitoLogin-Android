package com.applicaster.awscognitologin.screens.signin

class SignInPresenter(var signInView: SignInView, var signInInteractor: SignInInteractor) :
        SignInInteractor.OnSignInFinishedListener {

    fun signIn(username: String, password: String) {
        signInInteractor.signIn(username, password, this)
    }

    override fun onSignInSuccess() {
        signInView.hideProgress()
        signInView.onSignInSuccess()
    }

    override fun onSignInFail(error: String) {
        signInView.hideProgress()
        signInView.onSignInFail(error)
    }

}