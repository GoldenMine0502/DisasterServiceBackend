package kr.goldenmine.disasterservicebackend.collector

import kr.goldenmine.disasterservicebackend.collector.objs.ImageData
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ImageDataRepository: JpaRepository<ImageData, Long> {
    @Query(
        "SELECT data FROM ImageData data WHERE data.label != :normalImageLabel"
    )
    fun findRecentDisasterImage(normalImageLabel: Int, pageable: Pageable): List<ImageData>
}