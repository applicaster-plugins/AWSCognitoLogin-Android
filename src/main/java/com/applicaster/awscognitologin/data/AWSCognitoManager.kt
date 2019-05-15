package com.applicaster.awscognitologin.data

import android.content.Context
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.amazonaws.regions.Regions
import com.applicaster.awscognitologin.plugin.PluginDataRepository

enum class AWSCognitoManager {
    INSTANCE;

    var userPool: CognitoUserPool? = null
    var cognitoUser: CognitoUser? = null

    companion object {

        fun getInstance(context: Context): CognitoUserPool {
            val instance = PluginDataRepository.INSTANCE
            return CognitoUserPool(context, instance.getUserPoolId(),
                    instance.getClientId(), instance.getClientSecret(),
                    Regions.fromName(instance.getRegion()))
        }

        fun getCognitoUserPool(context: Context, userPoolId: String, clientId: String,
                               clientSecret: String, region: String): CognitoUserPool {
            return CognitoUserPool(context, userPoolId, clientId, clientSecret,
                    Regions.fromName(region))
        }

        fun getUserAttributes(email: String): CognitoUserAttributes {
            val userAttributes = CognitoUserAttributes()
            userAttributes.addAttribute("email", email)
            return userAttributes
        }
    }
}