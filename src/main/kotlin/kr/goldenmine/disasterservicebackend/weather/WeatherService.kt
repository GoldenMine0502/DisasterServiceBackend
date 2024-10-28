package kr.goldenmine.disasterservicebackend.weather

import com.google.gson.GsonBuilder
import kr.goldenmine.disasterservicebackend.util.distance
import okhttp3.OkHttpClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.nio.charset.Charset
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

@Service
class WeatherService(

) {
    private val logger: Logger = LoggerFactory.getLogger(WeatherService::class.java)

    val locationList = loadLocationData()
    val retrofitService = createRetrofitService()
    val serviceKey = getServiceKeyFromFile()
//    val info = HashMap<Pair<String, String>>

    private final fun getServiceKeyFromFile(): String {
        val file = File("data/servicekey.txt")

        return file.readText()
    }

    private final fun loadLocationData(): List<LocationData> {
        val filePath = "data/240715.csv"
        val locationDataList = mutableListOf<LocationData>()
        File(filePath).bufferedReader(Charset.forName("CP949")).useLines { lines ->
            lines.drop(1).forEach { line -> // 첫 번째 줄은 헤더이므로 건너뜁니다.
                val columns = line.split(",")

                // 각 필드를 적절한 타입으로 변환하여 LocationData 객체 생성
                val locationData = LocationData(
                    country = columns[0],
                    adminCode = columns[1],
                    level1 = columns[2].takeIf { it.isNotEmpty() },
                    level2 = columns[3].takeIf { it.isNotEmpty() },
                    level3 = columns[4].takeIf { it.isNotEmpty() },
                    gridX = columns[5].toInt(),
                    gridY = columns[6].toInt(),
                    longitudeDegree = columns[7].toInt(),
                    longitudeMinute = columns[8].toInt(),
                    longitudeSecond = columns[9].toDouble(),
                    latitudeDegree = columns[10].toInt(),
                    latitudeMinute = columns[11].toInt(),
                    latitudeSecond = columns[12].toDouble(),
                    longitudeSecPer100 = columns[13].toDouble(),
                    latitudeSecPer100 = columns[14].toDouble(),
                    locationUpdate = columns[15].takeIf { it.isNotEmpty() }
                )
                locationDataList.add(locationData)
            }
        }
        return locationDataList
    }

    private final fun createRetrofitService(): WeatherRequestService {
        val gson = GsonBuilder()
            .setLenient()  // Enable lenient mode
            .create()

        // Configure OkHttpClient with custom timeout values
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS) // Connection timeout
            .readTimeout(30, TimeUnit.SECONDS)    // Read timeout
            .writeTimeout(30, TimeUnit.SECONDS)   // Write timeout
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://apis.data.go.kr/") // 기본 URL을 여기에 설정하세요
            .client(okHttpClient)  // Use the customized OkHttpClient
            .addConverterFactory(GsonConverterFactory.create(gson)) // XML 형식이므로 SimpleXml 사용
            .addConverterFactory(ScalarsConverterFactory.create()) // XML 형식이므로 SimpleXml 사용
            .build()

        return retrofit.create(WeatherRequestService::class.java)
    }

    // key ex) "충청북도 충주시 주덕음" (위치)
    // value pair first ex) 20241024-0600 (시간)
    // value pair second ex) 20.8 (온도)
    private val temperatures = HashMap<String, ArrayList<Pair<String, Double>>>()

    fun crawlAll() {
        logger.info("service key: $serviceKey")

        for(location in locationList) {
//            println(location.level1)
            if(location.level1?.contains("충청북도") == true) {
                val nx = location.gridX
                val ny = location.gridY
                val levelKey = location.getLevelKey()
                if(!temperatures.containsKey(levelKey)) {
                    temperatures[levelKey] = ArrayList()
                }

                // i = 시도횟수
                logger.info("crawling $levelKey...")
                for(i in 1..5) {
                    try {
                        crawl(levelKey, nx, ny)
                        Thread.sleep(1000)
                        break
                    } catch(ex: Exception) {
                        logger.error(ex.message, ex)
                        Thread.sleep(5000)
                    }
                }
            }
        }
    }

    fun crawl(levelKey: String, x: Int, y: Int) {
        val currentDate = LocalDate.now().minusDays(1) // 어제로 설정해서 안전하게
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")

        val ymd = currentDate.format(formatter)

        val hms = "0500"

        val response = retrofitService.getWeatherData(
            serviceKey = serviceKey,
            pageNo = 1,
            numOfRows = 1000,
            dataType = "JSON",
            baseDate = ymd,
            baseTime = hms,
            nx = x,
            ny = y,
        ).execute()

//        println(response.raw().body.toString())

        val body = response.body() ?: throw RuntimeException("요청 실패 ${response.code()} ${response.message()}")
//        println(body)
        val values = body.response.body.items.item
            .asSequence()
            .filter { it.category == "TMP" }  // 온도로 필터링
            .map { Pair("${it.fcstDate}-${it.fcstTime}", it.fcstValue.toDouble()) }
            .toList()

        val list = temperatures[levelKey] ?: throw RuntimeException("오류왜남?")

        // 동기화 설정 (리스트가 빌 때 엑세스시 오류 방지)
        synchronized(list) {
            list.clear()
            list.addAll(values)
        }
    }

    fun getTemperature(location: LocationData): Double? {
        val formatter = DateTimeFormatter.ofPattern("yyyyMMddhh00")
        val currentDate = LocalDateTime.now()

        val key = location.getLevelKey()
        val key2 = currentDate.format(formatter)

        val temperatures = temperatures[key] ?: return null

        return temperatures.firstOrNull { it.first == key2 }?.second
    }

    fun getLocationNearby(latitude: Double, longitude: Double): LocationData {
        var result = locationList[0]
        var min = Double.MAX_VALUE

        for(location in locationList) {
            val distance = distance(
                result.latitudeSecPer100, location.latitudeSecPer100,
                result.longitudeSecPer100, location.latitudeSecPer100,
            )

            if(min > distance) {
                result = location
                min = distance
            }
        }

        return result
    }
}