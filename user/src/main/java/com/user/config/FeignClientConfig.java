package com.user.config;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignClientConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = 
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            
            String username = request.getHeader("X-User-Name");
            String roles = request.getHeader("X-User-Roles");

            if (username != null) {
                template.header("X-User-Name", username);
            }
            if (roles != null) {
                template.header("X-User-Roles", roles);
            }
        }
    }
}