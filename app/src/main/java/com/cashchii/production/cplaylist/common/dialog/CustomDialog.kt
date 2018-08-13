package com.cashchii.production.cplaylist.common.dialog

import android.app.Activity
import android.app.Dialog
import android.view.Window
import com.cashchii.production.cplaylist.R
import com.cashchii.production.cplaylist.common.Constant
import kotlinx.android.synthetic.main.dialog_custom.*


/**
 * Created by Panc. on 8/13/2018 AD.
 */
class CustomDialog {

    fun showDialog(activity: Activity, msg: String, positiveBtn: String, callback: ((String) -> Unit)?) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_custom)

        dialog.tvAsk.text = msg
        dialog.positiveBtn.text = positiveBtn
        dialog.positiveBtn.setOnClickListener {
            callback?.invoke(Constant.BTN.POSITIVE)
            dialog.dismiss()
        }

        dialog.negativeBtn.setOnClickListener {
            callback?.invoke(Constant.BTN.NEGATIVE)
            dialog.dismiss()
        }

        dialog.show()

    }
}