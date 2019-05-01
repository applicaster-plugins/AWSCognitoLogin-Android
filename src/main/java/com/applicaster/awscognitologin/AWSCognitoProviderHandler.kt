package com.applicaster.awscognitologin

import android.content.Context
import com.applicaster.authprovider.LinkAuthenticationProviderHandler
import com.applicaster.awscognitologin.screens.signin.SignInActivity
import com.applicaster.model.APAuthenticationProvider
import com.applicaster.model.APModel
import java.util.HashMap

class AWSCognitoProviderHandler : LinkAuthenticationProviderHandler() {

    private var context: Context? = null

    override fun init(provider: APAuthenticationProvider?, context: Context?, eventParam: APModel?) {
        super.init(provider, context, eventParam)
        this.context = context
    }

    override fun startAuthorization(params: HashMap<String, String>?) {
        // todo: get configuration parameters
        context?.let {
            it.startActivity(SignInActivity.getCallingIntent(it))
        }
    }

    override fun onAuthenticationSuccess(token: String?) {
        super.onAuthenticationSuccess(token)
    }

    override fun onAuthenticationError(error: String?) {
        super.onAuthenticationError(error)
    }

    override fun onAuthenticationCancel() {
        super.onAuthenticationCancel()
    }
}
