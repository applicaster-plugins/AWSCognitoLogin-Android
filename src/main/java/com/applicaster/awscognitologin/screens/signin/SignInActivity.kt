package com.applicaster.awscognitologin.screens.signin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import com.applicaster.awscognitologin.R
import com.applicaster.awscognitologin.screens.activate.ActivateAccountActivity
import com.applicaster.awscognitologin.screens.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    companion object {
        fun getCallingIntent(@NonNull context: Context): Intent {
            return Intent(context, SignInActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_sign_in)

        tv_activate_account.setOnClickListener {
            startActivity(ActivateAccountActivity.getCallingIntent(this))
        }

        btn_sign_up.setOnClickListener {
            startActivity(SignUpActivity.getCallingIntent(this))
        }
    }
}