package com.applicaster.awscognitologin.screens.forgot

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ForgotPasswordContinuation
import com.applicaster.awscognitologin.R
import com.applicaster.awscognitologin.screens.signin.SignInActivity
import com.applicaster.awscognitologin.utils.UIUtils
import com.applicaster.util.ui.Toaster
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity(), ForgotPasswordView {

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

        UIUtils.applyInputStyle(et_username_fp)
        UIUtils.applyInputStyle(et_code_fp)
        UIUtils.applyInputStyle(et_new_password_fp)

        btn_forgot_password.setOnClickListener {
            // todo: check if edit texts is empty
            if (alreadySendUsername) {
                continuation.setVerificationCode(et_code_fp.text.toString())
                continuation.setPassword(et_new_password_fp.text.toString())
                continuation.continueTask()
            } else {
                forgotPasswordPresenter.forgotPassword(et_username_fp.text.toString())
            }
        }
    }

    override fun onForgotPasswordContinuation(continuation: ForgotPasswordContinuation) {
        alreadySendUsername = true
        this.continuation = continuation
        // since AWS forgot and reset password are together in the same method
        // we are using the same ui for both by hiding and showing elements
        tv_forgot_password_title.text = resources.getString(R.string.awsco_update_pwd_title_txt)
        tv_forgot_password_indication.text = resources.getString(R.string.awsco_update_pwd_desc_txt)
        et_username_fp.visibility = View.GONE
        et_code_fp.visibility = View.VISIBLE
        et_new_password_fp.visibility = View.VISIBLE
        tv_forgot_password_button_txt.text = resources.getText(R.string.awsco_update_pwd_btn_txt)
    }

    override fun onForgotPasswordSuccess() {
        getAlertDialog(this, resources.getString(R.string.dialog_title),
                resources.getString(R.string.dialog_description),
                resources.getString(R.string.dialog_positive_button_txt)).show()
    }

    fun getAlertDialog(context: Context, title: String, message: String,
                       positiveBtnText: String) : AlertDialog {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(positiveBtnText) { _, _ ->
            // todo: figure out how to put this in a separate method
            startActivity(SignInActivity.getCallingIntent(this))
            finish()
        }

        return builder.create()
    }

    override fun onForgotPasswordFail(error: String) {
        Toaster.makeToast(this, "onForgotPasswordFail")
    }

    override fun showProgress() {
        l_progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        l_progress.visibility = View.GONE
    }
}