package dam.proyecto.services

import dam.proyecto.models.dtos.UsuarioLoginDto
import dam.proyecto.models.dtos.UsuarioRegistroDto

interface UsuarioService {
    fun obtenerUsuarioPorEmail(email: String): UsuarioLoginDto?
    fun registrar(usuarioRegistroDto: UsuarioRegistroDto): UsuarioRegistroDto?
}