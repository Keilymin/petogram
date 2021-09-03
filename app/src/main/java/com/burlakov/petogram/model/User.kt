package com.burlakov.petogram.model

data class User(var username: String?, var email: String, var password: String) {
    constructor(email: String, password: String) : this(null, email, password)
}