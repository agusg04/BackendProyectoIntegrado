package dam.proyecto.services.impl

import dam.proyecto.auth.responses.ApiResponse
import dam.proyecto.data.FotoUsuario
import dam.proyecto.data.FotografiaPost
import dam.proyecto.data.FotografiaWallData
import dam.proyecto.models.mappers.FotografiaMapper
import dam.proyecto.repositories.FotografiaRepository
import dam.proyecto.services.FotografiaService
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

@Service
class FotografiaServiceImpl(
    private val fotografiaRepository: FotografiaRepository,
    private val fotografiaMapper: FotografiaMapper
    ) : FotografiaService {
    override fun obtenerFotos(): ApiResponse<FotografiaWallData> {
        val validatedPhotos = fotografiaRepository.findAllByEstado()

        if (validatedPhotos.isEmpty()) {
            return ApiResponse(
                success = false,
                message = "No hay fotos disponibles",
                data = null
            )
        }

        val photoSet = validatedPhotos.map { fotografiaMapper.toDtoPost(it) }.toSet()

        return ApiResponse(
            success = true,
            message = "Muro de fotos devuelto con éxito",
            data = FotografiaWallData(photoSet)
        )
    }

    override fun obtenerFotosDeUsuario(idUsuario: Long): Set<FotoUsuario> {
        val fotosUsuario = fotografiaRepository.findAllByUsuario_Id(idUsuario)

        if (fotosUsuario.isEmpty()) {
            return emptySet()
        }

        return fotosUsuario.map { fotografiaMapper.toFotoUsuario(it) }.toSet()
    }

    override fun obtenerFoto(id: Long): ApiResponse<FotografiaPost> {
        val foto = fotografiaRepository.findById(id).orElse(null)
            ?: return ApiResponse(
                success = false,
                message = "Esa foto no esta disponible",
                data = null
            )

        return ApiResponse(
            success = true,
            message = "Muro de fotos devuelto con éxito",
            data = fotografiaMapper.toDtoPost(foto)
        )
    }

    override fun obtenerImagen(id: Long): ApiResponse<Resource> {

        val foto = fotografiaRepository.findById(id).orElse(null)
            ?: return ApiResponse(
                success = false,
                message = "Foto no encontrada",
                data = null
            )

        val filePath = foto.filePath?.let { java.io.File(it) }  // Creamos un objeto File a partir de la URL

        if (filePath == null || !filePath.exists()) {
            return ApiResponse(
                success = false,
                message = "Imagen no encontrada",
                data = null
            )
        }

        val resource: Resource = FileSystemResource(filePath)

        return ApiResponse(
            success = true,
            message = "Imagen cargada con éxito",
            data = resource
        )
    }

    /*
    fun obtenerFotosPaginadas(page: Int, size: Int): ApiResponse<List<FotografiaPost>> {
        val pageable = PageRequest.of(page, size, Sort.by("id").descending())
        val pagina = fotografiaRepository.findAll(pageable)
        val fotos = pagina.content.map { it.toFotografiaPost() } // Usa tu mapper

        return ApiResponse.success(fotos)
    }

     */
}
