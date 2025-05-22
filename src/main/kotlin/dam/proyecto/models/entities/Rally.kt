package dam.proyecto.models.entities

import jakarta.persistence.*
import lombok.Data
import java.time.LocalDateTime

@Entity
@Table(name = "rallys")
@Data
open class Rally {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @Column(name = "nombre", nullable = false, length = 45)
    open var nombre: String? = null

    @Column(name = "descripcion", nullable = false, length = 255)
    open var descripcion: String? = null

    @Column(name = "fecha_inicio", nullable = false)
    open var fechaInicio: LocalDateTime? = null

    @Column(name = "fecha_fin", nullable = false)
    open var fechaFin: LocalDateTime? = null

    @Column(name = "plazo_votacion", nullable = false)
    open var plazoVotacion: LocalDateTime? = null

    @Column(name = "votos_por_usuario", nullable = false)
    open var votosPorUsuario: Int? = null

    @Column(name = "max_fotos", nullable = false)
    open var maxFotos: Int? = null

    @Column(name = "primer_premio", nullable = false)
    open var primerPremio: Int? = null

    @Column(name = "segundo_premio", nullable = false)
    open var segundoPremio: Int? = null

    @Column(name = "tercer_premio", nullable = false)
    open var tercerPremio: Int? = null

    @OneToMany(mappedBy = "rally")
    open var fotografias: MutableSet<Fotografia> = mutableSetOf()
}