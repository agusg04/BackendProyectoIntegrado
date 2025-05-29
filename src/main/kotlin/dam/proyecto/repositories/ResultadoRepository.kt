package dam.proyecto.repositories

import dam.proyecto.models.entities.Resultado
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface ResultadoRepository : JpaRepository<Resultado, Long> {
    @Transactional
    @Modifying
    @Query("update Resultado r set r.puntajeTotal = r.puntajeTotal + 1 WHERE r.id = :idFoto")
    fun increasePuntaje(@Param("idFoto") idFoto: Long): Int
    @Transactional
    @Modifying
    @Query("update Resultado r set r.puntajeTotal = r.puntajeTotal - 1 WHERE r.id = :idFoto AND r.puntajeTotal > 0")
    fun decreasePuntaje(@Param("idFoto") idFoto: Long): Int
}