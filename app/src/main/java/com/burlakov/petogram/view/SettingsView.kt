package com.burlakov.petogram.view

import android.net.Uri
import com.burlakov.petogram.model.User

interface SettingsView : MessageView {
    fun changeUsername(username: String)
    fun changeEmail(email: String)
    fun changePassword(oldPassword: String,newPassword : String)
    fun changeAvatar(uri: Uri)
    fun saveUser(user: User)
    fun LogOut(email: Boolean)
}