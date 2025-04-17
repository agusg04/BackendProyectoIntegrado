package dam.proyecto.models.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import dam.proyecto.models.enums.Roles
import jakarta.persistence.*
import lombok.Data
import java.time.Instant

@Entity
@Table(name = "usuarios")
@Data
open class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @Column(name = "email", nullable = false, length = 100)
    open var email: String? = null

    @Column(name = "nombre", nullable = false, length = 45)
    open var nombre: String? = null

    @Column(name = "apellido1", nullable = false, length = 45)
    open var apellido1: String? = null

    @Column(name = "apellido2", nullable = false, length = 45)
    open var apellido2: String? = null

    @Column(name = "contrasenia", nullable = false)
    @JsonIgnore
    open var contrasenia: String? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    open var rol: Roles? = null

    @Column(name = "fecha_registro", nullable = false)
    open var fechaRegistro: Instant? = null

    @OneToMany(mappedBy = "idUsuario", cascade = [CascadeType.ALL])
    open var fotosSubidas: MutableSet<Fotografia> = mutableSetOf()

    @OneToMany(mappedBy = "validador", cascade = [CascadeType.ALL])
    open var fotosValidadas: MutableSet<Fotografia> = mutableSetOf()

    @OneToMany(mappedBy = "idVotante", cascade = [CascadeType.ALL])
    open var votaciones: MutableSet<Voto> = mutableSetOf()
}