package dam.proyecto.auth

import dam.proyecto.models.dtos.UsuarioRegistroDto
import dam.proyecto.models.enums.Roles
import dam.proyecto.services.UsuarioService
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class AuthService(
    private val userService: UsuarioService,
    //private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder
) {
    private val logger = LoggerFactory.getLogger(AuthService::class.java)

    fun login(request: LoginRequest): ApiResponse<LoginResponse> {
        val email = request.email
        val password = request.password

        if (email.isNullOrBlank() || password.isNullOrBlank()) {
            return ApiResponse(
                success = false,
                message = "Por favor, complete todos los campos"
            )
        }

        val usuario = userService.obtenerUsuarioPorEmail(email)
            ?: return ApiResponse(
                success = false,
                message = "No se ha encontrado el usuario $email"
            )

        if (!passwordEncoder.matches(password, usuario.contrasenia)) {
            return ApiResponse(
                success = false,
                message = "Contraseña incorrecta"
            )
        }

        logger.info("Usuario conectado: ${usuario.nombre}")

        val token = "hola" // TODO: implementar generación de token JWT

        return ApiResponse(
            success = true,
            message = "Inicio de sesión exitoso",
            data = LoginResponse(nombre = usuario.nombre, token = token)
        )
    }

    fun register(request: RegisterRequest): ApiResponse<RegisterResponse> {
        if (listOf(
                request.name, request.lastName1,
                request.lastName2, request.email,
                request.password
            ).any { it.isNullOrBlank() }
        ) {
            return ApiResponse(success = false, message = "Por favor, complete todos los campos")
        }

        val usuarioRegistroDto = UsuarioRegistroDto(
            email = request.email,
            nombre = request.name,
            apellido1 = request.lastName1,
            apellido2 = request.lastName2,
            contrasenia = passwordEncoder.encode(request.password),
            rol = Roles.USUARIO,
            fechaRegistro = Instant.now()
        )

        val registrado = userService.registrar(usuarioRegistroDto)
            ?: return ApiResponse(success = false, message = "")

        logger.info("Usuario registrado: ${registrado.nombre}")

        return ApiResponse(
            success = true,
            message = "Usuario registrado exitosamente",
            data = RegisterResponse(
                nombre = registrado.nombre,
                apellido1 = registrado.apellido1,
                apellido2 = registrado.apellido2,
                email = registrado.email,
                fechaRegistro = registrado.fechaRegistro,
                token = "hola"
            )
        )
    }
}