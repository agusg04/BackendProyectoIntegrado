package dam.proyecto.models.dtos

import dam.proyecto.models.enums.Roles
import java.io.Serializable
import java.time.Instant

/**
 * DTO for {@link dam.proyecto.models.entities.Usuario}
 */
data class UsuarioRegistroDto(
    var email: String,
    var nombre: String,
    var apellido1: String,
    var apellido2: String,
    var contrasenia: String,
    var rol: Roles,
    var fechaRegistro: Instant
) : Serializable