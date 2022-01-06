package biz.config;

import biz.security.JWTAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilterConfig extends GenericFilter {

    @Autowired
    JWTAuthentication jwtAuthentication;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // Header 토큰값 추출
        String token = jwtAuthentication.getToken((HttpServletRequest) request);
    }
}
