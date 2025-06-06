package dam.proyecto.models.mappers

import dam.proyecto.data.VotoUsuario
import dam.proyecto.models.dtos.VotoDto
import dam.proyecto.models.entities.Voto
import org.mapstruct.*

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
abstract class VotoMapper {

    @Mappings(Mapping(source = "fotoId", target = "foto.id"), Mapping(source = "votanteId", target = "votante.id"))
    abstract fun toEntity(votoDto: VotoDto): Voto

    @InheritInverseConfiguration(name = "toEntity")
    abstract fun toDto(voto: Voto): VotoDto

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract fun partialUpdate(votoDto: VotoDto, @MappingTarget voto: Voto): Voto

    @Mapping(source = "id", target = "votoId")
    @Mapping(source = "foto.filePath", target = "urlFoto")
    abstract fun toVotoUsuario(voto: Voto): VotoUsuario
}