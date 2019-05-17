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
import com.applicaster.awscognitologin.utils.UIUtils
import com.applicaster.util.ui.Toaster
import kotlinx.android.synthetic.main.activity_confirmation_code.*
import kotlinx.android.synthetic.main.activity_confirmation_code.l_progress

class ConfirmationCodeActivity : AppCompatActivity(), ConfirmationCodeView {

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

        applyStyles()

        setTexts()

        iv_close_cc.setOnClickListener {
            finish()
        }

        btn_send_code.setOnClickListener {
            confirmationCodePresenter.sendConfirmationCode(et_username_cc.text.toString())
        }
    }

    private fun setTexts() {
        UIUtils.setText(tv_confirmation_code_title, "awsco_confirmation_title_txt")
        UIUtils.setText(tv_confirmation_code_description, "awsco_confirmation_desc_txt")
        UIUtils.setText(tv_send_code_btn, "awsco_send_code_btn_txt")
    }

    private fun applyStyles() {
        UIUtils.applyCrossButtonStyle(v_close_cc, "awsco_close_button_color")

        cl_confirmation_code.setBackgroundColor(Color.parseColor(PluginDataRepository.INSTANCE.params?.get("awsco_bc_color").toString()))

        UIUtils.applyTitleStyle(tv_confirmation_code_title)

        UIUtils.applyDescriptionStyle(tv_confirmation_code_description)

        UIUtils.applyInputStyle(et_username_cc)

        UIUtils.applyButtonStyle(btn_send_code, tv_send_code_btn)
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