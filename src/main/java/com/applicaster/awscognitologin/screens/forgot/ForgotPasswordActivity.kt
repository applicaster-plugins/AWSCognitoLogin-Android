package com.applicaster.awscognitologin.screens.forgot

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ForgotPasswordContinuation
import com.applicaster.awscognitologin.R
import com.applicaster.awscognitologin.plugin.PluginDataRepository
import com.applicaster.awscognitologin.screens.base.AWSActivity
import com.applicaster.awscognitologin.screens.signin.SignInActivity
import com.applicaster.awscognitologin.utils.UIUtils
import com.applicaster.awscognitologin.utils.UIUtils.Companion.getAlertDialog
import com.applicaster.util.ui.Toaster
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_forgot_password.l_progress

class ForgotPasswordActivity : AWSActivity(), ForgotPasswordView, View.OnClickListener {

    var alreadySendUsername = false
    lateinit var continuation: ForgotPasswordContinuation

    var forgotPasswordPresenter: ForgotPasswordPresenter =
            ForgotPasswordPresenter(this, ForgotPasswordInteractor())

    companion object {

        fun getCallingIntent(@NonNull context: Context): Intent {
            return Intent(context, ForgotPasswordActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_forgot_password)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_back_fp -> goTo(SignInActivity.getCallingIntent(this))

            R.id.iv_close_fp -> finish()

            R.id.btn_forgot_password -> {
                if (alreadySendUsername) {

                    et_code_fp.text?.let {
                        if (it.isEmpty()) {
                            tv_code_validation_fp.visibility = View.VISIBLE
                            return
                        }
                    }


                    hideView(tv_code_validation_fp)

                    et_new_password_fp.text?.let {
                        if (it.isEmpty()) {
                            tv_new_password_validation_fp.visibility = View.VISIBLE
                            return
                        }
                    }

                    hideView(tv_new_password_validation_fp)

                    continuation.setVerificationCode(et_code_fp.text.toString())
                    continuation.setPassword(et_new_password_fp.text.toString())
                    continuation.continueTask()
                } else {
                    et_username_fp.text?.let {
                        if (it.isEmpty()) {
                            tv_username_validation_fp.visibility = View.VISIBLE
                            return
                        }
                    }

                    forgotPasswordPresenter.forgotPassword(et_username_fp.text.toString())
                }
            }
        }
    }

    override fun setTexts() {
        UIUtils.setText(tv_forgot_password_title, "awsco_forgot_pwd_title_txt")
        UIUtils.setText(tv_forgot_password_description, "awsco_forgot_pwd_desc_txt")
        UIUtils.setText(et_username_fp, "awsco_user_input_placeholder_txt")
        UIUtils.setText(tv_forgot_password_btn, "awsco_send_forgot_pwd_btn_txt")
    }

    override fun applyStyles() {
        UIUtils.applyBackButtonStyle(iv_back_fp)
        UIUtils.applyCrossButtonStyle(v_close_fp, "awsco_close_button_color")
        cl_forgot_password.setBackgroundColor(Color.parseColor(PluginDataRepository.INSTANCE.params?.get("awsco_bc_color").toString()))
        UIUtils.applyTitleStyle(tv_forgot_password_title)
        UIUtils.applyDescriptionStyle(tv_forgot_password_description)
        UIUtils.applyInputStyle(et_username_fp)
        UIUtils.applyInputStyle(et_code_fp)
        UIUtils.applyInputStyle(et_new_password_fp)
        UIUtils.applyButtonStyle(btn_forgot_password, tv_forgot_password_btn)
        UIUtils.addClearButtonToInput(rl_clear_username_fp, et_username_fp)
        UIUtils.addClearButtonToInput(rl_clear_code_fp, et_code_fp)
        UIUtils.addClearButtonToInput(rl_clear_new_password_fp, et_new_password_fp)
    }

    override fun onForgotPasswordContinuation(continuation: ForgotPasswordContinuation) {
        alreadySendUsername = true
        this.continuation = continuation
        // since AWS forgot and reset password are together in the same method
        // we are using the same ui for both by hiding and showing elements
        rl_username_fp.visibility = View.GONE
        tv_username_validation_fp.visibility = View.GONE
        et_code_fp.visibility = View.VISIBLE
        tv_code_validation_fp.visibility = View.INVISIBLE
        et_new_password_fp.visibility = View.VISIBLE
        tv_new_password_validation_fp.visibility = View.INVISIBLE

        UIUtils.setText(tv_forgot_password_title, "awsco_update_pwd_title_txt")
        UIUtils.setText(tv_forgot_password_description, "awsco_update_pwd_desc_txt")
        UIUtils.setText(tv_forgot_password_btn, "awsco_update_pwd_btn_txt")
        UIUtils.setText(et_new_password_fp, "awsco_new_pwd_input_placeholder_txt")
        UIUtils.setText(et_code_fp, "awsco_code_input_placeholder_txt")
    }

    override fun onForgotPasswordSuccess() {
        getAlertDialog(this, resources.getString(R.string.dialog_title),
                resources.getString(R.string.dialog_description),
                resources.getString(R.string.dialog_positive_button_txt),
                DialogInterface.OnClickListener { _, _ -> goTo(SignInActivity.getCallingIntent(this)) })
                .show()
    }

    override fun onForgotPasswordFail(error: String) {
        UIUtils.getAlertDialog(this, this.getString(R.string.on_forgot_password_failed_title),
                this.getString(R.string.on_forgot_password_failed_message), this.getString(R.string.ok_btn))
                .show()
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