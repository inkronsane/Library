package com.inkronsane.Library.secutiry;


import com.inkronsane.Library.entity.*;
import com.inkronsane.Library.repository.*;
import com.inkronsane.Library.service.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.*;
import lombok.*;
import org.antlr.v4.runtime.misc.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.context.*;
import org.springframework.security.web.authentication.*;
import org.springframework.stereotype.*;
import org.springframework.web.filter.*;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

   @Autowired
   private final JwtService jwtService;
   @Autowired
   private final UserRepository userService;

   @Override
   protected void doFilterInternal(
     @NotNull HttpServletRequest request,
     @NotNull HttpServletResponse response,
     @NotNull FilterChain filterChain)
     throws ServletException, IOException {
      final String authHeader = request.getHeader("Authorization");
      final String jwt;
      final String username;
      if (authHeader == null || !authHeader.startsWith("Bearer ")) {
         filterChain.doFilter(request, response);
         return;
      }
      jwt = authHeader.substring(7);
      username = jwtService.extractUsername(jwt);
      if (username != null || SecurityContextHolder.getContext().getAuthentication() == null) {
         User user = userService.findByUsername(username).orElseThrow();
         if (jwtService.isTokenValid(jwt, user)) {
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            UsernamePasswordAuthenticationToken token =
              new UsernamePasswordAuthenticationToken(
                user.getId(), null,
                List.of(new SimpleGrantedAuthority(user.getRole().name())));
            token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            securityContext.setAuthentication(token);
            SecurityContextHolder.setContext(securityContext);
         }
      }
      filterChain.doFilter(request, response);
   }
}