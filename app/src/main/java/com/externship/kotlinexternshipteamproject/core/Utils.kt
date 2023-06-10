package com.externship.kotlinexternshipteamproject.core

import android.util.Log
import com.externship.kotlinexternshipteamproject.core.Constants.TAG

class Utils {
    companion object {
        fun print(e: Exception) = Log.e(TAG, e.stackTraceToString())
    }
}