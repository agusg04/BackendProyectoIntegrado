package dam.proyecto.services

import dam.proyecto.auth.responses.ApiResponse
import dam.proyecto.data.UserData
import dam.proyecto.models.dtos.ListaUsuarios
import dam.proyecto.models.dtos.UsuarioLoginDto
import dam.proyecto.models.dtos.UsuarioRegistradoDto
import dam.proyecto.models.dtos.UsuarioRegistroDto

interface UsuarioService {
    fun obtenerUsuarioPorEmail(email: String): UsuarioLoginDto?
    fun registrar(usuarioRegistroDto: UsuarioRegistroDto): UsuarioRegistradoDto?
    fun actualizarUltimoLoginPorId(idUsuario: Long): Boolean
    fun existeEmail(email: String): Boolean
    fun obtenerUsuarios(): ApiResponse<ListaUsuarios>
    fun obtenerUsuario(idUsuario: Long): ApiResponse<UserData>
    fun actualizarUsuario(idUsuario: Long, usuarioRegistroDto: UsuarioRegistroDto): ApiResponse<UsuarioRegistradoDto>
}