package dam.proyecto.services

import dam.proyecto.auth.responses.ApiResponse
import dam.proyecto.data.FotografiaPost
import dam.proyecto.data.FotografiaWallData
import org.springframework.core.io.Resource

interface FotografiaService {
    fun obtenerFotos(): ApiResponse<FotografiaWallData>
    fun obtenerFoto(id: Long): ApiResponse<FotografiaPost>
    fun obtenerImagen(id: Long): ApiResponse<Resource>
    //fun obtenerFotosParaUsuario(idUsuario: Long): ApiResponse<FotografiaWallDataConVoto>
}
