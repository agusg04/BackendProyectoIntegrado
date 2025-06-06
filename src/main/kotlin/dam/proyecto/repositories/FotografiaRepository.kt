package dam.proyecto.repositories

import dam.proyecto.models.entities.Fotografia
import dam.proyecto.models.enums.Estado
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FotografiaRepository : JpaRepository<Fotografia, Long> {
    override fun findAll(): List<Fotografia>
    fun findAllByEstado(estado: Estado = Estado.ADMITIDA): List<Fotografia>
    fun findAllByUsuario_Id(idUsuario: Long): List<Fotografia>
}
