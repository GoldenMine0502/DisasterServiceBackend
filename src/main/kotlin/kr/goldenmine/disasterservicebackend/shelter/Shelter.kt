package kr.goldenmine.disasterservicebackend.shelter

data class Shelter(
    val id: Int,
    val name: String,
    val type: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val area: Double,
    val capacity: Int,
    val isOpen: String?,
    val usageType: String?,
    val contactNumber: String?,
    val managingAgency: String?
)