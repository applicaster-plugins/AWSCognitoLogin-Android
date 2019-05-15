package com.applicaster.awscognitologin.screens.activate

import com.applicaster.awscognitologin.screens.base.View
import java.lang.Exception

interface ActivateAccountView : View {
    fun onActivateAccountSuccess()
    fun onActivateAccountFail(exception: Exception?)
}