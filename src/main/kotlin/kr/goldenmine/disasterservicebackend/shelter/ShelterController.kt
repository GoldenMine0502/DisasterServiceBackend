package kr.goldenmine.disasterservicebackend.shelter

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/shelter")
class ShelterController(
    val shelterService: ShelterService,
) {
    @GetMapping(
        "/list"
    )
    fun list(): ShelterListResponse {
        return ShelterListResponse(
            list=shelterService.shelters
        )
    }
}