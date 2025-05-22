package dam.proyecto.auth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class UserPrincipal(
    val id: Long?,
    val email: String?,
    private val authoritiesList: Collection<GrantedAuthority>
): UserDetails {
    override fun getUsername() = email
    override fun getPassword() = ""  // No lo necesitas si no usas auth por sesión
    override fun getAuthorities(): Collection<GrantedAuthority> = authoritiesList
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true
}
