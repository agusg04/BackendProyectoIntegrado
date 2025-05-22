package dam.proyecto.services.impl

import dam.proyecto.auth.responses.ApiResponse
import dam.proyecto.models.dtos.RallyInfoDto
import dam.proyecto.models.mappers.RallyMapper
import dam.proyecto.repositories.RallyRepository
import dam.proyecto.services.RallyService
import org.springframework.stereotype.Service

@Service
class RallyServiceImpl(
    private val rallyRepository: RallyRepository,
    private val rallyMapper: RallyMapper

) : RallyService {
    override fun obtenerDatosRally(): ApiResponse<RallyInfoDto> {
        val rally = rallyRepository.findRally()

        return ApiResponse(
            success = true,
            message = "Iformacion rally",
            data = rallyMapper.toDto(rally)
        )
    }


}