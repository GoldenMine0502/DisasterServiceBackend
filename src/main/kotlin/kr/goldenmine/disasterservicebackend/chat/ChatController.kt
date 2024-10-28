package kr.goldenmine.disasterservicebackend.chat

import jakarta.servlet.http.HttpServletRequest
import kr.goldenmine.disasterservicebackend.chat.objs.RequestSendChat
import kr.goldenmine.disasterservicebackend.chat.objs.ResponseChatList
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/chat")
class ChatController(
    val chatService: ChatService
) {
    @PutMapping("/send")
    fun sendChat(
        @RequestBody chatRequest: RequestSendChat,
        request: HttpServletRequest,
    ) {
        if(chatRequest.text.isEmpty()) throw RuntimeException()

        chatService.addChat(chatRequest.text, request.remoteAddr)
    }

    @GetMapping("/list")
    fun listChat(): ResponseChatList {
        return ResponseChatList(chatService.getRecentChat())
    }
}