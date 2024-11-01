package kr.goldenmine.disasterservicebackend.collector.objs

import jakarta.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "image_data")
class ImageData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "timestamp", nullable = false)
    var timestamp: Timestamp,

    @Column(name = "token", nullable = false)
    var token: String,

    @Column(name = "label", nullable = false)
    var label: Int,

    @Column(name = "percent", nullable = false)
    var percent: Double,
) {
    override fun toString(): String {
        return "ImageData(id=$id, token='$token', label=$label, percent=$percent)"
    }

    fun toDto(): ImageDataDTO {
        return ImageDataDTO(
            id,
            timestamp,
            token,
            label,
            percent,
        )
    }
}