package com.example.collabapp.config;

import com.example.collabapp.components.WebSocketAuthInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.Map;

/**
 * Configures WebSocket messaging support using STOMP.
 *
 * <p>Enables bidirectional communication between clients and the server,
 * allowing features such as real-time collaboration, notifications,
 * and presence indicators.</p>
 *
 * <p>Message flow:</p>
 * <pre>
 * Client --> /app/**  --> @MessageMapping methods
 * Server --> /topic/** --> Broadcast to subscribers
 * Server --> /user/**  --> Private messages to specific users
 * </pre>
 */
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
@Slf4j
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final WebSocketAuthInterceptor webSocketAuthInterceptor;

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor
                        .getAccessor(message, StompHeaderAccessor.class);

                if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
                    Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
                    if (sessionAttributes != null) {
                        String userId = (String) sessionAttributes.get("userId");
                        log.info("STOMP CONNECT — userId: {}", userId);
                    }
                }
                return message;
            }
        });
    }

    /**
     * Configures the message broker used to route messages.
     *
     * <p>The broker tracks subscriptions and delivers messages
     * to the appropriate connected clients.</p>
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        /*
         * Enables Spring's lightweight in-memory broker.
         *
         * "/topic" is used for publish-subscribe messaging
         * (one sender -> many receivers).
         *
         * Example:
         *   /topic/notes/123
         *   All collaborators editing Note 123 receive updates.
         *
         * "/queue" is used for point-to-point messaging
         * (server -> specific user/session).
         *
         * Example:
         *   /user/queue/invitations
         *   Only the intended recipient receives the notification.
         */
        registry.enableSimpleBroker("/topic","/queue");
        /*
         * Prefix for messages sent FROM clients TO the application.
         *
         * Messages beginning with "/app" are routed to methods
         * annotated with @MessageMapping.
         *
         * Example:
         *   Client sends:
         *     /app/note.update
         *
         *   Spring routes to:
         *     @MessageMapping("/note.update")
         */
        registry.setApplicationDestinationPrefixes("/app");
        /*
        * Prefix used for user-specific destinations.
                *
                * Enables APIs such as:
         *   convertAndSendToUser(...)
         *
         * Example:
         *   Server sends:
         *     /user/queue/notifications
                *
                *   Only that authenticated user receives the message.
         */
        registry.setUserDestinationPrefix("/user");
    }

    /**
     * Registers WebSocket endpoints that clients use
     * to establish the initial connection.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        /*
         * Exposes "/ws" as the endpoint used for the
         * WebSocket handshake.
         *
         * Example:
         *   const socket = new SockJS("/ws");
         *
         * During connection:
         *   HTTP Upgrade Request
         *          ↓
         *   Persistent WebSocket Connection
         */
        registry.addEndpoint("/ws")
                /*
                * Allows connections from any origin.
                 *
                 * Useful during development when frontend
                * and backend run on different hosts/ports.
                *
                * In production, replace "*" with specific
                 * trusted frontend domains.
                */
                .setAllowedOriginPatterns("*")
                /*
                 * Enables SockJS fallback options.
                 *
                 * If native WebSockets are unavailable,
                 * SockJS transparently falls back to
                 * alternative transports such as:
                 *
                 * - XHR Streaming
                 * - Long Polling
                 *
                 * Modern browsers usually support WebSockets,
                 * but SockJS improves compatibility.
                 */
                .addInterceptors(webSocketAuthInterceptor)
                .withSockJS();
    }
}
