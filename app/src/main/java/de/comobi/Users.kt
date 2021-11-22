package de.comobi

import android.content.Context

import android.content.SharedPreferences
import androidx.preference.PreferenceManager

/**
Class is used in Chat.kt to store data in Firebase
 **/
data class Users(val email: String? = null, val username: String? = null, val carModel: String? = null){
}


/**
This function suppose to save userdata as cache, but not implemented till the end
 **/
class SaveSharedPreference {
    val PREF_USER_NAME = "username"
    fun getSharedPreferences(ctx: Context?): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(ctx)
    }

    fun setUserName(ctx: Context?, userName: String?) {
        val editor: SharedPreferences.Editor = getSharedPreferences(ctx).edit()
        editor.putString(PREF_USER_NAME, userName)
        editor.commit()
    }

    fun getUserName(ctx: Context?): String? {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "")
    }
}