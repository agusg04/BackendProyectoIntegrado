package dam.proyecto.auth.responses

data class LoginResponse(
    val nombre: String,
    val accessToken: String,
    val refreshToken: String
)

