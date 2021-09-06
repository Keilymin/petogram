package com.burlakov.petogram.model


data class User(
    var id: Long?,
    var username: String?,
    var email: String,
    var password: String,
    var verificationCode: String?,
    var active: Boolean
) {
    constructor(email: String, password: String) : this(null, null, email, password, null, false)

    constructor() : this(null, null, "error", "error", null, false) {
    }

    fun equals(other: User): Boolean {
        return id == other.id &&
                username == other.username &&
                email == other.email &&
                password == other.password
    }
}