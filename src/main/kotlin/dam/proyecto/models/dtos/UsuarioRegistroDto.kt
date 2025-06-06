package dam.proyecto.models.dtos

import com.fasterxml.jackson.annotation.JsonFormat
import dam.proyecto.models.enums.Roles
import java.io.Serializable
import java.time.LocalDateTime

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
    var urlFoto: String?,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    var fechaRegistro: LocalDateTime
) : Serializable