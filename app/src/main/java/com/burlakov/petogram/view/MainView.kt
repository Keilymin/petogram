package com.burlakov.petogram.view

import com.burlakov.petogram.model.User

interface MainView : MessageView {
    fun userDataError(message: String)
    fun saveUser(user: User)
    fun toSetUsername()
    fun setAvatarAndUserData()
}