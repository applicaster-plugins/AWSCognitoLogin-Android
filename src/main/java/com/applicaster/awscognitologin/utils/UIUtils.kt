package com.applicaster.awscognitologin.utils

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.applicaster.awscognitologin.plugin.PluginDataRepository
import com.rengwuxian.materialedittext.MaterialEditText

class UIUtils {
    companion object {
        private val params = PluginDataRepository.INSTANCE.params

        fun applyInputStyle(inputField: MaterialEditText) {
            params?.let {
                inputField.underlineColor = Color.parseColor(params["awsco_input_ulc"].toString())
                inputField.setMetTextColor(Color.parseColor(params["awsco_input_txt_color"].toString()))
                inputField.setMetHintTextColor(Color.parseColor(params["awsco_validation_txt_color"].toString()))
                inputField.textSize = params["awsco_input_fontsize"].toString().toFloat()
                // todo: figure out how can i set the hint text size
            }
        }

        fun applyTitleStyle(title: TextView) {
            params?.let {
                title.setTextColor(Color.parseColor(params["awsco_view_title_color"].toString()))
                title.textSize = params["awsco_view_title_fontsize"].toString().toFloat()
            }
        }

        fun applyDescriptionStyle(description: TextView) {
            params?.let {
                description.setTextColor(Color.parseColor(params["awsco_view_desc_txt_color"].toString()))
                description.textSize = params["awsco_view_desc_txt_fontsize"].toString().toFloat()
            }
        }

        // button are made by linear layout and text view
        fun applyButtonStyle(button: LinearLayout, buttonText: TextView) {
            params?.let {
                val drawable = button.background

                if (drawable is GradientDrawable) {
                    drawable.setColor(Color.parseColor(params["awsco_btn_color"].toString()))
                    drawable.setStroke(1, Color.parseColor(params["awsco_btn_br_color"].toString()))
                }

                buttonText.textSize = params["awsco_btn_fontsize"].toString().toFloat()
                buttonText.setTextColor(Color.parseColor(params["awsco_btn_txt_color"].toString()))
            }
        }

        fun applyButtonStyle(button: LinearLayout, buttonText: TextView, buttonText2: TextView) {
            params?.let {
                applyButtonStyle(button, buttonText)

                buttonText2.textSize = params["awsco_btn_fontsize"].toString().toFloat()
                buttonText2.setTextColor(Color.parseColor(params["awsco_btn_txt_color"].toString()))
            }
        }

        fun applyLinkStyle(link: TextView) {
            params?.let {
                link.textSize = params["awsco_link_fontsize"].toString().toFloat()
                link.setTextColor(Color.parseColor(params["awsco_link_color"].toString()))
            }
        }

        fun setText(textView: TextView, textKey: String) {
            params?.let {
                textView.text = params[textKey].toString()
            }
        }

        fun setText(editText: MaterialEditText, hintTextKey: String) {
            params?.let {
                editText.hint = params[hintTextKey].toString()
            }
        }

        fun applyCrossButtonStyle(crossButton: View, keyValue: String) {
            params?.let {
                val drawable = crossButton.background
                if (drawable is GradientDrawable) {
                    drawable.setColor(Color.parseColor(params["awsco_close_button_color"].toString()))
                }
            }
        }

        fun applyBackButtonStyle(backButton: ImageView) {
            params?.let {
                backButton.setColorFilter(Color.parseColor(params["awsco_backbutton_color"].toString()))
            }
        }
    }
}