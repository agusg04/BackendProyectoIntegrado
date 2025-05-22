package dam.proyecto.services

import dam.proyecto.models.dtos.UsuarioLoginDto
import dam.proyecto.models.dtos.UsuarioRegistradoDto
import dam.proyecto.models.dtos.UsuarioRegistroDto

interface UsuarioService {
    fun obtenerUsuarioPorEmail(email: String): UsuarioLoginDto?
    fun registrar(usuarioRegistroDto: UsuarioRegistroDto): UsuarioRegistradoDto?
    fun actualizarUltimoLoginPorId(idUsuario: Long): Boolean
}