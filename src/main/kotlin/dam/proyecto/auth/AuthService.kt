package dam.proyecto.auth

import dam.proyecto.FechaUtils
import dam.proyecto.auth.requests.LoginRequest
import dam.proyecto.auth.requests.LogoutRequest
import dam.proyecto.auth.requests.RefreshTokenRequest
import dam.proyecto.auth.requests.RegisterRequest
import dam.proyecto.auth.responses.ApiResponse
import dam.proyecto.auth.responses.LoginResponse
import dam.proyecto.auth.responses.RefreshTokenResponse
import dam.proyecto.auth.responses.RegisterResponse
import dam.proyecto.models.dtos.UsuarioRegistroDto
import dam.proyecto.models.enums.Roles
import dam.proyecto.services.UsuarioService
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.regex.Pattern

@Service
class AuthService(
    private val userService: UsuarioService,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder,
) {
    private val logger = LoggerFactory.getLogger(AuthService::class.java)

    private fun isEmailValid(email: String): Boolean {
        val pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\$")
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    private fun areFieldsNotEmpty(vararg fields: String): Boolean {
        return fields.none { it.isBlank() }
    }

    fun login(request: LoginRequest): ApiResponse<LoginResponse> {
        val email = request.email
        val password = request.password

        if (!areFieldsNotEmpty(email, password)) {
            logger.error("Error en el login: Los campos 'email' o 'password' están vacíos")
            return ApiResponse(
                success = false,
                message = "Por favor, complete todos los campos"
            )
        }

        if (!isEmailValid(email)) {
            logger.error("Error en el login: El campos 'email' tiene un formato no válido")
            return ApiResponse(
                success = false,
                message = "El formato del email no es válido"
            )
        }

        val usuario = userService.obtenerUsuarioPorEmail(email)
            ?: run {
                logger.error("Error en el login: No se ha encontrado el usuario con el email '$email'")
                return ApiResponse(
                    success = false,
                    message = "No se ha encontrado el usuario $email"
                )
            }

        if (!passwordEncoder.matches(password, usuario.contrasenia)) {
            logger.error("Error en el login: La contraseña no es correcta")
            return ApiResponse(
                success = false,
                message = "Contraseña incorrecta"
            )
        }

        if (!userService.actualizarUltimoLoginPorId(usuario.id)) {
            return ApiResponse(success = false, message = "No se pudo iniciar sesión")
        }

        logger.info("Usuario conectado: ${usuario.email}")

        val accessToken = jwtService.generateAccessToken(usuario.email, usuario.id, usuario.rol)
        logger.info("Token access generado $accessToken")
        val refreshToken = jwtService.generateRefreshToken(usuario.email, usuario.id, usuario.rol)
        logger.info("Token refresh generado $refreshToken")

        return ApiResponse(
            success = true,
            message = "Inicio de sesión exitoso",
            data = LoginResponse(
                nombre = usuario.nombre,
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        )
    }

    fun refreshToken(request: RefreshTokenRequest): ApiResponse<RefreshTokenResponse> {
        val refreshToken = request.refreshToken

        if (!jwtService.isTokenValid(refreshToken)) {
            return ApiResponse(
                success = false,
                message = "Refresh token inválido o expirado"
            )
        }

        val email = jwtService.extractEmail(refreshToken)
            ?: return ApiResponse(success = false, message = "No se pudo extraer el email del token")

        val usuario = userService.obtenerUsuarioPorEmail(email)
            ?: return ApiResponse(success = false, message = "Usuario no encontrado")

        val newAccessToken = jwtService.generateAccessToken(email, usuario.id, usuario.rol)

        logger.info("Nuevo token generado $newAccessToken")

        return ApiResponse(
            success = true,
            message = "Token refrescado correctamente",
            data = RefreshTokenResponse(accessToken = newAccessToken)
        )
    }


    fun register(request: RegisterRequest): ApiResponse<RegisterResponse> {
        if (!areFieldsNotEmpty(request.name, request.lastName1, request.lastName2, request.email, request.password)) {
            logger.error("Error en el registro: Alguno de los campos está/n vacío/s")
            return ApiResponse(success = false, message = "Por favor, complete todos los campos")
        }

        if (!isEmailValid(request.email)) {
            logger.error("Error en el registro: El campos 'email' tiene un formato no válido")
            return ApiResponse(success = false, message = "El formato del email no es válido")
        }

        if (userService.existeEmail(request.email)) {
            logger.error("Error en el registro: El email ${request.email} ya está registrado")
            return ApiResponse(success = false, message = "El email ya está registrado")
        }

        val usuarioRegistroDto = UsuarioRegistroDto(
            email = request.email,
            nombre = request.name,
            apellido1 = request.lastName1,
            apellido2 = request.lastName2,
            contrasenia = passwordEncoder.encode(request.password),
            rol = Roles.USER,
            fechaRegistro = FechaUtils.ahora()
        )

        val registrado = userService.registrar(usuarioRegistroDto)
            ?: return ApiResponse(success = false, message = "Error al intentar realizar el registro")

        if (!userService.actualizarUltimoLoginPorId(registrado.id)) {
            return ApiResponse(success = false, message = "No se pudo iniciar sesión")
        }

        logger.info("Usuario registrado: ${registrado.email}")

        val accessToken = jwtService.generateAccessToken(registrado.email, registrado.id, registrado.rol)
        logger.info("Token access generado $accessToken")
        val refreshToken = jwtService.generateRefreshToken(registrado.email, registrado.id, registrado.rol)
        logger.info("Token refresh generado $refreshToken")


        return ApiResponse(
            success = true,
            message = "Usuario registrado exitosamente",
            data = RegisterResponse(
                nombre = registrado.nombre,
                apellido1 = registrado.apellido1,
                apellido2 = registrado.apellido2,
                email = registrado.email,
                fechaRegistro = registrado.fechaRegistro,
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        )
    }

    fun logout(tokens: LogoutRequest): ApiResponse<String> {

        //jwtService.extractUserId(tokens.refreshToken)?.let { usuarioRepository.updateLastLoginById(it) }

        val userId = jwtService.extractUserId(tokens.refreshToken)
            ?: return ApiResponse(success = false, message = "Token inválido")

        if (!userService.actualizarUltimoLoginPorId(userId)) {
            return ApiResponse(success = false, message = "No se pudo cerrar la sesión (usuario no encontrado)")
        }

        logger.info("Usuario desconectado: ${jwtService.extractEmail(tokens.accessToken)}")

        return ApiResponse(
            success = true,
            message = "Sesión cerrada correctamente"
        )
    }
}