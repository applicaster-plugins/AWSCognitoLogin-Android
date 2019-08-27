package com.applicaster.awscognitologin.utils

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import com.applicaster.awscognitologin.R
import com.applicaster.awscognitologin.plugin.PluginDataRepository
import com.applicaster.awscognitologin.screens.signin.SignInActivity
import com.applicaster.util.StringUtil
import com.rengwuxian.materialedittext.MaterialEditText
import kotlinx.android.synthetic.main.activity_sign_in.*
import java.util.regex.Matcher
import java.util.regex.Pattern

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

        fun getPasswordFailMessage(context: Context): String {
            params?.let {
                val message = params["awsco_password_error_message"].toString()
                return if (message != "null") message else context.getString(R.string.on_sign_up_failed_message)
            }

            return context.getString(R.string.on_sign_up_failed_message)
        }

        fun getAlertDialog(context: Context, title: String, message: String,
                           positiveBtnText: String)
                : AlertDialog {
            return getAlertDialog(context, title, message, positiveBtnText,
                    DialogInterface.OnClickListener { dialogInterface, _ -> dialogInterface.dismiss() })
        }

        fun getAlertDialog(context: Context, title: String, message: String,
                                   positiveBtnText: String, listener: DialogInterface.OnClickListener)
                : AlertDialog {
            val builder = AlertDialog.Builder(context, R.style.AlertDialogCustom)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setPositiveButton(positiveBtnText, listener)

            return builder.create()
        }

        fun addClearButtonToInput(ivClearMessage: RelativeLayout, inputField: EditText) {
            inputField.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    // do nothing
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    // do nothing
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    p0?.let {
                        ivClearMessage.visibility = if (p0.isEmpty()) View.INVISIBLE else View.VISIBLE
                        ivClearMessage.setOnClickListener {
                            inputField.text.clear()
                        }
                    }
                }

            })
        }

        fun checkIfUsernameMatchesPattern(string: String): Boolean {
            val patternStr = "^(?=.{8,20}\$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])\$"
            val pattern: Pattern = Pattern.compile(patternStr)
            val matcher: Matcher = pattern.matcher(string)
            return matcher.matches()
        }
    }
}
