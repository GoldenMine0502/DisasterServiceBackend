package kr.goldenmine.disasterservicebackend.collector

import kr.goldenmine.disasterservicebackend.util.convertMultipartFileToMultipartBody
import org.apache.coyote.BadRequestException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

@Service
class ImageRequestService(
    val imageDataRepository: ImageDataRepository,
    val fileService: FileService,
) {
    private val logger: Logger = LoggerFactory.getLogger(ImageRequestService::class.java)

    val service: DisasterDetectionServer = Retrofit.Builder()
        .baseUrl("http://localhost:8000")
        .addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
        .create(DisasterDetectionServer::class.java)

    fun saveImageAndData(token: String, file: MultipartFile): DisasterDetectionResponse {
        val imageBody = convertMultipartFileToMultipartBody(file)
        val result = service.request(imageBody).execute().body() ?: throw BadRequestException("Bad Request")

        val imageData = ImageData(
            0,
            token,
            result.label,
            result.labelPercent(),
        )

        val id = imageDataRepository.save(imageData).id
        fileService.storeFile(id, file)

        logger.info("id: $id, data: $imageData")

        return result
    }
}