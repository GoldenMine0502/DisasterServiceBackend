package kr.goldenmine.disasterservicebackend.chat.objs

import java.sql.Timestamp

class ChatDTO(
    val timestamp: Timestamp,
    val text: String,
    val ip: String,
    ) {
}