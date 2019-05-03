package com.applicaster.awscognitologin

import android.content.Context
import android.util.Log
import com.applicaster.awscognitologin.screens.signin.SignInActivity
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
        context?.startActivity(SignInActivity.getCallingIntent(context))
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
