package com.applicaster.awscognitologin.screens.base

import android.content.Context
import com.applicaster.awscognitologin.data.AWSCognitoManager

open class Interactor(var context: Context) {

    protected val userPool by lazy {
        AWSCognitoManager.getInstance(context)
    }
}