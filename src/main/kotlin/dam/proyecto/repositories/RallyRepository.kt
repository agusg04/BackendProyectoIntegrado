package dam.proyecto.repositories

import dam.proyecto.models.entities.Rally
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface RallyRepository : JpaRepository<Rally, Long>{
    @Query("FROM Rally")
    fun findRally(): Rally
}
