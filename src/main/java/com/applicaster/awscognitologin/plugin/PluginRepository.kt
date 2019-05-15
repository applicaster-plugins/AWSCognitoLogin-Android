package com.applicaster.awscognitologin.plugin

interface PluginRepository {
    fun isUserAlreadyLoggedIn() : Boolean
    fun setUserAlreadyLoggedIn(isUserAlreadyLoggedIn: Boolean)
    fun getUsername() : String
    fun setUsername(username: String)
    fun getPassword() : String
    fun setPassword(password: String)
    fun getToken() : String?
    fun setToken(token: String?)
    fun getUserPoolId(): String
    fun setUserPoolId(userPool: String)
    fun getClientId(): String
    fun setClientId(clientId: String)
    fun getClientSecret() : String
    fun setClientSecret(clientSecret: String)
    fun getRegion(): String
    fun setRegion(region: String)
}