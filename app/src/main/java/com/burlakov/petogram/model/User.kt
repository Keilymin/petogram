package com.burlakov.petogram.model


data class User(var id: Long?,
                var username: String?,
                var email: String,
                var password: String,
                var verificationCode: String?,
                var active: Boolean,
                var avatar: String?) {
    constructor(email: String, password: String) : this(null, null, email, password, null, false, null)

    constructor() : this(null, null, "error", "error", null, false, null) {
    }

    fun equals(other: User): Boolean {
        return id == other.id &&
                username == other.username &&
                email == other.email &&
                password == other.password &&
                avatar == other.avatar
    }
}