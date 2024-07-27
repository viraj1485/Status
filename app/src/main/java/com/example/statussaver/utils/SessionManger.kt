package com.example.statussaver.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.statussaver.R

class SessionManger(var context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val WHATSAPP_ROOT_URI = "whatsapprooturi"
        const val WHATSAPP_PERMISSION_GRANTED = "whatsapppermissiongranted"
    }

    fun saveTreeUri(uri: String) {
        val editor = prefs.edit()
        editor.putString(WHATSAPP_ROOT_URI, uri)
        editor.apply()
    }

    fun getTreeUri(): String? {
        return prefs.getString(WHATSAPP_ROOT_URI, "")
    }

    fun isPermissionGranted(isPermissionGranted: Boolean) {
        val editor = prefs.edit()
        editor.putBoolean(WHATSAPP_PERMISSION_GRANTED, isPermissionGranted)
        editor.apply()
    }

    fun getIsUserPermissionGranted(): Boolean {
        return prefs.getBoolean(WHATSAPP_PERMISSION_GRANTED, false)
    }

}