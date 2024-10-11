package kr.goldenmine.disasterservicebackend.collector.objs

data class DisasterDetectionResponse(
    val output: List<Double>,
    val label: Int,
) {
    fun labelPercent(): Double {
        return output[label]
    }
}