package dam.proyecto

import java.time.LocalDateTime
import java.time.ZoneId

object FechaUtils {
    fun ahora(): LocalDateTime {
        return LocalDateTime.now(ZoneId.of("Europe/Madrid"))
    }
}