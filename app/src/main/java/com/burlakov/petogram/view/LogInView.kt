package com.burlakov.petogram.view

import com.burlakov.petogram.model.User

interface LogInView : SingUpView {
    fun goToMain(user: User)
    fun resetPassword(email: String)
}
