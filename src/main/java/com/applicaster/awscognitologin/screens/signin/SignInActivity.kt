package com.applicaster.awscognitologin.screens.signin

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.applicaster.awscognitologin.R
import com.applicaster.awscognitologin.plugin.PluginDataRepository
import com.applicaster.awscognitologin.screens.confirmation.ConfirmationCodeActivity
import com.applicaster.awscognitologin.screens.forgot.ForgotPasswordActivity
import com.applicaster.awscognitologin.screens.signup.SignUpActivity
import com.applicaster.awscognitologin.utils.UIUtils
import com.applicaster.plugin_manager.login.LoginManager
import com.applicaster.util.ui.Toaster
import com.squareup.picasso.Picasso
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

        applyStyles()

        setTexts()

        iv_close_si.setOnClickListener {
            finish()
        }

        btn_sign_in.setOnClickListener {
            // todo: check if fields are not empty
            signInPresenter.signIn(et_user.text.toString(), et_password.text.toString())
        }

        tv_activate_account.setOnClickListener {
            goTo(ConfirmationCodeActivity.getCallingIntent(this))
        }

        btn_sign_up.setOnClickListener {
            goTo(SignUpActivity.getCallingIntent(this))
        }

        tv_forgot_password.setOnClickListener {
            goTo(ForgotPasswordActivity.getCallingIntent(this))
        }
    }

    private fun setTexts() {
        UIUtils.setText(tv_sign_in_title, "awsco_signin_title_text")
        UIUtils.setText(et_user, "awsco_user_input_placeholder_txt")
        UIUtils.setText(et_password, "awsco_password_input_placeholder_txt")
        UIUtils.setText(tv_forgot_password, "awsco_forpas_txt")
        UIUtils.setText(tv_activate_account, "awsco_actacc_txt")
        UIUtils.setText(tv_sign_up_question_btn, "awsco_signup_btn_qt_txt")
        UIUtils.setText(tv_sign_up_answer_btn, "awsco_signup_btn_answ_text")

    }

    private fun applyStyles() {
        UIUtils.applyCrossButtonStyle(v_close_si, "awsco_close_button_color")

        cl_sign_in.setBackgroundColor(Color.parseColor(PluginDataRepository.INSTANCE.params?.get("awsco_bc_color").toString()))

        Picasso.get()
                .load(PluginDataRepository.INSTANCE.params?.get("awsco_logo_image").toString())
                .into(iv_logo)

        UIUtils.applyTitleStyle(tv_sign_in_title)

        UIUtils.applyInputStyle(et_user)
        UIUtils.applyInputStyle(et_password)

        UIUtils.applyButtonStyle(btn_sign_in, tv_sign_in_btn)
        UIUtils.applyButtonStyle(btn_sign_up, tv_sign_up_question_btn, tv_sign_up_answer_btn)

        UIUtils.applyLinkStyle(tv_forgot_password)
        UIUtils.applyLinkStyle(tv_activate_account)
    }

    fun goTo(intent: Intent) {
        startActivity(intent)
        finish()
    }

    override fun onSignInSuccess() {
        LoginManager.notifyEvent(this, LoginManager.RequestType.LOGIN, true)
        finish()
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