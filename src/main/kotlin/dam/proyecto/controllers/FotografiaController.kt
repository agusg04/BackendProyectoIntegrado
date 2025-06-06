package dam.proyecto.controllers

import dam.proyecto.auth.responses.ApiResponse
import dam.proyecto.data.FotografiaPost
import dam.proyecto.data.FotografiaWallData
import dam.proyecto.services.FotografiaService
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.nio.file.Files

@RestController
@RequestMapping("/api/photos")
class FotografiaController(
    private val fotografiaService: FotografiaService
) {

    @GetMapping
    fun obtenerTodas(): ResponseEntity<ApiResponse<FotografiaWallData>> {
        val response = fotografiaService.obtenerFotos()
        return ResponseEntity.ok(response)
    }

    /*
    @GetMapping
    fun obtenerPaginadas(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<ApiResponse<List<FotografiaPost>>> {
        val response = fotografiaService.obtenerFotosPaginadas(page, size)
        return ResponseEntity.ok(response)
    }
    
     */

    @GetMapping("/{id}")
    fun obtenerPorId(@PathVariable id: Long): ResponseEntity<ApiResponse<FotografiaPost>> {
        val response = fotografiaService.obtenerFoto(id)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}/image")
    fun obtenerImagen(@PathVariable id: Long): ResponseEntity<Resource> {
        val response = fotografiaService.obtenerImagen(id)

        return if (response.success) {
            val resource: Resource = response.data ?: return ResponseEntity.notFound().build()

            // Detectar tipo MIME del archivo
            val file = resource.file
            val mimeType = Files.probeContentType(file.toPath()) ?: "application/octet-stream"

            // Retornar la imagen con el tipo MIME correcto
            ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, mimeType)  // Aquí asignamos el tipo MIME detectado
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"${file.name}\"")  // Usamos el nombre original del archivo
                .body(resource)
        } else {
            ResponseEntity.badRequest().body(null)
        }
    }
}