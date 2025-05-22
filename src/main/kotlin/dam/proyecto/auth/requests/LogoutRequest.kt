package dam.proyecto.auth.requests

data class
LogoutRequest(
    val accessToken: String,
    val refreshToken: String
)
