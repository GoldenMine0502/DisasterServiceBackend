package kr.goldenmine.disasterservicebackend.chat.objs

import jakarta.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "chat")
class Chat(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "timestamp", nullable = false)
    val timestamp: Timestamp,

    @Column(name = "token", nullable = false)
    val ip: String,

    @Column(name = "text", nullable = false)
    val text: String,
) {

    fun getMaskedIp(ip: String): String {
        val parts = ip.split(".")

        return listOf(
            parts[0],
            parts[1],
            "*".repeat(parts[2].length),
            "*".repeat(parts[3].length)
        ).joinToString(".")
    }

    fun toDto(): ChatDTO {
        return ChatDTO(
            timestamp,
            text,
            getMaskedIp(ip),
        )
    }
}