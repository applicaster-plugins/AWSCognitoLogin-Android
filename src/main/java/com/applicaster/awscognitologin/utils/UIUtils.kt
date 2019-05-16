package com.applicaster.awscognitologin.utils

import android.graphics.Color
import com.applicaster.awscognitologin.plugin.PluginDataRepository
import com.rengwuxian.materialedittext.MaterialEditText

class UIUtils {
    companion object {
        fun applyInputStyle(inputField: MaterialEditText) {
            var params = PluginDataRepository.INSTANCE.params
            params?.let {
                inputField.underlineColor = Color.parseColor(params["awsco_input_ulc"].toString())
                inputField.setMetTextColor(Color.parseColor(params["awsco_input_txt_color"].toString()))
                inputField.setMetHintTextColor(Color.parseColor(params["awsco_validation_txt_color"].toString()))
                inputField.textSize = params["awsco_input_fontsize"].toString().toFloat()
                // todo: figure out how can i set the hint text size
            }
        }
    }
}