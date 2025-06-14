package dam.proyecto.models.mappers

import dam.proyecto.data.FotoUsuario
import dam.proyecto.data.FotografiaPost
import dam.proyecto.models.entities.Fotografia
import dam.proyecto.models.enums.Estado
import org.mapstruct.*

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
abstract class FotografiaMapper {

    abstract fun toEntity(fotografiaPost: FotografiaPost): Fotografia

    abstract fun toDto(fotografia: Fotografia): FotografiaPost

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract fun partialUpdate(fotografiaPost: FotografiaPost, @MappingTarget fotografia: Fotografia): Fotografia

    fun toDtoPost(foto: Fotografia): FotografiaPost {
        return FotografiaPost(
            id = foto.id,
            idUsuario = foto.usuario?.id,
            nombreUsuario = foto.usuario?.nombre,
            apellidoUsuario = foto.usuario?.apellido1,
            titulo = foto.titulo,
            descripcion = foto.descripcion,
            filePath = foto.filePath,
            tamanio = foto.tamanio,
            formato = foto.formato,
            downloadUrl = "/api/photos/${foto.id}/image",
            resultadoPuntajeTotal = foto.resultado?.puntajeTotal
        )
    }

    fun toFotoUsuario(foto: Fotografia): FotoUsuario {
        return FotoUsuario(
            fotoId = foto.id ?: 0L,
            urlFoto = foto.filePath ?: "",
            votos = foto.resultado?.puntajeTotal,
            titulo = foto.titulo ?: "",
            descripcion = foto.descripcion,
            estado = foto.estado ?: Estado.PENDIENTE
        )
    }

}