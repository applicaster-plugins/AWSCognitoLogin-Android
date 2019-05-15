package com.applicaster.awscognitologin.screens.signup

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.applicaster.awscognitologin.screens.base.View
import java.lang.Exception

interface SignUpView : View {
    fun onSignUpSuccess()
    fun onUserIsNotConfirmed(cognitoUserCodeDeliveryDetails: CognitoUserCodeDeliveryDetails)
    fun onSignUpFailed(exception: Exception)
}