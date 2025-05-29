package dam.proyecto.services.impl

import dam.proyecto.repositories.ResultadoRepository
import dam.proyecto.services.ResultadoService
import org.springframework.stereotype.Service

@Service
class ResultadoServiceImpl(
    private val resultadoRepository: ResultadoRepository

) : ResultadoService {

    override fun incrementarPuntaje(idFoto: Long): Boolean {
        val resultado = resultadoRepository.increasePuntaje(idFoto)

        return resultado > 0
    }

    override fun decrementarPuntaje(idFoto: Long): Boolean {
        val resultado = resultadoRepository.decreasePuntaje(idFoto)

        return resultado > 0
    }
}