package dam.proyecto.auth

import java.time.Instant

data class RegisterResponse(
    val nombre: String,
    val apellido1: String,
    val apellido2: String,
    val email: String,
    val fechaRegistro: Instant,
    val token: String
)
