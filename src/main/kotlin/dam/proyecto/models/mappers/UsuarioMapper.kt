package dam.proyecto.models.mappers

import dam.proyecto.models.dtos.UsuarioRegistradoDto
import dam.proyecto.models.dtos.UsuarioRegistroDto
import dam.proyecto.models.entities.Usuario
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import org.mapstruct.ReportingPolicy

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
abstract class UsuarioMapper {

    //abstract fun toEntity(usuarioDto: UsuarioDto): Usuario

    //abstract fun toDto(usuario: Usuario): UsuarioDto

    abstract fun toRegistroEntity(usuarioRegistroDto: UsuarioRegistroDto): Usuario
    abstract fun toRegistroDto(guardado: Usuario): UsuarioRegistradoDto


    //@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    //abstract fun partialUpdate(usuarioDto: UsuarioDto, @MappingTarget usuario: Usuario): Usuario
}