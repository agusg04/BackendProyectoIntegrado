package dam.proyecto.services.impl

import dam.proyecto.models.dtos.UsuarioLoginDto
import dam.proyecto.models.dtos.UsuarioRegistroDto
import dam.proyecto.models.mappers.UsuarioMapper
import dam.proyecto.repositories.UsuarioRepository
import dam.proyecto.services.UsuarioService
import org.springframework.stereotype.Service

@Service
class UsuarioServiceImpl(
    private val repositorioUsuario: UsuarioRepository,
    private val usuarioMapper: UsuarioMapper

) : UsuarioService {

    override fun obtenerUsuarioPorEmail(email: String): UsuarioLoginDto? {
        val usuario = repositorioUsuario.findUsuarioByEmail(email) ?: return null

        return UsuarioLoginDto(
            email = requireNotNull(usuario.email),
            nombre = requireNotNull(usuario.nombre),
            contrasenia = requireNotNull(usuario.contrasenia)
        )
    }

    override fun registrar(usuarioRegistroDto: UsuarioRegistroDto): UsuarioRegistroDto? {
        val usuarioEntity = usuarioMapper.toRegistroEntity(usuarioRegistroDto)
        val guardado = repositorioUsuario.save(usuarioEntity)
        return usuarioMapper.toRegistroDto(guardado)
    }
}