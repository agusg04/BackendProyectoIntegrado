package dam.proyecto.auth.responses

import dam.proyecto.data.UserData

data class LoginResponse(
    val userData: UserData,
    val accessToken: String,
    val refreshToken: String,
)

