package kr.goldenmine.disasterservicebackend.props

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "image")
class ImageProperties {
    var directory: String = ""
    var normalImageLabel: Int = 3
}