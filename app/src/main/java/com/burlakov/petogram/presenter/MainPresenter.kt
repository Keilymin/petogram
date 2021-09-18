package com.burlakov.petogram.presenter


import com.burlakov.petogram.PetogramApplication
import com.burlakov.petogram.utils.LocaleException
import com.burlakov.petogram.view.MainView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainPresenter(val viewState: MainView) {
    val handler = LocaleException(viewState).handler

    fun userDataIsOk() = CoroutineScope(Dispatchers.Main).launch(handler) {

        val curUser = PetogramApplication.user

        val answer = PetogramApplication.userService.checkUser(curUser!!)

        if (answer.user == null) {
            viewState.userDataError(answer.message)
        } else {
            if (!(answer.user!!.equals(PetogramApplication.user!!))) {
                viewState.saveUser(answer.user!!)
            }
            if (curUser.username == null) {
                viewState.toSetUsername()
            } else {
                viewState.setAvatarAndUserData()
            }
        }

    }
}