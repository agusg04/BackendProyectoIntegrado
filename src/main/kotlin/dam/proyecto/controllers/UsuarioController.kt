package dam.proyecto.controllers

import dam.proyecto.auth.responses.ApiResponse
import dam.proyecto.data.UserData
import dam.proyecto.models.dtos.ListaUsuarios
import dam.proyecto.models.dtos.UsuarioRegistradoDto
import dam.proyecto.models.dtos.UsuarioRegistroDto
import dam.proyecto.services.UsuarioService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/api/users")
class UsuarioController(
    private val userService: UsuarioService
) {

    @GetMapping
    fun obtenerUsuarios(): ResponseEntity<ApiResponse<ListaUsuarios>> {
        val response = userService.obtenerUsuarios()
        return if (response.success) ResponseEntity.ok(response)
        else ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response)
    }

    @GetMapping("/{id}")
    fun obtenerUsuario(@PathVariable id: Long): ResponseEntity<ApiResponse<UserData>> {
        val response = userService.obtenerUsuario(id)
        return if (response.success) ResponseEntity.ok(response)
        else ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response)
    }

    @PutMapping("/{id}")
    fun actualizarUsuario(
        @PathVariable id: Long,
        @RequestBody usuarioRegistroDto: UsuarioRegistroDto
    ): ResponseEntity<ApiResponse<UsuarioRegistradoDto>> {
        val response = userService.actualizarUsuario(id, usuarioRegistroDto)
        return if (response.success) ResponseEntity.ok(response)
        else ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response)
    }
}