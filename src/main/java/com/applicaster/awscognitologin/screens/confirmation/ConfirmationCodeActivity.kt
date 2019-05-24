package com.applicaster.awscognitologin.screens.confirmation

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.applicaster.awscognitologin.R
import com.applicaster.awscognitologin.plugin.PluginDataRepository
import com.applicaster.awscognitologin.screens.activate.ActivateAccountActivity
import com.applicaster.awscognitologin.screens.base.AWSActivity
import com.applicaster.awscognitologin.screens.signin.SignInActivity
import com.applicaster.awscognitologin.utils.UIUtils
import com.applicaster.util.ui.Toaster
import kotlinx.android.synthetic.main.activity_confirmation_code.*
import kotlinx.android.synthetic.main.activity_confirmation_code.l_progress

class ConfirmationCodeActivity : AWSActivity(), ConfirmationCodeView, View.OnClickListener {

    var confirmationCodePresenter: ConfirmationCodePresenter = ConfirmationCodePresenter(
            this, ConfirmationCodeInteractor())

    companion object {
        fun getCallingIntent(@NonNull context: Context): Intent {
            return Intent(context, ConfirmationCodeActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_confirmation_code)
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.iv_back_cc -> goTo(SignInActivity.getCallingIntent(this))

            R.id.iv_close_cc -> finish()

            R.id.btn_send_code -> {
                if(et_username_cc.text.isEmpty()) {
                    tv_username_validation_cc.visibility = View.VISIBLE
                    return
                }

                hideView(tv_username_validation_cc)

                confirmationCodePresenter.sendConfirmationCode(et_username_cc.text.toString())
            }
        }
    }

    override fun setTexts() {
        UIUtils.setText(tv_confirmation_code_title, "awsco_confirmation_title_txt")
        UIUtils.setText(tv_confirmation_code_description, "awsco_confirmation_desc_txt")
        UIUtils.setText(tv_send_code_btn, "awsco_send_code_btn_txt")
    }

    override fun applyStyles() {
        UIUtils.applyBackButtonStyle(iv_back_cc)
        UIUtils.applyCrossButtonStyle(v_close_cc, "awsco_close_button_color")
        cl_confirmation_code.setBackgroundColor(Color.parseColor(PluginDataRepository.INSTANCE.params?.get("awsco_bc_color").toString()))
        UIUtils.applyTitleStyle(tv_confirmation_code_title)
        UIUtils.applyDescriptionStyle(tv_confirmation_code_description)
        UIUtils.applyInputStyle(et_username_cc)
        UIUtils.applyButtonStyle(btn_send_code, tv_send_code_btn)
        UIUtils.addClearButtonToInput(rl_clear_username_cc, et_username_cc)
    }

    override fun onConfirmationCodeSuccess() {
        startActivity(ActivateAccountActivity.getCallingIntent(this))
        finish()
    }

    override fun onConfirmationCodeFail(error: String) {
        Toaster.makeToast(this, error)
    }

    override fun showProgress() {
        l_progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        l_progress.visibility = View.GONE
    }
}