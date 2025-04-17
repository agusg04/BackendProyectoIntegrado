package dam.proyecto.models.dtos

import java.io.Serializable

/**
 * DTO for {@link dam.proyecto.models.dtos.UsuarioDto}
 */
data class UsuarioLoginDto(
    val email: String,
    val nombre: String,
    val contrasenia: String
) : Serializable