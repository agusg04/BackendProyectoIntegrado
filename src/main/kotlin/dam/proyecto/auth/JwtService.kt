package dam.proyecto.auth

import dam.proyecto.models.enums.Roles
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtService(
    @Value("\${jwt.secret}") secret: String,
    @Value("\${jwt.expiration.access}") private val jwtExpirationAccess: Long,
    @Value("\${jwt.expiration.refresh}") private val jwtExpirationRefresh: Long,
) {

    private val secretKey: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray(Charsets.UTF_8))
    fun generateToken(email: String, id: Long, rol: Roles, expirationMillis: Long): String {
        val claims = mapOf(
            "id" to id,
            "rol" to rol.name
        )
        val now = Date()
        val expiration = Date(now.time + expirationMillis)

        return Jwts.builder()
            .setSubject(email)
            .addClaims(claims)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(secretKey)
            .compact()
    }

    fun generateAccessToken(email: String, userId: Long, rol: Roles): String {
        return generateToken(email, userId, rol, jwtExpirationAccess) // 15 min
    }

    fun generateRefreshToken(email: String, userId: Long, rol: Roles): String {
        return generateToken(email, userId, rol, jwtExpirationRefresh) // 7 días
    }

    fun extractEmail(token: String): String? {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .body
                .subject
        } catch (e: Exception) {
            null
        }
    }

    fun extractUserId(token: String): Long? {
        return try {
            val claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .body

            claims["id"]?.toString()?.toLongOrNull()
        } catch (e: Exception) {
            null
        }
    }

    fun extractIssuedAt(token: String): LocalDateTime? {
        val claims = extractClaims(token) ?: return null
        return claims.issuedAt
            ?.toInstant()
            ?.atZone(ZoneId.of("Europe/Madrid"))
            ?.toLocalDateTime()
    }

    fun isTokenValid(token: String, expectedEmail: String? = null): Boolean {
        return try {
            val claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .body

            val isExpired = claims.expiration.before(Date())
            val emailMatches = expectedEmail?.let { it == claims.subject } ?: true

            !isExpired && emailMatches
        } catch (e: Exception) {
            println(e.message)
            false
        }
    }

    private fun getAllClaims(token: String) = Jwts.parserBuilder()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(token)
        .body

    fun extractClaim(token: String, claim: String): String? = try {
        getAllClaims(token)[claim]?.toString()
    } catch (e: Exception) {
        null
    }

    fun extractClaims(token: String): Claims? {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: Exception) {
            println(e.message)
            null
        }
    }
}
