package dam.proyecto.models.entities

import jakarta.persistence.*
import lombok.Data
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(name = "resultados")
@Data
open class Resultado {
    @Id
    @Column(name = "id_foto", nullable = false)
    open var id: Long? = null

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_foto", nullable = false)
    open var foto: Fotografia? = null

    @Column(name = "puntaje_total", nullable = false)
    open var puntajeTotal: Int? = null
}