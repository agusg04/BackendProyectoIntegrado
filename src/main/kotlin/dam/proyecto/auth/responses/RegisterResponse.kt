package dam.proyecto.auth.responses

import java.time.LocalDateTime

data class RegisterResponse(
    val nombre: String,
    val apellido1: String,
    val apellido2: String,
    val email: String,
    val fechaRegistro: LocalDateTime,
    val accessToken: String,
    val refreshToken: String
)
