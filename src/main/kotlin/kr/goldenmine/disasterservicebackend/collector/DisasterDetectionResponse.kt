package kr.goldenmine.disasterservicebackend.collector

data class DisasterDetectionResponse(
    val output: List<Double>,
    val label: Int,
) {
    fun labelPercent(): Double {
        return output[label]
    }
}