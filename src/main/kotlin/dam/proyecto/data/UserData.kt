package dam.proyecto.data

import dam.proyecto.models.enums.Roles
import java.time.LocalDateTime

data class UserData(
    val email: String,
    val nombre: String,
    val primerApellido: String,
    val segundoApellido: String,
    val rol: Roles,
    val urlFoto: String? = null,
    val fechaRegistro: LocalDateTime,
    val fotosSubidas: Set<FotoUsuario>,
    val votos: Set<VotoUsuario>,
)