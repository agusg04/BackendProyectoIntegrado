package dam.proyecto.models.dtos

import java.io.Serializable
import java.time.LocalDateTime

/**
 * DTO for {@link dam.proyecto.models.entities.Voto}
 */
data class VotoDto(
    var fotoId: Long? = null,
    var votanteId: Long? = null,
    var fechaVoto: LocalDateTime? = null
) : Serializable