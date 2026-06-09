package com.example.collabapp.components;

import com.example.collabapp.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
@RequiredArgsConstructor //creates a constructor with all final fields, helping in DI in spring, ideal for creating immutable objects, implements constructor injection
public class WebSocketAuthInterceptor implements HandshakeInterceptor {
    private final JwtService jwtService;

    @Override
    public boolean beforeHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response,
                                   @NonNull WebSocketHandler wsHandler,
                                   @NonNull Map<String,Object> attributes){
        if(request instanceof ServletServerHttpRequest servletRequest){
            String token = servletRequest.getServletRequest().getParameter("token");
            if(token!=null && jwtService.isTokenValid(token)){
                String userId = jwtService.extractUserId(token);
                attributes.put("userId",userId); //makes userId available in session
                return true;
            }
        }
        return false;
    }

    @Override
    public void afterHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response,
                               @NonNull WebSocketHandler wsHandler, Exception exception){}

}
