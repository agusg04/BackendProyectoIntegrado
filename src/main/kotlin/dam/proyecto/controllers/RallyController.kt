package dam.proyecto.controllers

import dam.proyecto.auth.responses.ApiResponse
import dam.proyecto.models.dtos.RallyInfoDto
import dam.proyecto.services.impl.RallyServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/rally")
class RallyController(private val rallyService: RallyServiceImpl) {

    @GetMapping
    fun rallyInfo(): ResponseEntity<ApiResponse<RallyInfoDto>> {
        val response = rallyService.obtenerDatosRally()
        return ResponseEntity.ok(response)
    }
}