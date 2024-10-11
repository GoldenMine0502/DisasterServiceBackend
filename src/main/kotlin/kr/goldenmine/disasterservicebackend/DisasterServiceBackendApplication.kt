package kr.goldenmine.disasterservicebackend

import kr.goldenmine.disasterservicebackend.props.ImageProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(*[ImageProperties::class])
class DisasterServiceBackendApplication

fun main(args: Array<String>) {
    runApplication<DisasterServiceBackendApplication>(*args)
}
