package kr.goldenmine.disasterservicebackend.weather

import kr.goldenmine.disasterservicebackend.weather.objs.RequestCurrentWeather
import kr.goldenmine.disasterservicebackend.weather.objs.ResponseCurrentWeather
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/weather")
class WeatherController(
    val service: WeatherService
) {
    @GetMapping(
        "/current"
    )
    fun current(
        request: RequestCurrentWeather,
    ): ResponseCurrentWeather {
        val location = service.getLocationNearby(request.latitude, request.longitude)
        val temperature = service.getTemperature(location) ?: -100.0

        return ResponseCurrentWeather(
            location,
            temperature
        )
    }
}