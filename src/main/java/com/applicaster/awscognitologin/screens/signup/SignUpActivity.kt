package com.applicaster.awscognitologin.screens.signup

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails
import com.applicaster.awscognitologin.R
import com.applicaster.awscognitologin.plugin.PluginDataRepository
import com.applicaster.awscognitologin.screens.activate.ActivateAccountActivity
import com.applicaster.awscognitologin.screens.signin.SignInActivity
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

        setTexts()

        iv_close_reg.setOnClickListener {
            finish()
        }

        btn_sign_up.setOnClickListener {
            if (validateFields()) {
                signUpPresenter.signUp(et_username_su.text.toString(),
                        et_email_su.text.toString(),
                        et_password_su.text.toString())
            }
        }
    }

    private fun setTexts() {
        UIUtils.setText(tv_registration_title, "awsco_registration_code_title_txt")
        UIUtils.setText(et_username_su, "awsco_user_input_placeholder_txt")
        UIUtils.setText(et_email_su, "awsco_email_input_placeholder_txt")
        UIUtils.setText(et_password_su, "awsco_password_input_placeholder_txt")
        UIUtils.setText(et_confirm_password_su, "awsco_password_confirmation_input_placeholder_txt")
        UIUtils.setText(tv_sign_up_btn, "awsco_registration_sign_up_btn_txt")
    }

    private fun applyStyles() {
        UIUtils.applyCrossButtonStyle(v_close_reg, "awsco_close_button_color")

        cl_sign_up.setBackgroundColor(Color.parseColor(PluginDataRepository.INSTANCE.params?.get("awsco_bc_color").toString()))

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

    override fun onBackPressed() {
        startActivity(SignInActivity.getCallingIntent(this))
        finish()
    }
}