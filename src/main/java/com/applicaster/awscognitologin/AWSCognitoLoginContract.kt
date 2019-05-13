package com.applicaster.awscognitologin

import android.content.Context
import android.util.Log
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.applicaster.awscognitologin.data.AWSCognitoManager
import com.applicaster.awscognitologin.plugin.PluginDataRepository
import com.applicaster.awscognitologin.screens.signin.SignInActivity
import com.applicaster.awscognitologin.utils.Constants.Companion.CLIENT_ID
import com.applicaster.awscognitologin.utils.Constants.Companion.CLIENT_SECRET
import com.applicaster.awscognitologin.utils.Constants.Companion.REGION
import com.applicaster.awscognitologin.utils.Constants.Companion.USER_POOL_ID
import com.applicaster.plugin_manager.hook.HookListener
import com.applicaster.plugin_manager.login.AsyncLoginContract
import com.applicaster.plugin_manager.login.LoginContract
import com.applicaster.plugin_manager.playersmanager.Playable

class AWSCognitoLoginContract : AsyncLoginContract(), LoginContract.Callback {

    override fun login(context: Context?, additionalParams: MutableMap<Any?, Any?>?) {
        Log.d(this.javaClass.simpleName, "login")
    }

    override fun login(context: Context?, playable: Playable?, additionalParams: MutableMap<Any?, Any?>?) {
        Log.d(this.javaClass.simpleName, "login with playable")
        context?.let {
            AWSCognitoManager.INSTANCE.userPool = AWSCognitoManager.getInstance(it)
            it.startActivity(SignInActivity.getCallingIntent(it))
        }
    }

    override fun login(context: Context?, additionalParams: MutableMap<Any?, Any?>?, callback: LoginContract.Callback?) {
        Log.d(this.javaClass.simpleName, "login with playable")
        context?.let {
            AWSCognitoManager.INSTANCE.userPool = AWSCognitoManager.getInstance(it)
            it.startActivity(SignInActivity.getCallingIntent(it))
        }
    }

    override fun isItemLocked(model: Any?): Boolean {
        return true
    }

    override fun isItemLocked(context: Context?, model: Any?, callback: LoginContract.Callback?) {

    }

    override fun onResult(result: Boolean) {

    }

    override fun isTokenValid(): Boolean {
        return false
    }

    override fun setToken(token: String?) {

    }

    override fun executeOnStartup(context: Context?, listener: HookListener?) {
        Log.d(this.javaClass.simpleName, "execute on startup")
    }

    override fun getToken(): String {
        return ""
    }

    override fun setPluginConfigurationParams(params: MutableMap<Any?, Any?>?) {
        Log.d(this.javaClass.simpleName, "set plugin configuration")
        val instance = PluginDataRepository.INSTANCE
        params?.let {
            instance.setClientId(params[CLIENT_ID].toString())
            instance.setClientSecret(params[CLIENT_SECRET].toString())
            instance.setUserPoolId(params[USER_POOL_ID].toString())
            instance.setRegion(params[REGION].toString())
        }
    }

    override fun handlePluginScheme(context: Context?, data: MutableMap<String, String>?): Boolean {
        return true
    }

    override fun executeOnApplicationReady(context: Context?, listener: HookListener?) {
        Log.d(this.javaClass.simpleName, "execute on application ready")
    }

    override fun logout(context: Context?, additionalParams: MutableMap<Any?, Any?>?) {
        Log.d(this.javaClass.simpleName, "logout")
    }
}
