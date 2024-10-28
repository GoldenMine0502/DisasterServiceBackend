package kr.goldenmine.disasterservicebackend.collector.objs

import java.sql.Timestamp

data class ImageDataDTO(
    val id: Long,
    val timestamp: Timestamp,
    val token: String,
    val label: Int,
    val percent: Double,
) {
}