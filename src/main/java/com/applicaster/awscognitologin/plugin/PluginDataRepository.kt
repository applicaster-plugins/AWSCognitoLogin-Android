package com.applicaster.awscognitologin.plugin

import com.applicaster.awscognitologin.utils.Constants.Companion.CLIENT_ID
import com.applicaster.awscognitologin.utils.Constants.Companion.CLIENT_SECRET
import com.applicaster.awscognitologin.utils.Constants.Companion.REGION
import com.applicaster.awscognitologin.utils.Constants.Companion.USER_POOL_ID
import com.applicaster.util.PreferenceUtil

enum class PluginDataRepository : PluginRepository {
    INSTANCE {
        override fun getUserPoolId(): String {
            return PreferenceUtil.getInstance().getStringPref(USER_POOL_ID, null)
        }

        override fun setUserPoolId(userPool: String) {
            PreferenceUtil.getInstance().setStringPref(USER_POOL_ID, userPool)
        }

        override fun getClientId(): String {
            return PreferenceUtil.getInstance().getStringPref(CLIENT_ID, null)
        }

        override fun setClientId(clientId: String) {
            PreferenceUtil.getInstance().setStringPref(CLIENT_ID, clientId)
        }

        override fun getClientSecret() : String {
            return PreferenceUtil.getInstance().getStringPref(CLIENT_SECRET, null)
        }

        override fun setClientSecret(clientSecret: String) {
            PreferenceUtil.getInstance().setStringPref(CLIENT_SECRET, clientSecret)
        }

        override fun getRegion(): String {
            return PreferenceUtil.getInstance().getStringPref(REGION, null)
        }

        override fun setRegion(region: String) {
            PreferenceUtil.getInstance().setStringPref(REGION, region)
        }
    };
}