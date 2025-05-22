package dam.proyecto.services.impl

import dam.proyecto.FechaUtils
import dam.proyecto.models.dtos.UsuarioLoginDto
import dam.proyecto.models.dtos.UsuarioRegistradoDto
import dam.proyecto.models.dtos.UsuarioRegistroDto
import dam.proyecto.models.mappers.UsuarioMapper
import dam.proyecto.repositories.UsuarioRepository
import dam.proyecto.services.UsuarioService
import org.springframework.stereotype.Service

@Service
class UsuarioServiceImpl(
    private val usuarioRepository: UsuarioRepository,
    private val usuarioMapper: UsuarioMapper

) : UsuarioService {

    override fun obtenerUsuarioPorEmail(email: String): UsuarioLoginDto? {
        val usuario = usuarioRepository.findUsuarioByEmail(email) ?: return null

        return UsuarioLoginDto(
            id = requireNotNull(usuario.id),
            email = requireNotNull(usuario.email),
            nombre = requireNotNull(usuario.nombre),
            contrasenia = requireNotNull(usuario.contrasenia),
            rol = requireNotNull(usuario.rol)
        )
    }

    override fun registrar(usuarioRegistroDto: UsuarioRegistroDto): UsuarioRegistradoDto? {
        val usuarioEntity = usuarioMapper.toRegistroEntity(usuarioRegistroDto)
        val guardado = usuarioRepository.save(usuarioEntity)
        return usuarioMapper.toRegistroDto(guardado)
    }

    override fun actualizarUltimoLoginPorId(idUsuario: Long): Boolean =
        usuarioRepository.updateLastLoginById(idUsuario, FechaUtils.ahora()) > 0
}