package dam.proyecto.models.mappers

import dam.proyecto.models.dtos.RallyInfoDto
import dam.proyecto.models.entities.Rally
import org.mapstruct.*

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING
)
abstract class RallyMapper {

    @Mappings(
        Mapping(source = "nombre", target = "nombreRally"),
        Mapping(source = "descripcion", target = "descripcionRally"),
        Mapping(source = "fechaInicio", target = "fechaInicio"),
        Mapping(source = "fechaFin", target = "fechaFin"),
        Mapping(source = "plazoVotacion", target = "plazoVotacion"),
        Mapping(source = "votosPorUsuario", target = "maxVotosUsuario"),
        Mapping(source = "maxFotos", target = "maxFotosUsuario"),
        Mapping(source = "primerPremio", target = "primerPremio"),
        Mapping(source = "segundoPremio", target = "segundoPremio"),
        Mapping(source = "tercerPremio", target = "tercerPremio")
    )
    abstract fun toDto(rally: Rally): RallyInfoDto

    @Mappings(
        Mapping(source = "nombreRally", target = "nombre"),
        Mapping(source = "descripcionRally", target = "descripcion"),
        Mapping(source = "fechaInicio", target = "fechaInicio"),
        Mapping(source = "fechaFin", target = "fechaFin"),
        Mapping(source = "plazoVotacion", target = "plazoVotacion"),
        Mapping(source = "maxVotosUsuario", target = "votosPorUsuario"),
        Mapping(source = "maxFotosUsuario", target = "maxFotos"),
        Mapping(source = "primerPremio", target = "primerPremio"),
        Mapping(source = "segundoPremio", target = "segundoPremio"),
        Mapping(source = "tercerPremio", target = "tercerPremio")
    )
    abstract fun toEntity(rallyInfoDto: RallyInfoDto): Rally

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract fun partialUpdate(rallyInfoDto: RallyInfoDto, @MappingTarget rally: Rally): Rally
}
