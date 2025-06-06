package dam.proyecto.models.mappers

import dam.proyecto.models.dtos.UsuarioId
import dam.proyecto.models.dtos.UsuarioRegistradoDto
import dam.proyecto.models.dtos.UsuarioRegistroDto
import dam.proyecto.models.entities.Usuario
import org.mapstruct.*

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
abstract class UsuarioMapper {

    //abstract fun toEntity(usuarioDto: UsuarioDto): Usuario

    //abstract fun toDto(usuario: Usuario): UsuarioDto
    abstract fun toRegistroEntity(usuarioRegistroDto: UsuarioRegistroDto): Usuario
    abstract fun toRegistroDto(guardado: Usuario): UsuarioRegistradoDto
    @Mapping(source = "id", target = "idUsuario")
    @Mapping(source = "email", target = "email")
    abstract fun toUsuarioId(usuario: Usuario): UsuarioId

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract fun partialUpdate(usuarioRegistroDto: UsuarioRegistroDto, @MappingTarget usuario: Usuario): Usuario


    //@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    //abstract fun partialUpdate(usuarioDto: UsuarioDto, @MappingTarget usuario: Usuario): Usuario
}