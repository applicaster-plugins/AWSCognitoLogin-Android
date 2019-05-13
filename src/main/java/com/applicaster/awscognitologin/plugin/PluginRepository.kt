package com.applicaster.awscognitologin.plugin

interface PluginRepository {
    fun getUserPoolId(): String
    fun setUserPoolId(userPool: String)
    fun getClientId(): String
    fun setClientId(clientId: String)
    fun getClientSecret() : String
    fun setClientSecret(clientSecret: String)
    fun getRegion(): String
    fun setRegion(region: String)
}