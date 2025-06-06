package dam.proyecto.services.impl

import dam.proyecto.FechaUtils
import dam.proyecto.auth.responses.ApiResponse
import dam.proyecto.data.ListaVotos
import dam.proyecto.data.VotoUsuario
import dam.proyecto.models.dtos.VotoDto
import dam.proyecto.models.mappers.VotoMapper
import dam.proyecto.repositories.VotoRepository
import dam.proyecto.services.FotografiaService
import dam.proyecto.services.ResultadoService
import dam.proyecto.services.VotoService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class VotoServiceImpl(
    private val votoRepository: VotoRepository,
    private val fotografiaServiceImpl: FotografiaService,
    private val votoMapper: VotoMapper,
    private val resultadoServiceImpl: ResultadoService

) : VotoService {
    private val logger = LoggerFactory.getLogger(VotoServiceImpl::class.java)

    override fun obtenerIdsFotosVotadasPorUsuario(idUsuario: Long): ApiResponse<ListaVotos>? {
        val votos = votoRepository.findFotosIdsByVotanteId(idUsuario) ?: return null

        if (votos.isEmpty()) {
            return ApiResponse(
                success = false,
                message = "No hay votos del usuario disponibles",
                data = null
            )
        }

        return ApiResponse(
            success = true,
            message = "Lista de votos del usuario",
            data = ListaVotos(votos)
        )
    }

    override fun votar(idUsuario: Long, idFoto: Long): ApiResponse<Boolean> {
        val foto = fotografiaServiceImpl.obtenerFoto(idFoto)

        if (foto.data == null) {
            return ApiResponse(
                success = false,
                message = "Fotografía no encontrada",
                data = false
            )
        }

        val yaVotada = votoRepository.existsByVotanteIdAndFotoId(idUsuario, idFoto)

        if (yaVotada) {
            return ApiResponse(
                false,
                "Foto ya votada",
                false
            )
        }

        val votoDto = VotoDto(idFoto, idUsuario, FechaUtils.ahora())
        val votoGuardado = votoRepository.save(votoMapper.toEntity(votoDto))

        val resultadoFoto = resultadoServiceImpl.incrementarPuntaje(idFoto)
        if (!resultadoFoto) {
            votoRepository.delete(votoGuardado)
            return ApiResponse(
                success = false,
                message = "No se pudo actualizar el puntaje de la foto",
                data = false
            )
        }

        return if (votoGuardado.id != null) {
            ApiResponse(
                success = true,
                message = "Voto registrado correctamente",
                data = true
            )
        } else {
            ApiResponse(
                success = false,
                message = "No se pudo registrar el voto",
                data = false
            )
        }
    }

    override fun eliminarVoto(idUsuario: Long, idFoto: Long): ApiResponse<Boolean> {
        val voto = votoRepository.findVotoByVotanteIdAndFotoId(idUsuario, idFoto)
            ?: return ApiResponse(
                success = false,
                message = "Foto no votada",
                data = false
            )

        votoRepository.delete(voto)

        val votoSigueExistiendo = votoRepository.existsByVotanteIdAndFotoId(idUsuario, idFoto)

        val resultadoFoto = resultadoServiceImpl.decrementarPuntaje(idFoto)
        if (!resultadoFoto) {
            votoRepository.save(voto)
            return ApiResponse(
                success = false,
                message = "No se pudo actualizar el puntaje de la foto",
                data = false
            )
        }

        return if (!votoSigueExistiendo) {
            logger.info("Voto del usuario $idUsuario eliminado correctamente")
            ApiResponse(
                success = true,
                message = "Voto eliminado correctamente",
                data = true
            )
        } else {
            ApiResponse(
                success = false,
                message = "No se pudo eliminar el voto",
                data = false
            )
        }
    }

    override fun obtenerVotosUsuario(idUsuario: Long): Set<VotoUsuario> {
        val votos = votoRepository.findVotosByVotante_Id(idUsuario)

        if (votos.isEmpty()) {
            return emptySet()
        }

        return votos.map { votoMapper.toVotoUsuario(it) }.toSet()
    }
}