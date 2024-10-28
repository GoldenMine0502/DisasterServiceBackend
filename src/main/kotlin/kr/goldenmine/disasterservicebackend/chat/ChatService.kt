package kr.goldenmine.disasterservicebackend.chat

import kr.goldenmine.disasterservicebackend.chat.objs.Chat
import kr.goldenmine.disasterservicebackend.chat.objs.ChatDTO
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.LocalDateTime



@Service
class ChatService(
    val chatRepository: ChatRepository
) {
    fun getRecentChat(): List<ChatDTO> {
        val yesterday: Timestamp = Timestamp.valueOf(LocalDateTime.now().minusDays(1))
        val chats = chatRepository.getRecentChatsAfter(yesterday)

        return chats.map { it.toDto() }
    }

    fun addChat(text: String, ip: String) {
        val chat = Chat(
            0L,
            Timestamp.valueOf(LocalDateTime.now()),
            ip,
            text,
        )

        chatRepository.save(chat)
    }
}