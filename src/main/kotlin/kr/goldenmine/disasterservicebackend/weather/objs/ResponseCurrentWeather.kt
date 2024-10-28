package kr.goldenmine.disasterservicebackend.weather.objs

import kr.goldenmine.disasterservicebackend.weather.LocationData

class ResponseCurrentWeather(
    val locationData: LocationData,
    val temperature: Double,
) {
}