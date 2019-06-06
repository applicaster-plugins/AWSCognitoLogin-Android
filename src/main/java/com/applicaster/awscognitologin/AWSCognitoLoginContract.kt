package com.applicaster.awscognitologin

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.applicaster.awscognitologin.data.AWSCognitoManager
import com.applicaster.awscognitologin.plugin.PluginDataRepository
import com.applicaster.awscognitologin.screens.signin.SignInActivity
import com.applicaster.awscognitologin.screens.signin.SignInInteractor
import com.applicaster.awscognitologin.utils.Constants.Companion.CLIENT_ID
import com.applicaster.awscognitologin.utils.Constants.Companion.CLIENT_SECRET
import com.applicaster.awscognitologin.utils.Constants.Companion.REGION
import com.applicaster.awscognitologin.utils.Constants.Companion.TOKEN
import com.applicaster.awscognitologin.utils.Constants.Companion.USER_POOL_ID
import com.applicaster.awscognitologin.utils.UIUtils
import com.applicaster.model.APCategory
import com.applicaster.model.APChannel
import com.applicaster.model.APURLPlayable
import com.applicaster.model.APVodItem
import com.applicaster.plugin_manager.Plugin
import com.applicaster.plugin_manager.hook.HookListener
import com.applicaster.plugin_manager.login.AsyncLoginContract
import com.applicaster.plugin_manager.login.LoginContract
import com.applicaster.plugin_manager.login.LoginManager
import com.applicaster.plugin_manager.playersmanager.Playable
import com.applicaster.util.PreferenceUtil
import com.applicaster.util.ui.Toaster

class AWSCognitoLoginContract : AsyncLoginContract(), LoginContract.Callback, SignInInteractor.OnSignInFinishedListener {
    var context: Context? = null

    override fun login(context: Context?, additionalParams: MutableMap<Any?, Any?>?) {
        Log.d(this.javaClass.simpleName, "login")
    }

    override fun login(context: Context?, playable: Playable?, additionalParams: MutableMap<Any?, Any?>?) {
        Log.d(this.javaClass.simpleName, "login with playable")
        this.context = context
        context?.let {
            AWSCognitoManager.INSTANCE.userPool = AWSCognitoManager.getInstance(context)
            if (PluginDataRepository.INSTANCE.isUserAlreadyLoggedIn()) {
                // login
                SignInInteractor().signIn(PluginDataRepository.INSTANCE.getUsername(),
                        PluginDataRepository.INSTANCE.getPassword(),
                        this)
            } else {
                // if user is not logged in already launch LoginActivity
                launchLoginActivity(context)
            }
        }

    }

    // region response from login
    override fun onSignInSuccess() {
        LoginManager.notifyEvent(context, LoginManager.RequestType.LOGIN, true)
    }

    override fun onSignInFail(error: String) {
        Toaster.makeToast(context, "onSignInFail")
        launchLoginActivity(context)
    }
    // endregion

    private fun launchLoginActivity(context: Context?) {
        context?.startActivity(SignInActivity.getCallingIntent(context))
    }

    override fun isItemLocked(model: Any?): Boolean {
//        model?.let {
//            if(model is APVodItem && model.isFree) return false
//            if(model is APURLPlayable && model.isFree) return false
//            if(model is APCategory && model.isFree) return false
//            if(model is APChannel && model.isFree) return false
//        }

        return true
    }

    override fun isItemLocked(context: Context?, model: Any?, callback: LoginContract.Callback?) {
        // do nothing?
    }

    override fun onResult(result: Boolean) {
        // do nothing?
    }

    override fun isTokenValid(): Boolean {
        // todo: check with javi for once and all how this actually have to work
        if (PluginDataRepository.INSTANCE.getToken() != null) {
            return true
        }
        return false
    }

    override fun setToken(token: String?) {
        // todo: check with javi for once and all how this actually have to work
        PluginDataRepository.INSTANCE.setToken(token)
    }

    override fun executeOnStartup(context: Context?, listener: HookListener?) {
        Log.d(this.javaClass.simpleName, "execute on startup")
    }

    override fun getToken(): String {
        val token = PluginDataRepository.INSTANCE.getToken()
        token?.let { return token }
        return ""
    }

    override fun setPluginConfigurationParams(params: MutableMap<Any?, Any?>?) {
        Log.d(this.javaClass.simpleName, "set plugin configuration")
        val instance = PluginDataRepository.INSTANCE
        PluginDataRepository.INSTANCE.params = params
        params?.let {
            instance.setClientId(params[CLIENT_ID].toString())
            instance.setClientSecret(params[CLIENT_SECRET].toString())
            instance.setUserPoolId(params[USER_POOL_ID].toString())
            instance.setRegion(params[REGION].toString())
        }
    }

    override fun handlePluginScheme(context: Context?, data: MutableMap<String, String>?): Boolean {
        // todo: localize this messages
        data?.let {
            if (it["type"].equals("login")) {
                if (it["action"].equals("logout")) {
                    if (!PluginDataRepository.INSTANCE.isUserAlreadyLoggedIn()) {
                        AWSCognitoManager.INSTANCE.userPool?.let { userPool ->
                            userPool.user?.signOut()
                            AWSCognitoManager.INSTANCE.userPool = null
                            PluginDataRepository.INSTANCE.clearCredentials()
                            context?.let {
                                UIUtils.getAlertDialog(context, "Sign Out", "Signed out successfully.",
                                        "OK",
                                        DialogInterface.OnClickListener { dialogInterface, _ -> dialogInterface.dismiss() })
                                        .show()
                            }
                            return true
                        }
                    } else {
                        context?.let {
                            UIUtils.getAlertDialog(context, "Sign Out", "There's no user signed in.",
                                    "OK",
                                    DialogInterface.OnClickListener { dialogInterface, _ -> dialogInterface.dismiss() })
                                    .show()
                        }
                    }
                }
            }
        }
        return false
    }

    override fun executeOnApplicationReady(context: Context?, listener: HookListener?) {
        Log.d(this.javaClass.simpleName, "execute on application ready")
    }

    override fun logout(context: Context?, additionalParams: MutableMap<Any?, Any?>?) {
        Log.d(this.javaClass.simpleName, "logout")
    }
}
