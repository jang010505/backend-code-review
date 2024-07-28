package com.yongfill.server.domain.auth.filter;

import com.yongfill.server.domain.auth.config.CustomMemberDetails;
import com.yongfill.server.domain.auth.config.CustomMemberDetailsService;
import com.yongfill.server.domain.auth.config.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomMemberDetailsService customMemberDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = getTokenFromRequest(request);
//        String refreshToken = getRefreshTokenFromRequest(request);

        // 토큰 유효성 검사
        if (accessToken != null && jwtTokenProvider.validateToken(accessToken)) {
            UsernamePasswordAuthenticationToken authentication = getAuthenticationFromToken(accessToken);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    // JWT 토큰 추출
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 이후 토큰 값만 반환
        }
        return null;
    }

    // JWT 토큰으로부터 인증 객체를 생성합니다.
    private UsernamePasswordAuthenticationToken getAuthenticationFromToken(String accessToken) {
        String email = jwtTokenProvider.getUserEmailFromToken(accessToken);
        UserDetails userDetails = customMemberDetailsService.loadUserByUsername(email);
        System.out.println("getAuthenticationFromToken: " + userDetails);

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}

