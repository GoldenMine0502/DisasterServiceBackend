package kr.goldenmine.disasterservicebackend.weather

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

// 지알아서 돌아감
@Service
class WeatherAutoCrawler(
    val weatherService: WeatherService
) {
    private val logger: Logger = LoggerFactory.getLogger(WeatherService::class.java)

    init {
        WeatherAutoCrawlerThread().start()
    }

    val sleepTime = 1000L * 60L * 60L * 6L // 6시간

    inner class WeatherAutoCrawlerThread: Thread() {
        override fun run() {
            logger.info("crawling weather started.")
            while(true) {
                weatherService.crawlAll()
                logger.info("crawling weather epoch finished.")
                sleep(sleepTime)
            }
        }
    }
}
