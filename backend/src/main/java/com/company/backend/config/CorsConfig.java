package com.company.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
   /**
    * 全局配置 CORS，允许来自 http://localhost:5173 的请求访问后端 API
     * 这对于前端开发环境非常重要，因为前端通常运行在不同的端口上   
     * 日本語訳：全体的なCORS設定を行い、http://localhost:5173からのリクエストがバックエンドAPIにアクセスできるようにします。
     * これはフロントエンドの開発環境にとって非常に重要です。なぜなら、フロントエンドは通常、異なるポートで
    */
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("*");
    }
}