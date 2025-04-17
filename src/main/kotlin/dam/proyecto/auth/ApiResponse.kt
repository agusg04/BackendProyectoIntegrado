package dam.proyecto.auth

data class ApiResponse<T>(
    val success: Boolean,
    val message: String?,
    val data: T? = null
)
