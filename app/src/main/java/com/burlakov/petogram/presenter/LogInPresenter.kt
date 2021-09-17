package com.burlakov.petogram.presenter

import com.burlakov.petogram.PetogramApplication
import com.burlakov.petogram.model.User
import com.burlakov.petogram.utils.LocaleException
import com.burlakov.petogram.view.LogInView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LogInPresenter(val viewState: LogInView) {
    val handler = LocaleException(viewState).handler

     fun logIn(email: String, password: String) = CoroutineScope(Dispatchers.Main).launch(handler) {

        val answer = PetogramApplication.userService.logIn(User(email, password))

        if (answer.user == null) {
            viewState.showMessage(
                answer.message, answer.isPositive
            )
        } else {
            viewState.goToMain(answer.user!!)
        }
         viewState.enableButton(true)

    }

     fun resetPassword(email: String) = CoroutineScope(Dispatchers.Default).launch(handler) {
        val answer = PetogramApplication.userService.forgotPassword(email)
         viewState.showMessage(answer.message, answer.isPositive)
    }
}