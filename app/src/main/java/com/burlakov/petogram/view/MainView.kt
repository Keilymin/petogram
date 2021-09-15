package com.burlakov.petogram.view

import com.burlakov.petogram.model.User
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface MainView : MessageView {
    @StateStrategyType(SingleStateStrategy::class)
    fun userDataError(message: String)
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun saveUser(user: User)
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun toSetUsername()
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setAvatarAndUserData()
}