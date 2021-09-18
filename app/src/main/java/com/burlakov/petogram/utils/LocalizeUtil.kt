package com.burlakov.petogram.utils

import android.content.Context
import android.content.res.Resources
import com.burlakov.petogram.R

class LocalizeUtil {
    companion object {
        fun localize(string: String, context: Context): String {
            val result: String
            when (string) {
                "Message send" -> {
                    result = context.getString(R.string.message_password_restore_send)
                    return result
                }
                "Not registered" -> {
                    result =context.getString(R.string.not_registered)
                    return result
                }
                "Password is decline" -> {
                    result = context.getString(R.string.password_changed_error)
                    return result
                }
                "Account is not activate" -> {
                    result = context.getString(R.string.acc_is_not_activate)
                    return result
                }
                "Wrong data" -> {
                    result = context.getString(R.string.wrong_password)
                    return result
                }
                "Saved" -> {
                    result = context.getString(R.string.registred)
                    return result
                }
            }
            return string
        }
    }
}