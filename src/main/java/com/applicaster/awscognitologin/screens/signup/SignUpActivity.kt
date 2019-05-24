package com.applicaster.awscognitologin.screens.signup

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import android.util.Patterns
import android.view.View
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails
import com.applicaster.awscognitologin.R
import com.applicaster.awscognitologin.plugin.PluginDataRepository
import com.applicaster.awscognitologin.screens.activate.ActivateAccountActivity
import com.applicaster.awscognitologin.screens.base.AWSActivity
import com.applicaster.awscognitologin.screens.signin.SignInActivity
import com.applicaster.awscognitologin.utils.UIUtils
import com.applicaster.plugin_manager.login.LoginManager
import com.applicaster.util.ui.Toaster
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AWSActivity(), SignUpView, View.OnClickListener {

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
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_back_reg -> {
                goTo(SignInActivity.getCallingIntent(this))
            }

            R.id.iv_close_reg -> finish()

            R.id.btn_sign_up -> {
                if(et_username_su.text.isEmpty()) {
                    tv_username_validation_su.visibility = View.VISIBLE
                    return
                }

                hideView(tv_username_validation_su)

                if(et_email_su.text.isEmpty()) {
                    tv_email_validation_su.visibility = View.VISIBLE
                    return
                }

                if(et_password_su.text.isEmpty()) {
                    tv_password_validation_su.visibility = View.VISIBLE
                    return
                }

                hideView(tv_password_validation_su)

                if(et_confirm_password_su.text.isEmpty()) {
                    tv_confirm_password_validation_su.visibility = View.VISIBLE
                    return
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(et_email_su.text.toString()).matches()) {
                    tv_email_validation_su.text = resources.getString(R.string.invalid_email)
                    tv_email_validation_su.visibility = View.VISIBLE
                    return
                }

                hideView(tv_email_validation_su)

                if(et_password_su.text.toString() != et_confirm_password_su.text.toString()) {
                    // todo: add this to localization
                    tv_confirm_password_validation_su.text =
                            resources.getString(R.string.your_passwords_are_not_equal)
                    tv_confirm_password_validation_su.visibility = View.VISIBLE
                    return
                }

                hideView(tv_confirm_password_validation_su)

                signUpPresenter.signUp(et_username_su.text.toString(),
                        et_email_su.text.toString(),
                        et_password_su.text.toString())
            }
        }
    }

    override fun setTexts() {
        UIUtils.setText(tv_registration_title, "awsco_registration_code_title_txt")
        UIUtils.setText(et_username_su, "awsco_user_input_placeholder_txt")
        UIUtils.setText(et_email_su, "awsco_email_input_placeholder_txt")
        UIUtils.setText(et_password_su, "awsco_password_input_placeholder_txt")
        UIUtils.setText(et_confirm_password_su, "awsco_password_confirmation_input_placeholder_txt")
        UIUtils.setText(tv_sign_up_btn, "awsco_registration_sign_up_btn_txt")
    }

    override fun applyStyles() {
        UIUtils.applyBackButtonStyle(iv_back_reg)
        UIUtils.applyCrossButtonStyle(v_close_reg, "awsco_close_button_color")
        cl_sign_up.setBackgroundColor(Color.parseColor(PluginDataRepository.INSTANCE.params?.get("awsco_bc_color").toString()))
        UIUtils.applyTitleStyle(tv_registration_title)
        UIUtils.applyInputStyle(et_username_su)
        UIUtils.applyInputStyle(et_email_su)
        UIUtils.applyInputStyle(et_password_su)
        UIUtils.applyInputStyle(et_confirm_password_su)
        UIUtils.applyButtonStyle(btn_sign_up, tv_sign_up_btn)
        UIUtils.addClearButtonToInput(rl_clear_username_su, et_username_su)
        UIUtils.addClearButtonToInput(rl_clear_email_su, et_email_su)
        UIUtils.addClearButtonToInput(rl_clear_password_su, et_password_su)
        UIUtils.addClearButtonToInput(rl_clear_confirm_password_su, et_confirm_password_su)
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