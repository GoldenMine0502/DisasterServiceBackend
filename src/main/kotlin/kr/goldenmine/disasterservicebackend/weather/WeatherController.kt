package kr.goldenmine.disasterservicebackend.weather

import kr.goldenmine.disasterservicebackend.weather.objs.RequestCurrentWeather
import kr.goldenmine.disasterservicebackend.weather.objs.ResponseCurrentWeather
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/weather")
class WeatherController(
    val service: WeatherService
) {
    @PostMapping(
        "/current"
    )
    fun current(
        @RequestBody request: RequestCurrentWeather,
    ): ResponseCurrentWeather {
        val location = service.getLocationNearby(request.latitude, request.longitude)
        val temperature = service.getTemperature(location) ?: 25.0

        return ResponseCurrentWeather(
            location,
            temperature
        )
    }
}