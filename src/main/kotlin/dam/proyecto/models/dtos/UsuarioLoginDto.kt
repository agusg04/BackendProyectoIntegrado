package dam.proyecto.models.dtos

import dam.proyecto.models.enums.Roles
import java.io.Serializable
import java.time.LocalDateTime

/**
 * DTO for {@link dam.proyecto.models.dtos.UsuarioDto}
 */
data class UsuarioLoginDto(
    val id: Long,
    val email: String,
    val nombre: String,
    var apellido1: String,
    var apellido2: String,
    val contrasenia: String,
    val rol: Roles,
    var urlFotoPerfil: String?,
    val fechaRegistro: LocalDateTime
) : Serializable