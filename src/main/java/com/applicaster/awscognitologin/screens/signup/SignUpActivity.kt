package com.applicaster.awscognitologin.screens.signup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.applicaster.awscognitologin.R
import com.applicaster.awscognitologin.screens.activate.ActivateAccountActivity
import com.applicaster.awscognitologin.screens.confirmation.ConfirmationCodeActivity
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
        btn_sign_up.setOnClickListener {
            if (validateFields()) {
                signUpPresenter.signUp(et_username_su.text.toString(),
                        et_email_su.text.toString(),
                        et_password_su.text.toString())
            }
        }
    }

    // returns true if all fields are filled
    private fun validateFields(): Boolean {
        // todo: check if username and email are not empty
        // todo: check if password and confirmPassword are equals
        return true
    }

    override fun onSignUpSuccess() {
        Toaster.makeToast(this, "onSignUpSuccess")
    }

    override fun onUserIsNotConfirmed(cognitoUserCodeDeliveryDetails: CognitoUserCodeDeliveryDetails) {
        Toaster.makeToast(this, "onUserIsNotConfirmed")
        startActivity(ActivateAccountActivity.getCallingIntent(this))
    }

    override fun onSignUpFailed(exception: Exception) {
        Toaster.makeToast(this, exception.message)
    }

    override fun showProgress() {
        v_progress.visibility = View.VISIBLE
        pb_progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        v_progress.visibility = View.GONE
        pb_progress.visibility = View.GONE
    }
}