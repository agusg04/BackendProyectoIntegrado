package dam.proyecto.models.dtos

import java.io.Serializable
import java.time.Instant

/**
 * DTO for {@link dam.proyecto.models.entities.Rally}
 */
data class RallyInfoDto(
    var nombreRally: String? = null,
    var descripcionRally: String? = null,
    var fechaInicio: Instant? = null,
    var fechaFin: Instant? = null,
    var plazoVotacion: Instant? = null,
    var votosPorUsuario: Int? = null,
    var maxFotosUsuario: Int? = null,
    var primerPremio: Int? = null,
    var segundoPremio: Int? = null,
    var tercerPremio: Int? = null
) : Serializable