package dam.proyecto.auth

import dam.proyecto.repositories.UsuarioRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(
    private val jwtService: JwtService,
    private val userDetailsService: CustomUserDetailsService,
    private val usuarioRepository: UsuarioRepository,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        val jwt = authHeader?.takeIf { it.startsWith("Bearer ") }?.removePrefix("Bearer ")?.trim()

        if (jwt == null) {
            filterChain.doFilter(request, response)
            return
        }

        val lastLogin = jwtService.extractUserId(jwt)?.let { usuarioRepository.findLastLoginById(it) }

        val issuedAt = jwtService.extractIssuedAt(jwt)

        if (!jwtService.isTokenValid(jwt) || lastLogin == null || issuedAt?.isBefore(lastLogin) == true) {
            //El token debe ser posterior al ultimo login del usuario si no no es valido
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido o deslogueado")
            logger.error("El token $jwt no es valido")
            return
        }

        if (SecurityContextHolder.getContext().authentication == null) {
            val email = jwtService.extractEmail(jwt)
            val userDetails = userDetailsService.loadUserByUsername(email)

            val authToken = UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.authorities
            ).apply {
                details = WebAuthenticationDetailsSource().buildDetails(request)
            }

            SecurityContextHolder.getContext().authentication = authToken
        }

        filterChain.doFilter(request, response)
    }
}