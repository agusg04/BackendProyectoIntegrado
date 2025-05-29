package dam.proyecto.repositories

import dam.proyecto.models.entities.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Repository
interface UsuarioRepository : JpaRepository<Usuario, Long> {
    @Query("FROM Usuario u WHERE u.email = :email")
    fun findUsuarioByEmail(@Param("email") email: String): Usuario?

    fun findByEmail(@Param("email") email: String?): Usuario?

    @Transactional
    @Modifying
    @Query("update Usuario u set u.lastLogin = ?2 where u.id = ?1")
    fun updateLastLoginById(idUsuario: Long, lastLogin: LocalDateTime): Int

    @Transactional
    @Modifying
    @Query("update Usuario u set u.lastLogin = ?2 where u.email = ?1")
    fun updateLastLoginByEmail(email: String, lastLogin: LocalDateTime): Int

    @Query("SELECT u.lastLogin FROM Usuario u WHERE u.id = :id")
    fun findLastLoginById(@Param("id") id: Long): LocalDateTime?

    fun existsByEmail(email: String?): Boolean
}