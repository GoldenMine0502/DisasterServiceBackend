package kr.goldenmine.disasterservicebackend.util

import jakarta.servlet.http.HttpServletRequest
import kr.goldenmine.disasterservicebackend.collector.ImageRequestController
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.IOException
import javax.imageio.ImageIO

class ImageUtil

private val logger: Logger = LoggerFactory.getLogger(ImageUtil::class.java)

fun getImageFromBytes(bytes: ByteArray): BufferedImage {
    // Convert byte[] to InputStream
    val bais = ByteArrayInputStream(bytes)

    // Convert InputStream to BufferedImage
    val bufferedImage = ImageIO.read(bais);

    return bufferedImage
}

fun convertMultipartFileToMultipartBody(file: MultipartFile): MultipartBody.Part {
    val fileName = file.originalFilename
    val requestBody = RequestBody.create(file.contentType?.toMediaTypeOrNull(), file.bytes)
    val imageBody = MultipartBody.Part.createFormData("image", fileName, requestBody)

    return imageBody
}

fun getResponseEntityFromResource(requestServlet: HttpServletRequest, resource: Resource): ResponseEntity<Resource> {
    // Try to determine file's content type
    var contentType: String? = null
    try {
        contentType = requestServlet.servletContext.getMimeType(resource.file.absolutePath)
    } catch (ex: IOException) {
        logger.info("Could not determine file type.")
    }

    // Fallback to the default content type if type could not be determined
    if (contentType == null) {
        contentType = "application/octet-stream"
    }

    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(contentType))
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.filename + "\"")
        .body(resource)
}