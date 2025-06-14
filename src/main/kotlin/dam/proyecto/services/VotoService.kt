package dam.proyecto.services

import dam.proyecto.auth.responses.ApiResponse
import dam.proyecto.data.ListaVotos
import dam.proyecto.data.VotoUsuario

interface VotoService {
    fun obtenerIdsFotosVotadasPorUsuario(idUsuario: Long): ApiResponse<ListaVotos>?
    fun votar(idUsuario: Long, idFoto: Long): ApiResponse<Boolean>
    fun eliminarVoto(idUsuario: Long, idFoto: Long): ApiResponse<Boolean>
    fun obtenerVotosUsuario(idUsuario: Long): Set<VotoUsuario>
}