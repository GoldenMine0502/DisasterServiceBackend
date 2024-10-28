package kr.goldenmine.disasterservicebackend.chat

import kr.goldenmine.disasterservicebackend.chat.objs.Chat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.sql.Timestamp

@Repository
interface ChatRepository: JpaRepository<Chat, Long> {
    // 입력한 시간 이후인 채팅 목록
    @Query(
        "SELECT chat FROM Chat chat WHERE chat.timestamp >= :time ORDER BY chat.timestamp DESC"
    )
    fun getRecentChatsAfter(time: Timestamp): List<Chat>
}