package kr.goldenmine.disasterservicebackend.collector

import jakarta.servlet.http.HttpServletRequest
import kr.goldenmine.disasterservicebackend.util.convertMultipartFileToMultipartBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.apache.coyote.BadRequestException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.IOException


@RestController
@RequestMapping("/images")
class ImageRequestController(
    val imageRequestService: ImageRequestService
) {
    private val logger: Logger = LoggerFactory.getLogger(ImageRequestController::class.java)

    @PostMapping(
        "check"
    )
    fun newImage(
        requestServlet: HttpServletRequest,
        @RequestPart("image") file: MultipartFile
    ): DisasterDetectionResponse {
        val result = imageRequestService.saveImageAndData("test", file)

        return result
    }
}