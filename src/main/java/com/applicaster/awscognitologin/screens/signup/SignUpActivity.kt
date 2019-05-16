package com.applicaster.awscognitologin.screens.signup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails
import com.applicaster.awscognitologin.R
import com.applicaster.awscognitologin.screens.activate.ActivateAccountActivity
import com.applicaster.awscognitologin.utils.UIUtils
import com.applicaster.plugin_manager.login.LoginManager
import com.applicaster.util.ui.Toaster
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity(), SignUpView {

    var signUpPresenter: SignUpPresenter = SignUpPresenter(
            this, SignUpInteractor())

    companion object {
        fun getCallingIntent(@NonNull context: Context): Intent {
            return Intent(context, SignUpActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_sign_up)

        applyStyles()

        btn_sign_up.setOnClickListener {
            if (validateFields()) {
                signUpPresenter.signUp(et_username_su.text.toString(),
                        et_email_su.text.toString(),
                        et_password_su.text.toString())
            }
        }
    }

    private fun applyStyles() {
        UIUtils.applyTitleStyle(tv_registration_title)

        UIUtils.applyInputStyle(et_username_su)
        UIUtils.applyInputStyle(et_email_su)
        UIUtils.applyInputStyle(et_password_su)
        UIUtils.applyInputStyle(et_confirm_password_su)

        UIUtils.applyButtonStyle(btn_sign_up, tv_sign_up_btn)
    }

    // returns true if all fields are filled
    private fun validateFields(): Boolean {
        // todo: check if username and email are not empty
        // todo: check if password and confirmPassword are equals
        return true
    }

    override fun onSignUpSuccess() {
        LoginManager.notifyEvent(this, LoginManager.RequestType.LOGIN, true)
        finish()
    }

    override fun onUserIsNotConfirmed(cognitoUserCodeDeliveryDetails: CognitoUserCodeDeliveryDetails) {
        Toaster.makeToast(this, "onUserIsNotConfirmed")
        startActivity(ActivateAccountActivity.getCallingIntent(this))
        finish()
    }

    override fun onSignUpFailed(exception: Exception) {
        Toaster.makeToast(this, exception.message)
    }

    override fun showProgress() {
        l_progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        l_progress.visibility = View.GONE
    }
}