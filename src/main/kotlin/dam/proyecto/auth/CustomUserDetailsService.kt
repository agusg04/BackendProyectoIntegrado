package dam.proyecto.auth

import dam.proyecto.models.entities.Usuario
import dam.proyecto.repositories.UsuarioRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val usuarioRepository: UsuarioRepository
) : UserDetailsService {
    override fun loadUserByUsername(email: String?): UserDetails {
        val usuario: Usuario = usuarioRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("No se encontró el usuario con email: $email")

        val roleName = usuario.rol?.name
            ?: throw IllegalStateException("El usuario no tiene rol asignado")

        val authorities = listOf(SimpleGrantedAuthority("ROLE_$roleName"))

        return UserPrincipal(
            usuario.id,
            usuario.email,
            authorities
        )
    }
}