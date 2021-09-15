package com.burlakov.petogram.view

import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface UsernameView : MessageView {
    @StateStrategyType(SingleStateStrategy::class)
    fun backToMain()
}