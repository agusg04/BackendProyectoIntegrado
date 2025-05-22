package dam.proyecto.services

import dam.proyecto.auth.responses.ApiResponse
import dam.proyecto.models.dtos.RallyInfoDto

interface RallyService {
    fun obtenerDatosRally(): ApiResponse<RallyInfoDto>
}