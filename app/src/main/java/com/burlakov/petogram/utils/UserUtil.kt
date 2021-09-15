package com.burlakov.petogram.utils

import android.content.Context
import android.content.SharedPreferences
import com.burlakov.petogram.model.User

class UserUtil {
    companion object {
        public fun saveUser(user: User, context: Context) {

            val editor: SharedPreferences.Editor =
                context.getSharedPreferences("user_data", Context.MODE_PRIVATE).edit()
            editor.putLong("id", user.id!!)
            editor.putString("password", user.password)
            editor.putString("email", user.email)
            user.avatar.let { editor.putString("avatar", user.avatar) }
            user.username.let { editor.putString("username", user.username) }

            editor.apply()
        }

        public fun clear(context: Context) {
            context.getSharedPreferences("user_data", Context.MODE_PRIVATE)
                    .edit().clear().apply()
        }
    }
}