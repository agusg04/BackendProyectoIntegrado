package dam.proyecto.models.entities

import jakarta.persistence.*
import lombok.Data
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDateTime

@Entity
@Table(name = "votos")
@Data
open class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_foto", nullable = false)
    open var foto: Fotografia? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_votante", nullable = false)
    open var votante: Usuario? = null

    @Column(name = "fecha_voto", nullable = false)
    open var fechaVoto: LocalDateTime? = null
}