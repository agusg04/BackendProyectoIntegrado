package dam.proyecto.repositories

import dam.proyecto.models.entities.Voto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface VotoRepository : JpaRepository<Voto, Long> {
    @Query("SELECT v.foto.id FROM Voto v WHERE v.votante.id = :idUsuario")
    fun findFotosIdsByVotanteId(@Param("idUsuario") idUsuario: Long): List<Long>?
    fun existsByVotanteIdAndFotoId(usuarioId: Long, fotografiaId: Long): Boolean
    fun findVotoByVotanteIdAndFotoId(usuarioId: Long, fotografiaId: Long): Voto?
}