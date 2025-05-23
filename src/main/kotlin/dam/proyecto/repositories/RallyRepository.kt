package dam.proyecto.repositories

import dam.proyecto.models.entities.Rally
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface RallyRepository : JpaRepository<Rally, Long>{
    @Query("FROM Rally")
    fun findRally(): Rally
}
