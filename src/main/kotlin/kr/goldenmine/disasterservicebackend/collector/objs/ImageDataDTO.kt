package kr.goldenmine.disasterservicebackend.collector.objs

data class ImageDataDTO(
    val token: String,
    val label: Int,
    val percent: Double,
) {
}