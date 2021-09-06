package com.burlakov.petogram.view

import com.burlakov.petogram.model.User
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface LogInView : SingUpView {
    @StateStrategyType(SingleStateStrategy::class)
    fun goToMain(user: User)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun resetPassword(email: String)
}