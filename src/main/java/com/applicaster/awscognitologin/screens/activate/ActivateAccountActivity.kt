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
import com.applicaster.awscognitologin.screens.base.AWSActivity
import com.applicaster.awscognitologin.screens.signin.SignInActivity
import com.applicaster.awscognitologin.utils.UIUtils
import com.applicaster.plugin_manager.login.LoginManager
import com.applicaster.util.ui.Toaster
import com.google.android.gms.signin.SignIn
import kotlinx.android.synthetic.main.activity_activate_account.*
import kotlinx.android.synthetic.main.activity_activate_account.l_progress
import java.lang.Exception

class ActivateAccountActivity : AWSActivity(), ActivateAccountView, View.OnClickListener {

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
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_back_aa -> goTo(SignInActivity.getCallingIntent(this))
            R.id.iv_close_aa -> finish()
            R.id.btn_activate -> {
                if (et_code_aa.text.toString().isEmpty()) {
                    tv_code_validation_aa.visibility = View.VISIBLE
                    return
                }

                hideView(tv_code_validation_aa)

                activateAccountPresenter.activateAccount(et_code_aa.text.toString())
            }
        }
    }

    override fun setTexts() {
        UIUtils.setText(tv_activate_account_title, "awsco_activation_code_title_txt")
        UIUtils.setText(tv_activate_account_description, "awsco_activation_code_desc_txt")
        UIUtils.setText(et_code_aa, "awsco_code_input_placeholder_txt")
        UIUtils.setText(tv_activate_btn, "awsco_activate_btn_txt")
    }

    override fun applyStyles() {
        UIUtils.applyBackButtonStyle(iv_back_aa)
        UIUtils.applyCrossButtonStyle(v_close_aa, "awsco_close_button_color")
        cl_activate_account.setBackgroundColor(Color.parseColor(PluginDataRepository.INSTANCE.params?.get("awsco_bc_color").toString()))
        UIUtils.applyTitleStyle(tv_activate_account_title)
        UIUtils.applyDescriptionStyle(tv_activate_account_description)
        UIUtils.applyInputStyle(et_code_aa)
        UIUtils.applyButtonStyle(btn_activate, tv_activate_btn)
        UIUtils.applyLinkStyle(tv_resend_code)
        UIUtils.addClearButtonToInput(rl_clear_code_aa, et_code_aa)
    }

    override fun onActivateAccountSuccess() {
        Toaster.makeToast(this, this.getString(R.string.on_activate_account_success))
        LoginManager.notifyEvent(this, LoginManager.RequestType.LOGIN, true)
        finish()
    }

    override fun onActivateAccountFail(exception: Exception?) {
        UIUtils.getAlertDialog(this, this.getString(R.string.on_activate_account_failed_title),
                this.getString(R.string.on_activate_account_failed_message), this.getString(R.string.ok_btn))
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