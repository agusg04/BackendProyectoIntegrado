package dam.proyecto.services

interface ResultadoService {
    fun incrementarPuntaje(idFoto: Long): Boolean
    fun decrementarPuntaje(idFoto: Long): Boolean
}