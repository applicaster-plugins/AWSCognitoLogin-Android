package com.applicaster.awscognitologin.screens.base

import android.support.v7.app.AppCompatActivity
import android.util.Log

abstract class AWSActivity : AppCompatActivity() {
    abstract fun applyStyles()

    abstract fun setTexts()

    fun applyConfig() {
        try {
            applyStyles()
            setTexts()
        } catch (exception: Exception) {
            Log.e(this.javaClass.simpleName,
                    String.format("There was an error applying style parameters. " +
                            "Make sure style parameters are correctly configured. " +
                            "More details: %s", exception.message))
        }
    }

    override fun onStart() {
        super.onStart()
        applyConfig()
    }
}
