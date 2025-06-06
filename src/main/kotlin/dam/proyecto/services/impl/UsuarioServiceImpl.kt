package dam.proyecto.services.impl

import dam.proyecto.FechaUtils
import dam.proyecto.auth.responses.ApiResponse
import dam.proyecto.data.UserData
import dam.proyecto.models.dtos.ListaUsuarios
import dam.proyecto.models.dtos.UsuarioLoginDto
import dam.proyecto.models.dtos.UsuarioRegistradoDto
import dam.proyecto.models.dtos.UsuarioRegistroDto
import dam.proyecto.models.enums.Roles
import dam.proyecto.models.mappers.UsuarioMapper
import dam.proyecto.repositories.UsuarioRepository
import dam.proyecto.services.FotografiaService
import dam.proyecto.services.UsuarioService
import dam.proyecto.services.VotoService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UsuarioServiceImpl(
    private val usuarioRepository: UsuarioRepository,
    private val usuarioMapper: UsuarioMapper,
    private val fotografiaService: FotografiaService,
    private val votoService: VotoService,
    private val passwordEncoder: PasswordEncoder,

) : UsuarioService {

    override fun obtenerUsuarioPorEmail(email: String): UsuarioLoginDto? {
        val usuario = usuarioRepository.findUsuarioByEmail(email) ?: return null

        return UsuarioLoginDto(
            id = requireNotNull(usuario.id),
            email = requireNotNull(usuario.email),
            nombre = requireNotNull(usuario.nombre),
            apellido1 = requireNotNull(usuario.apellido1),
            apellido2 = requireNotNull(usuario.apellido2),
            contrasenia = requireNotNull(usuario.contrasenia),
            rol = requireNotNull(usuario.rol),
            urlFotoPerfil = usuario.urlFoto,
            fechaRegistro = requireNotNull(usuario.fechaRegistro)
        )
    }

    override fun registrar(usuarioRegistroDto: UsuarioRegistroDto): UsuarioRegistradoDto? {
        val usuarioEntity = usuarioMapper.toRegistroEntity(usuarioRegistroDto)
        val guardado = usuarioRepository.save(usuarioEntity)
        return usuarioMapper.toRegistroDto(guardado)
    }

    override fun actualizarUltimoLoginPorId(idUsuario: Long): Boolean =
        usuarioRepository.updateLastLoginById(idUsuario, FechaUtils.ahora().minusSeconds(1)) > 0
        //Le quito un milisegundo porque si no se genera el token con exactamente la misma fecha de la bdd

    override fun existeEmail(email: String): Boolean {
        return usuarioRepository.existsByEmail(email)
    }

    override fun obtenerUsuarios(): ApiResponse<ListaUsuarios> {
        val usuariosId = usuarioRepository.findAll()
            .map { usuarioMapper.toUsuarioId(it) }
            .toSet()

        return if (usuariosId.isEmpty()) {
            ApiResponse(
                success = true,
                message = "No hay usuarios registrados",
                data = ListaUsuarios(emptySet())
            )
        } else {
            ApiResponse(
                success = true,
                message = "Usuarios recuperados correctamente",
                data = ListaUsuarios(usuariosId)
            )
        }
    }

    override fun obtenerUsuario(idUsuario: Long): ApiResponse<UserData> {
        val usuario = usuarioRepository.findById(idUsuario)

        if (usuario.isEmpty) {
            return ApiResponse(
                success = false,
                message = "No se encontró el usuario",
                data = null
            )
        }

        val datos = usuario.get()

        val fotosSubidas = fotografiaService.obtenerFotosDeUsuario(idUsuario)
        val votos = votoService.obtenerVotosUsuario(idUsuario)

        val userData = UserData(
            email = datos.email ?: "",
            nombre = datos.nombre ?: "",
            primerApellido = datos.apellido1 ?: "",
            segundoApellido = datos.apellido2 ?: "",
            rol = datos.rol ?: Roles.USER,
            urlFoto = datos.urlFoto,
            fechaRegistro = requireNotNull(datos.fechaRegistro),
            fotosSubidas = fotosSubidas,
            votos = votos
        )

        return ApiResponse(
            success = true,
            message = "Usuario recuperado correctamente",
            data = userData
        )
    }

    override fun actualizarUsuario(idUsuario: Long, usuarioRegistroDto: UsuarioRegistroDto): ApiResponse<UsuarioRegistradoDto> {
        val usuarioExistente = usuarioRepository.findById(idUsuario).orElse(null)
            ?: return ApiResponse(
                success = false,
                message = "Usuario no encontrado",
                data = null
            )

        val contraseniaSegura = usuarioRegistroDto.contrasenia.takeIf { it.isNotBlank() }?.let {
            passwordEncoder.encode(it)
        }

        val dtoActualizado = usuarioRegistroDto.copy(
            contrasenia = contraseniaSegura ?: usuarioExistente.contrasenia ?: ""
        )

        usuarioMapper.partialUpdate(dtoActualizado, usuarioExistente)
        val actualizado = usuarioRepository.save(usuarioExistente)

        return ApiResponse(
            success = true,
            message = "Usuario actualizado correctamente",
            data = usuarioMapper.toRegistroDto(actualizado)
        )
    }
}