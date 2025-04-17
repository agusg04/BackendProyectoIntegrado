package dam.proyecto

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

fun main() {
    val rawPassword = "admin"
    val encoder = BCryptPasswordEncoder()
    val encodedPassword = encoder.encode(rawPassword)

    println("Contraseña original: $rawPassword")
    println("Contraseña codificada: $encodedPassword")

    val match = encoder.matches(rawPassword, encodedPassword)
    println("¿Coincide? $match")
}
