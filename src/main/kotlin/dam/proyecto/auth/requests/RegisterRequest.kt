package dam.proyecto.auth.requests

data class RegisterRequest(
    val name: String,
    val lastName1: String,
    val lastName2: String,
    val email: String,
    val password: String
)
