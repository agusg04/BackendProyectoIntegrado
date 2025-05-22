package dam.proyecto.data

import java.io.Serializable

/**
 * DTO for {@link dam.proyecto.models.entities.Fotografia}
 */
data class FotografiaPost(
    var id: Long? = null,
    var idUsuario: Long? = null,
    var nombreUsuario: String? = null,
    var apellidoUsuario: String? = null,
    var titulo: String? = null,
    var descripcion: String? = null,
    var filePath: String? = null,
    var tamanio: Int? = null,
    var formato: String? = null,
    var downloadUrl: String? = null,
    var resultadoPuntajeTotal: Int? = null
) : Serializable