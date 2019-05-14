package com.applicaster.awscognitologin.screens.activate

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.applicaster.awscognitologin.R
import com.applicaster.plugin_manager.login.LoginManager
import com.applicaster.util.ui.Toaster
import kotlinx.android.synthetic.main.activity_activate_account.*
import java.lang.Exception

class ActivateAccountActivity : AppCompatActivity(), ActivateAccountView {

    var activateAccountPresenter: ActivateAccountPresenter = ActivateAccountPresenter(
            this, ActivateAccountInteractor())

    companion object {

        fun getCallingIntent(@NonNull context: Context): Intent {
            return Intent(context, ActivateAccountActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_activate_account)

        btn_activate.setOnClickListener {
            if(et_code_aa.text.isNotEmpty()) {
                activateAccountPresenter.activateAccount(et_code_aa.text.toString())
            } else {
                // todo: show error
            }
        }
    }

    override fun onActivateAccountSuccess() {
        // todo: play video
        Toaster.makeToast(this, "onActivateAccountSuccess")
        LoginManager.notifyEvent(this, LoginManager.RequestType.LOGIN, true)
        finish()
    }

    override fun onActivateAccountFail(exception: Exception?) {
        // todo: show error message
        Toaster.makeToast(this, exception?.message)
    }

    override fun showProgress() {
        l_progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        l_progress.visibility = View.GONE
    }
}