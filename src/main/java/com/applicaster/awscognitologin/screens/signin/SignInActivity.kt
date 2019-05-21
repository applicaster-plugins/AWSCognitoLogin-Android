package com.applicaster.awscognitologin.screens.signin

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.NonNull
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.applicaster.awscognitologin.R
import com.applicaster.awscognitologin.plugin.PluginDataRepository
import com.applicaster.awscognitologin.screens.base.AWSActivity
import com.applicaster.awscognitologin.screens.confirmation.ConfirmationCodeActivity
import com.applicaster.awscognitologin.screens.forgot.ForgotPasswordActivity
import com.applicaster.awscognitologin.screens.signup.SignUpActivity
import com.applicaster.awscognitologin.utils.UIUtils
import com.applicaster.plugin_manager.login.LoginManager
import com.applicaster.util.ui.Toaster
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AWSActivity(), SignInView, View.OnClickListener {

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

        // todo: make this generic
        et_username_si.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                // do nothing
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // do nothing
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0?.let {
                    iv_clear_username_si.visibility = if (p0.isEmpty()) View.INVISIBLE else View.VISIBLE
                }
            }

        })

        et_password_si.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                // do nothing
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // do nothing
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0?.let {
                    
                }
            }

        })
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_sign_in -> {
                if (et_username_si.text.isEmpty()) {
                    tv_username_validation.visibility = View.VISIBLE
                    return
                }

                if (et_password_si.text.isEmpty()) {
                    tv_password_validation.visibility = View.VISIBLE
                    return
                }

                signInPresenter.signIn(et_username_si.text.toString(), et_password_si.text.toString())
            }

            R.id.iv_close_si -> finish()

            R.id.tv_activate_account -> goTo(ConfirmationCodeActivity.getCallingIntent(this))

            R.id.btn_sign_up -> goTo(SignUpActivity.getCallingIntent(this))

            R.id.tv_forgot_password -> goTo(ForgotPasswordActivity.getCallingIntent(this))

            R.id.iv_clear_username_si -> et_username_si.text.clear()
        }
    }

    override fun setTexts() {
        UIUtils.setText(tv_sign_in_title, "awsco_signin_title_text")
        UIUtils.setText(et_username_si, "awsco_user_input_placeholder_txt")
        UIUtils.setText(et_password_si, "awsco_password_input_placeholder_txt")
        UIUtils.setText(tv_forgot_password, "awsco_forpas_txt")
        UIUtils.setText(tv_activate_account, "awsco_actacc_txt")
        UIUtils.setText(tv_sign_up_question_btn, "awsco_signup_btn_qt_txt")
        UIUtils.setText(tv_sign_up_answer_btn, "awsco_signup_btn_answ_text")
    }

    override fun applyStyles() {
        UIUtils.applyCrossButtonStyle(v_close_si, "awsco_close_button_color")
        cl_sign_in.setBackgroundColor(Color.parseColor(PluginDataRepository.INSTANCE.params?.get("awsco_bc_color").toString()))
        Picasso.get()
                .load(PluginDataRepository.INSTANCE.params?.get("awsco_logo_image").toString())
                .into(iv_logo)
        UIUtils.applyTitleStyle(tv_sign_in_title)
        UIUtils.applyInputStyle(et_username_si)
        UIUtils.applyInputStyle(et_password_si)
        UIUtils.applyButtonStyle(btn_sign_in, tv_sign_in_btn)
        UIUtils.applyButtonStyle(btn_sign_up, tv_sign_up_question_btn, tv_sign_up_answer_btn)
        UIUtils.applyLinkStyle(tv_forgot_password)
        UIUtils.applyLinkStyle(tv_activate_account)
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