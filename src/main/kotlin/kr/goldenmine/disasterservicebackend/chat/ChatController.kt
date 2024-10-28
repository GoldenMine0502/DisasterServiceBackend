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

        chatService.addChat(chatRequest.text, getClientIp(request))
    }

    fun getClientIp(request: HttpServletRequest): String {
        var clientIp = request.getHeader("X-Forwarded-For")
        if (clientIp == null || clientIp.isEmpty() || "unknown".equals(clientIp, ignoreCase = true)) {
            clientIp = request.remoteAddr
        }
        return clientIp
    }

    @GetMapping("/list")
    fun listChat(): ResponseChatList {
        return ResponseChatList(chatService.getRecentChat())
    }
}