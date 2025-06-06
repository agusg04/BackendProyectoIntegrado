package dam.proyecto.data

import dam.proyecto.models.enums.Estado

data class FotoUsuario(
    val fotoId: Long,
    val urlFoto: String,
    val votos: Int? = null,
    val titulo: String,
    val descripcion: String? = null,
    val estado: Estado
)
