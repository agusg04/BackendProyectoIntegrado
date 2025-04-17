package dam.proyecto.repositories

import dam.proyecto.models.entities.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UsuarioRepository : JpaRepository<Usuario, Long> {
    @Query("FROM Usuario u WHERE u.email = :email")
    fun findUsuarioByEmail(@Param("email") email: String): Usuario?
}