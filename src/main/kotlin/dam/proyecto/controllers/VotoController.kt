package dam.proyecto.controllers

import dam.proyecto.auth.UserPrincipal
import dam.proyecto.auth.requests.VoteRequest
import dam.proyecto.auth.responses.ApiResponse
import dam.proyecto.data.ListaVotos
import dam.proyecto.services.impl.VotoServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/votes")
class VotoController(
    private val votoService: VotoServiceImpl
) {
    @GetMapping
    fun obtenerTodosParaUsuario(@AuthenticationPrincipal user: UserPrincipal): ResponseEntity<ApiResponse<ListaVotos>> {
        //Rescatar id del SecurityHolder con una clase auxiliar o alg
        //val username = SecurityContextHolder.getContext().authentication?.name
        val response = user.id?.let { votoService.obtenerIdsFotosVotadasPorUsuario(it) }
        return if (response?.success == true) ResponseEntity.ok(response)
        else ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response)
    }

    @PostMapping
    fun votarFotografia(
        @RequestBody request : VoteRequest,
        @AuthenticationPrincipal user: UserPrincipal
    ): ResponseEntity<ApiResponse<Boolean>> {
        val response = user.id?.let { votoService.votar(it, request.idFoto) }
        return if (response?.success == true) ResponseEntity.ok(response)
        else ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response)
    }

    @DeleteMapping
    fun quitarVotoFotografia(
        @RequestBody request: VoteRequest,
        @AuthenticationPrincipal user: UserPrincipal
    ): ResponseEntity<ApiResponse<Boolean>> {
        val response = user.id?.let { votoService.eliminarVoto(it, request.idFoto) }
        return if (response?.success == true) ResponseEntity.ok(response)
        else ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response)
    }

}