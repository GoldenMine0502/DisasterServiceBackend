package kr.goldenmine.disasterservicebackend.weather

import kr.goldenmine.disasterservicebackend.weather.objs.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherRequestService {

    @GET("/VilageFcstInfoService_2.0/getVilageFcst")
    fun getWeatherData(
        @Query("ServiceKey") serviceKey: String,
        @Query("pageNo") pageNo: Int = 1,
        @Query("numOfRows") numOfRows: Int = 1000,
        @Query("dataType") dataType: String = "XML",
        @Query("base_date") baseDate: String,
        @Query("base_time") baseTime: String = "0500",
        @Query("nx") nx: Int,
        @Query("ny") ny: Int,
    ): Call<ApiResponse>
}