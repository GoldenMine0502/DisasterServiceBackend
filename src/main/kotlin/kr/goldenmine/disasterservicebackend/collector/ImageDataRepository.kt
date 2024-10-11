package kr.goldenmine.disasterservicebackend.collector

import org.springframework.data.jpa.repository.JpaRepository

interface ImageDataRepository: JpaRepository<ImageData, Long> {

}