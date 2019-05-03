package com.applicaster.awscognitologin.screens.activate

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import com.applicaster.awscognitologin.R

class ActivateAccountActivity : AppCompatActivity() {
    companion object {
        fun getCallingIntent(@NonNull context: Context): Intent {
            return Intent(context, ActivateAccountActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_activate_account)
    }
}