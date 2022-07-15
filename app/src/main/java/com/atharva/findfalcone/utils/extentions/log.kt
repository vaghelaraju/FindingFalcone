package com.atharva.findfalcone.utils.extentions

import android.util.Log
import com.atharva.findfalcone.BuildConfig

fun log(tag: String, msg: String) {
    if (BuildConfig.DEBUG) {
        try {
            Log.d(tag, msg)
        } catch (e: Exception) {
        }
    }
}

