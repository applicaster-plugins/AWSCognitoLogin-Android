package com.applicaster.awscognitologin.screens.confirmation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import com.applicaster.awscognitologin.R
import com.applicaster.awscognitologin.screens.activate.ActivateAccountActivity
import kotlinx.android.synthetic.main.activity_confirmation_code.*

class ConfirmationCodeActivity : AppCompatActivity() {
    companion object {
        fun getCallingIntent(@NonNull context: Context): Intent {
            return Intent(context, ConfirmationCodeActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_confirmation_code)

        btn_send_code.setOnClickListener {
            startActivity(ActivateAccountActivity.getCallingIntent(this))
        }
    }
}