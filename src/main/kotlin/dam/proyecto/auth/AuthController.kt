package dam.proyecto.auth

import dam.proyecto.auth.requests.*
import dam.proyecto.auth.responses.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<ApiResponse<LoginResponse>> {
        val response = authService.login(request)
        return if (response.success) ResponseEntity.ok(response)
        else ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response)
    }

    @PostMapping("/refresh")
    fun refreshToken(@RequestBody request: RefreshTokenRequest): ResponseEntity<ApiResponse<RefreshTokenResponse>> {
        val response = authService.refreshToken(request)
        return if (response.success) ResponseEntity.ok(response)
        else ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response)
    }

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<ApiResponse<RegisterResponse>> {
        val response = authService.register(request)
        return if (response.success) ResponseEntity.ok(response)
        else ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response)
    }

    @PostMapping("/logout")
    fun logout(@RequestBody logoutRequest: LogoutRequest): ResponseEntity<ApiResponse<String>> {
        val response = authService.logout(logoutRequest)
        return if (response.success) ResponseEntity.ok(response)
        else ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response)
    }
}