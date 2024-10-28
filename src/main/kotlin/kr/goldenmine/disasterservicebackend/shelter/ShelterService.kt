package kr.goldenmine.disasterservicebackend.shelter

import com.opencsv.CSVReader
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.FileReader
import java.nio.charset.Charset

@Service
class ShelterService {
    val shelters = readSheltersFromCsv()

    private final fun readSheltersFromCsv(): List<Shelter> {
        val filePath = "data/민방위.csv"
        val shelters = mutableListOf<Shelter>()

        CSVReader(FileReader(filePath, Charset.forName("CP949"))).use { reader ->
            reader.skip(1) // 첫 번째 줄을 건너뜀 (헤더)

            reader.forEach { tokens ->
                val shelter = Shelter(
                    id = tokens[0].toInt(),
                    name = tokens[1],
                    type = tokens[2],
                    address = tokens[3],
                    latitude = tokens[4].toDouble(),
                    longitude = tokens[5].toDouble(),
                    area = tokens[6].toDouble(),
                    capacity = tokens[7].toInt(),
                    isOpen = tokens[8].takeIf { it.isNotEmpty() },
                    usageType = tokens[9].takeIf { it.isNotEmpty() },
                    contactNumber = tokens[10].takeIf { it.isNotEmpty() },
                    managingAgency = tokens[11].takeIf { it.isNotEmpty() }
                )
                shelters.add(shelter)
            }
        }
        return shelters
    }
}