package com.applicaster.awscognitologin.screens.signin

import com.applicaster.awscognitologin.screens.base.View


interface SignInView : View {
    fun onSignInSuccess()
    fun onSignInFail(message: String)
}