package dam.proyecto

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class HealthCheck {
    @GetMapping("/")
    fun healthCheck(): Boolean {
        return true
    }
}

