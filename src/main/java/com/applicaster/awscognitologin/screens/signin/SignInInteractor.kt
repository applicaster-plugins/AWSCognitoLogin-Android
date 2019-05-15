package com.applicaster.awscognitologin.screens.signin

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser
import com.applicaster.awscognitologin.data.AWSCognitoManager
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler
import com.applicaster.awscognitologin.plugin.PluginDataRepository


class SignInInteractor {
    
    lateinit var listener: OnSignInFinishedListener
    lateinit var username: String
    lateinit var password: String

    interface OnSignInFinishedListener {
        fun onSignInSuccess()
        fun onSignInFail(error: String)
    }

    fun signIn(username: String, password: String, listener: OnSignInFinishedListener) {
        this.listener = listener
        this.username = username
        this.password = password

        AWSCognitoManager.INSTANCE.userPool?.getUser(username)?.getSessionInBackground(authenticationHandler)
    }

    // Callback handler for the sign-in process
    private var authenticationHandler: AuthenticationHandler = object : AuthenticationHandler {

        override fun onSuccess(userSession: CognitoUserSession?, newDevice: CognitoDevice?) {
            AWSCognitoManager.INSTANCE.cognitoUserSession = userSession
            PluginDataRepository.INSTANCE.setUserAlreadyLoggedIn(true)
            PluginDataRepository.INSTANCE.updateCredentials(username, password, userSession?.accessToken?.jwtToken)
            listener.onSignInSuccess()
        }

        override fun authenticationChallenge(continuation: ChallengeContinuation?) {
            // nothing to do
        }

        override fun getAuthenticationDetails(authenticationContinuation: AuthenticationContinuation, userId: String) {
            // The API needs user sign-in credentials to continue
            val authenticationDetails = AuthenticationDetails(userId, password, null)
            // Pass the user sign-in credentials to the continuation
            authenticationContinuation.setAuthenticationDetails(authenticationDetails)
            // Allow the sign-in to continue
            authenticationContinuation.continueTask()
        }

        override fun getMFACode(multiFactorAuthenticationContinuation: MultiFactorAuthenticationContinuation) {
            // nothing to do
        }

        override fun onFailure(exception: Exception) {
            exception.message?.let { listener.onSignInFail(it) }
        }
    }
}