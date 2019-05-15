package com.applicaster.awscognitologin.screens.activate

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler
import com.applicaster.awscognitologin.data.AWSCognitoManager
import com.applicaster.awscognitologin.plugin.PluginDataRepository

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
            AWSCognitoManager.INSTANCE.cognitoUser?.getSessionInBackground(authenticationHandler)
        }

        override fun onFailure(exception: Exception?) {
            listener.onActivateAccountFail(exception)
        }

    }

    var authenticationHandler: AuthenticationHandler = object : AuthenticationHandler {
        override fun onSuccess(userSession: CognitoUserSession?, newDevice: CognitoDevice?) {
            AWSCognitoManager.INSTANCE.cognitoUserSession = userSession
            PluginDataRepository.INSTANCE.setUserAlreadyLoggedIn(true)
            PluginDataRepository.INSTANCE.updateCredentials(userSession?.accessToken?.jwtToken)
            listener.onActivateAccountSuccess()
        }

        override fun onFailure(exception: java.lang.Exception?) {
            listener.onActivateAccountFail(exception)
        }

        override fun getAuthenticationDetails(authenticationContinuation: AuthenticationContinuation, userId: String?) {
            // The API needs user sign-in credentials to continue
            val authenticationDetails = AuthenticationDetails(userId, PluginDataRepository.INSTANCE.getPassword(), null)
            // Pass the user sign-in credentials to the continuation
            authenticationContinuation.setAuthenticationDetails(authenticationDetails)
            // Allow the sign-in to continue
            authenticationContinuation.continueTask()
        }

        override fun authenticationChallenge(continuation: ChallengeContinuation?) {
            // do nothing
        }

        override fun getMFACode(continuation: MultiFactorAuthenticationContinuation?) {
            // do nothing
        }

    }
}