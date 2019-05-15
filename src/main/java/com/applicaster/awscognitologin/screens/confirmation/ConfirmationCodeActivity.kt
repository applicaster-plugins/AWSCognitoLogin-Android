package com.applicaster.awscognitologin.screens.confirmation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.applicaster.awscognitologin.R
import com.applicaster.awscognitologin.screens.activate.ActivateAccountActivity
import com.applicaster.util.ui.Toaster
import kotlinx.android.synthetic.main.activity_confirmation_code.*

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

        btn_send_code.setOnClickListener {
            confirmationCodePresenter.sendConfirmationCode(et_username_cc.text.toString())
        }
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