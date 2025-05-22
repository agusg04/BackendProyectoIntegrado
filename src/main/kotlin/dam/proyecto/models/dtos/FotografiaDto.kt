package dam.proyecto.models.dtos

import java.io.Serializable

/**
 * DTO for {@link dam.proyecto.models.entities.Fotografia}
 */
data class FotografiaDto(
    var id: Long? = null,
    var idUsuarioId: Long? = null,
    var idUsuarioNombre: String? = null,
    var idUsuarioApellido1: String? = null,
    var titulo: String? = null,
    var descripcion: String? = null,
    var votoIds: MutableSet<Long>?
) : Serializable