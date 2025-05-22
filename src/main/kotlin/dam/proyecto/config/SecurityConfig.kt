package dam.proyecto.config

import dam.proyecto.auth.JwtAuthFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthFilter: JwtAuthFilter
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers(
                        "/",
                        "/uploads/**",
                        "/api/auth/**",
                        "/api/rally",
                        "/api/photos/**"
                    ).permitAll()

                    .requestMatchers("/api/votos/**",
                                    ).hasAnyRole("USUARIO", "ADMIN")

                    .requestMatchers("/api/admin/**",
                                     "/api/rally/modificar",
                                     "/api/usuarios/**"
                                    ).hasRole("ADMIN")

                    .requestMatchers("/logout").denyAll()

                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .logout { it.disable() }

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}