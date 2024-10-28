package kr.goldenmine.disasterservicebackend.weather

data class LocationData(
    val country: String,
    val adminCode: String,
    val level1: String?,
    val level2: String?,
    val level3: String?,
    val gridX: Int,
    val gridY: Int,
    val longitudeDegree: Int,
    val longitudeMinute: Int,
    val longitudeSecond: Double,
    val latitudeDegree: Int,
    val latitudeMinute: Int,
    val latitudeSecond: Double,
    val longitudeSecPer100: Double,
    val latitudeSecPer100: Double,
    val locationUpdate: String?
) {
    fun getLevelKey(): String {
        val builder = StringBuilder("")

        if(level1 == null) {
            return ""
        }

        builder.append(level1)

        if(level2 != null) {
            builder.append(" ")
            builder.append(level2)
        }
        if(level3 != null) {
            builder.append(" ")
            builder.append(level3)
        }

        return builder.toString()
    }
}