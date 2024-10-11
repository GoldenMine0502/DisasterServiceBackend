package kr.goldenmine.disasterservicebackend.collector

import jakarta.servlet.http.HttpServletRequest
import kr.goldenmine.disasterservicebackend.collector.objs.DisasterDetectionResponse
import kr.goldenmine.disasterservicebackend.collector.objs.RecentDisasterImageResponse
import kr.goldenmine.disasterservicebackend.util.getResponseEntityFromResource
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/images")
class ImageRequestController(
    val imageRequestService: ImageRequestService,
    private val fileService: FileService
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

    @GetMapping(
        "recent"
    )
    fun recentImages(): RecentDisasterImageResponse {
        val result = imageRequestService.recentDisasterImages()

        return RecentDisasterImageResponse(result)
    }

    @GetMapping(
        "image/{id}"
    )
    fun getImage(
        @PathVariable id: Long,
        requestServlet: HttpServletRequest,
    ): ResponseEntity<Resource> {
        val resource = fileService.loadImage(id)

        return getResponseEntityFromResource(requestServlet, resource)
    }
}