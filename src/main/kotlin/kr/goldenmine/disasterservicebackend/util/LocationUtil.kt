package kr.goldenmine.disasterservicebackend.util

import kotlin.math.*


/**
 * Calculate distance between two points in latitude and longitude taking
 * into account height difference. If you are not interested in height
 * difference pass 0.0. Uses Haversine method as its base.
 *
 * lat1, lon1 Start point
 * lat2, lon2 End point
 * el1 Start altitude in meters
 * el2 End altitude in meters
 * @returns Distance in Meters
 */
fun distance(
    latitude1: Double, latitude2: Double,
    longitude1: Double, longitude2: Double,
    el1: Double = 0.0, el2: Double = 0.0
): Double {
    val R = 6371 // Radius of the earth
    val latDistance = Math.toRadians(latitude2 - latitude1)
    val lonDistance = Math.toRadians(longitude2 - longitude1)
    val a = (sin(latDistance / 2) * sin(latDistance / 2)
            + (cos(Math.toRadians(latitude1)) * cos(Math.toRadians(latitude2))
            * sin(lonDistance / 2) * sin(lonDistance / 2)))
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    var distance = R * c * 1000 // convert to meters
    val height = el1 - el2
    distance = distance.pow(2.0) + height.pow(2.0)
    return sqrt(distance)
}

fun dmsToDecimal(degree: Int, minute: Int, second: Double): Double {
    return degree + (minute / 60.0) + (second / 3600.0)
}
