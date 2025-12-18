package com.ssafy.enjoytrip.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.enjoytrip.interceptor.LoginInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
	
	private final LoginInterceptor loginInterceptor;
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 브라우저에서 http://localhost:8080/upload/파일이름.jpg 로 요청이 들어오면
        // 실제 컴퓨터의 file:///C:/ssafy/upload/ 폴더에서 찾겠다는 설정입니다.
        // (Mac이라면 file:///Users/사용자명/Desktop/upload/ 처럼 변경)
        
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:///C:/ssafy/upload/"); 
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                // 이 URL들은 로그인이 필수입니다.
                .addPathPatterns("/user/info")    // 마이페이지
                .addPathPatterns("/user/modify")  // 수정 (PUT) - URL 확인 필요
                .addPathPatterns("/user/delete")  // 탈퇴 (DELETE) - URL 확인 필요
                
               
                .addPathPatterns("/board/write")  // 글쓰기
                .addPathPatterns("/board/modify") // 글수정
                .addPathPatterns("/board/delete") // 글삭제
                .addPathPatterns("/follow/**");   // 팔로우 관련 모든 기능
                
              
    }
}