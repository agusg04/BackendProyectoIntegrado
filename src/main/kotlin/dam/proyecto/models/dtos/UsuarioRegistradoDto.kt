package dam.proyecto.models.dtos

import dam.proyecto.models.enums.Roles
import java.io.Serializable
import java.time.LocalDateTime

data class UsuarioRegistradoDto(
    var id: Long,
    var email: String,
    var nombre: String,
    var apellido1: String,
    var apellido2: String,
    var rol: Roles,
    var urlFoto: String?,
    var fechaRegistro: LocalDateTime
): Serializable
