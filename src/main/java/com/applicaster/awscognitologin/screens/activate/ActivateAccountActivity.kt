package com.applicaster.awscognitologin.screens.activate

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.applicaster.awscognitologin.R
import com.applicaster.awscognitologin.plugin.PluginDataRepository
import com.applicaster.awscognitologin.screens.signin.SignInActivity
import com.applicaster.awscognitologin.utils.UIUtils
import com.applicaster.plugin_manager.login.LoginManager
import com.applicaster.util.ui.Toaster
import kotlinx.android.synthetic.main.activity_activate_account.*
import kotlinx.android.synthetic.main.activity_activate_account.l_progress
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

        applyStyles()

        setTexts()

        iv_back_aa.setOnClickListener {
            startActivity(SignInActivity.getCallingIntent(this))
            finish()
        }

        iv_close_aa.setOnClickListener {
            finish()
        }

        btn_activate.setOnClickListener {
            if(et_code_aa.text.isNotEmpty()) {
                activateAccountPresenter.activateAccount(et_code_aa.text.toString())
            } else {
                // todo: show error
                Toaster.makeToast(this, "An error ocurred")
            }
        }
    }

    private fun setTexts() {
        UIUtils.setText(tv_activate_account_title, "awsco_activation_code_title_txt")
        UIUtils.setText(tv_activate_account_description, "awsco_activation_code_desc_txt")
        UIUtils.setText(et_code_aa, "awsco_code_input_placeholder_txt")
        UIUtils.setText(tv_activate_btn, "awsco_activate_btn_txt")
        // todo: missing resend code
    }

    private fun applyStyles() {
        UIUtils.applyBackButtonStyle(iv_back_aa)

        UIUtils.applyCrossButtonStyle(v_close_aa, "awsco_close_button_color")

        cl_activate_account.setBackgroundColor(Color.parseColor(PluginDataRepository.INSTANCE.params?.get("awsco_bc_color").toString()))

        UIUtils.applyTitleStyle(tv_activate_account_title)

        UIUtils.applyDescriptionStyle(tv_activate_account_description)

        UIUtils.applyInputStyle(et_code_aa)

        UIUtils.applyButtonStyle(btn_activate, tv_activate_btn)

        UIUtils.applyLinkStyle(tv_resend_code)
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

    override fun onBackPressed() {
        startActivity(SignInActivity.getCallingIntent(this))
        finish()
    }
}