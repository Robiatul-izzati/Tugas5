package com.example.pasienapp.data.model

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val data: LoginData?,
    val errors: Any?
)

data class LoginData(
    val token: String,
    val user: UserData
)

data class UserData(
    val id: Int,
    val name: String,
    val email: String,
    val role: String,
    val is_active: Boolean
)