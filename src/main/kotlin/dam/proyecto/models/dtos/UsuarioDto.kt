package dam.proyecto.models.dtos

import dam.proyecto.models.enums.Roles
import java.io.Serializable
import java.time.Instant

/**
 * DTO for {@link dam.proyecto.models.entities.Usuario}
 */
data class UsuarioDto(
    var id: Long? = null,
    var email: String? = null,
    var nombre: String? = null,
    var apellido1: String? = null,
    var apellido2: String? = null,
    var contrasenia: String? = null,
    var rol: Roles? = null,
    var fechaRegistro: Instant? = null,
    var fotosSubidaIds: MutableSet<Long>?,
    var fotosValidadaIds: MutableSet<Long>?,
    var votacioneIds: MutableSet<Long>?
) : Serializable