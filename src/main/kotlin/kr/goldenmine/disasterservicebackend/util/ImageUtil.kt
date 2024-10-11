package kr.goldenmine.disasterservicebackend.util

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.springframework.web.multipart.MultipartFile
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO

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