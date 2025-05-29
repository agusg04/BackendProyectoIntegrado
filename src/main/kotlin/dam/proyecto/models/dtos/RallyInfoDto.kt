package dam.proyecto.models.dtos

import java.io.Serializable
import java.time.LocalDateTime

/**
 * DTO for {@link dam.proyecto.models.entities.Rally}
 */
data class RallyInfoDto(
    var nombreRally: String? = null,
    var descripcionRally: String? = null,
    var fechaInicio: LocalDateTime? = null,
    var fechaFin: LocalDateTime? = null,
    var plazoVotacion: LocalDateTime? = null,
    var maxVotosUsuario: Int? = null,
    var maxFotosUsuario: Int? = null,
    var primerPremio: Int? = null,
    var segundoPremio: Int? = null,
    var tercerPremio: Int? = null
) : Serializable