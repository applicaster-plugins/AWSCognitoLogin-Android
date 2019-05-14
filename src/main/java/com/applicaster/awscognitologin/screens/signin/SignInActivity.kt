package com.applicaster.awscognitologin.screens.signin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.applicaster.awscognitologin.R
import com.applicaster.awscognitologin.screens.confirmation.ConfirmationCodeActivity
import com.applicaster.awscognitologin.screens.signup.SignUpActivity
import com.applicaster.plugin_manager.login.LoginManager
import com.applicaster.util.ui.Toaster
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity(), SignInView {

    var signInPresenter: SignInPresenter = SignInPresenter(
            this, SignInInteractor())

    companion object {

        fun getCallingIntent(@NonNull context: Context): Intent {
            return Intent(context, SignInActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_sign_in)

        btn_sign_in.setOnClickListener {
            // todo: check if fields are not empty
            signInPresenter.signIn(et_user.text.toString(), et_password.text.toString())
        }

        tv_activate_account.setOnClickListener {
            startActivity(ConfirmationCodeActivity.getCallingIntent(this))
        }

        btn_sign_up.setOnClickListener {
            startActivity(SignUpActivity.getCallingIntent(this))
        }
    }

    override fun onSignInSuccess() {
        LoginManager.notifyEvent(this, LoginManager.RequestType.LOGIN, true)
    }

    override fun onSignInFail(message: String) {
        Toaster.makeToast(this, "onSignInFail")
    }

    override fun showProgress() {
        l_progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        l_progress.visibility = View.GONE
    }
}