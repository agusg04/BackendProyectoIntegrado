package dam.proyecto.models.entities

import dam.proyecto.models.enums.Estado
import jakarta.persistence.*
import lombok.Data
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.Instant

@Entity
@Table(name = "fotografias")
@Data
open class Fotografia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_usuario", nullable = false)
    open var idUsuario: Usuario? = null

    @Column(name = "titulo", nullable = false, length = 45)
    open var titulo: String? = null

    @Column(name = "descripcion", length = 255)
    open var descripcion: String? = null

    @Column(name = "url", nullable = false, length = 255)
    open var url: String? = null

    @Column(name = "fecha_subida", nullable = false)
    open var fechaSubida: Instant? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    open var estado: Estado? = null

    @Column(name = "tamanio", nullable = false)
    open var tamanio: Int? = null

    @Column(name = "formato", nullable = false, length = 45)
    open var formato: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "validador")
    open var validador: Usuario? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_rally", nullable = false)
    open var idRally: Rally? = null

    @OneToOne(mappedBy = "foto", cascade = [CascadeType.ALL], optional = false)
    open var resultado: Resultado? = null

    @OneToMany(mappedBy = "idFoto")
    open var votos: MutableSet<Voto> = mutableSetOf()
}