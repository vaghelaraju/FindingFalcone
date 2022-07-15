package com.atharva.findfalcone.utils.extentions

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.atharva.findfalcone.BuildConfig
import com.atharva.findfalcone.R

fun Context.alert(message: CharSequence) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(R.string.alert)
    builder.setMessage(message)
    builder.setIcon(android.R.drawable.ic_dialog_alert)
    builder.setPositiveButton("ok"){dialogInterface, which ->
    }
    val alertDialog: AlertDialog = builder.create()
    alertDialog.setCancelable(false)
    alertDialog.show()

}

