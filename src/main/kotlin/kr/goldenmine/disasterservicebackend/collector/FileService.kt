package kr.goldenmine.disasterservicebackend.collector

import kr.goldenmine.disasterservicebackend.props.ImageProperties
import kr.goldenmine.disasterservicebackend.util.impl.FileStorageException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import javax.imageio.ImageIO
import kotlin.math.floor
import kotlin.math.min


@Service
class FileService(
    private final val imageProperties: ImageProperties
) {
    private val logger: Logger = LoggerFactory.getLogger(FileService::class.java)

    private val fileStorageLocation = Paths.get(imageProperties.directory)
        .toAbsolutePath().normalize()

    init {
        try {
            Files.createDirectories(fileStorageLocation)
        } catch (ex: Exception) {
            throw FileStorageException("Could not create the directory where the uploaded files will be stored.")
        }
    }

    fun storeFile(id: Long, file: MultipartFile): String {
        val fileName = "${id}.jpg"

        val minimizedImage = getMinimizedImage(file)
        val bos = ByteArrayOutputStream()
        ImageIO.write(minimizedImage, "jpg", bos)
        val inputStream = ByteArrayInputStream(bos.toByteArray())

        Files.copy(inputStream, fileStorageLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING)
        inputStream.close()
        minimizedImage.flush()

        logger.info("file saved: $fileName")

        return fileName
    }

    fun getMinimizedImage(file: MultipartFile): BufferedImage {
        val originalImage = ImageIO.read(file.inputStream)

        val maxWidth = 448
        val maxHeight = 448

        val currentWidth = originalImage.width.toDouble()
        val currentHeight = originalImage.height.toDouble()

        val nextRatio = min(maxWidth / currentWidth, maxHeight / currentHeight)
        val nextWidth = min(currentWidth, floor(currentWidth * nextRatio)).toInt()
        val nextHeight = min(currentHeight, floor(currentHeight * nextRatio)).toInt()

        val resizedImage = BufferedImage(nextWidth, nextHeight, BufferedImage.TYPE_INT_RGB)
        val graphics2D = resizedImage.createGraphics()
        graphics2D.drawImage(originalImage, 0, 0, nextWidth, nextHeight, null)
        graphics2D.dispose()

        originalImage.flush()

        return resizedImage
    }

    fun loadImage(id: Long): Resource {
        val fileName = "$id.jpg"

        return try {
            val filePath = fileStorageLocation.resolve(fileName).normalize()
            val resource = UrlResource(filePath.toUri())
            if (resource.exists()) {
                resource
            } else {
                throw FileStorageException("File not found $fileName")
            }
        } catch (ex: MalformedURLException) {
            throw FileStorageException("File not found $fileName")
        }
    }
}
