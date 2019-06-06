package com.applicaster.awscognitologin.plugin

import com.applicaster.awscognitologin.utils.Constants.Companion.CLIENT_ID
import com.applicaster.awscognitologin.utils.Constants.Companion.CLIENT_SECRET
import com.applicaster.awscognitologin.utils.Constants.Companion.PASSWORD
import com.applicaster.awscognitologin.utils.Constants.Companion.REGION
import com.applicaster.awscognitologin.utils.Constants.Companion.TOKEN
import com.applicaster.awscognitologin.utils.Constants.Companion.USERNAME
import com.applicaster.awscognitologin.utils.Constants.Companion.USER_ALREADY_LOGGED_IN
import com.applicaster.awscognitologin.utils.Constants.Companion.USER_POOL_ID
import com.applicaster.plugin_manager.login.LoginContract
import com.applicaster.util.PreferenceUtil

enum class PluginDataRepository : PluginRepository {
    INSTANCE {
        override fun isUserAlreadyLoggedIn(): Boolean {
            return PreferenceUtil.getInstance().getBooleanPref(USER_ALREADY_LOGGED_IN, false)
        }

        override fun setUserAlreadyLoggedIn(isUserAlreadyLoggedIn: Boolean) {
            PreferenceUtil.getInstance().setBooleanPref(USER_ALREADY_LOGGED_IN, isUserAlreadyLoggedIn)
        }

        override fun getUsername(): String {
            return PreferenceUtil.getInstance().getStringPref(USERNAME, "")
        }

        override fun setUsername(username: String) {
            PreferenceUtil.getInstance().setStringPref(USERNAME, username)
        }

        override fun getPassword(): String {
            return PreferenceUtil.getInstance().getStringPref(PASSWORD, "")
        }

        override fun setPassword(password: String) {
            PreferenceUtil.getInstance().setStringPref(PASSWORD, password)
        }

        override fun getToken(): String {
            return PreferenceUtil.getInstance().getStringPref(TOKEN, "")
        }

        override fun setToken(token: String?) {
            PreferenceUtil.getInstance().setStringPref(TOKEN, token)
        }

        override fun getUserPoolId(): String {
            return PreferenceUtil.getInstance().getStringPref(USER_POOL_ID, "")
        }

        override fun setUserPoolId(userPool: String) {
            PreferenceUtil.getInstance().setStringPref(USER_POOL_ID, userPool)
        }

        override fun getClientId(): String {
            return PreferenceUtil.getInstance().getStringPref(CLIENT_ID, "")
        }

        override fun setClientId(clientId: String) {
            PreferenceUtil.getInstance().setStringPref(CLIENT_ID, clientId)
        }

        override fun getClientSecret(): String {
            return PreferenceUtil.getInstance().getStringPref(CLIENT_SECRET, "")
        }

        override fun setClientSecret(clientSecret: String) {
            PreferenceUtil.getInstance().setStringPref(CLIENT_SECRET, clientSecret)
        }

        override fun getRegion(): String {
            return PreferenceUtil.getInstance().getStringPref(REGION, "")
        }

        override fun setRegion(region: String) {
            PreferenceUtil.getInstance().setStringPref(REGION, region)
        }
    };

    var params: MutableMap<Any?, Any?>? = null

    fun updateCredentials(token: String?) {
        setToken(token)
    }

    fun updateCredentials(username: String, password: String) {
        setUsername(username)
        setPassword(password)
    }

    fun updateCredentials(username: String, password: String, token: String?) {
        setUsername(username)
        setPassword(password)
        setToken(token)
    }

    fun clearCredentials() {
        setUserAlreadyLoggedIn(false)
        setUsername("")
        setPassword("")
        setToken("")
    }
}