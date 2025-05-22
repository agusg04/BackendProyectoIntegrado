package dam.proyecto.models.dtos

import dam.proyecto.models.enums.Roles
import java.io.Serializable

/**
 * DTO for {@link dam.proyecto.models.dtos.UsuarioDto}
 */
data class UsuarioLoginDto(
    val id: Long,
    val email: String,
    val nombre: String,
    val contrasenia: String,
    val rol: Roles
) : Serializable