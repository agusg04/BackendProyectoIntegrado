package dam.proyecto.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {


    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers(
                        "/",
                        "/api/auth/**",
                        "/api/rally/**"
                        ).permitAll() // permite login y registro
                it.anyRequest().authenticated()
            }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .logout { it.disable() }

        return http.build()
    }

    /*
    @Bean
    fun userDetailsService(): UserDetailsService {
        val user = User.builder()
            .username("usuario")
            .password(passwordEncoder().encode("1234"))
            .roles("USER")
            .build()
        return InMemoryUserDetailsManager(user)
    }

     */

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}