package com.company.backend.config;

import java.nio.file.Path;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.company.backend.config.UploadProperties;

@Configuration
public class WebConfig implements WebMvcConfigurer {
        private final UploadProperties uploadProperties;

        WebConfig(UploadProperties uploadProperties) {
                this.uploadProperties = uploadProperties;
        }

        /**
         *  配置静态资源处理器，使得访问 /staff_photo/** 和 /staff_file/** 的请求能够正确地映射到服务器上的文件系统路径。
         *  这对于前端能够正确显示员工照片和下载员工简历非常重要。通过使用 UploadProperties 获取配置的目录路径，并将其转换为绝对路径和 URI，确保了资源能够被正确访问。
         * 日本語訳：静的リソースハンドラーを設定し、/staff_photo/** および /staff_file/** へのリクエストがサーバー上のファイルシステムのパスに正しくマッピングされるようにします。
         * これは、フロントエンドが従業員の写真を正しく表示し、従業員の履歴書をダウンロードできるように
         */
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
                System.out.println("PHOTO PATH=" + Path.of(uploadProperties.getPhotoDir()).toAbsolutePath());

                System.out.println("FILE PATH=" + Path.of(uploadProperties.getFileDir()).toAbsolutePath());
                registry.addResourceHandler("/staff_photo/**")
                                .addResourceLocations(
                                                Path.of(uploadProperties.getPhotoDir())
                                                                .toAbsolutePath()
                                                                .toUri()
                                                                .toString());

                registry.addResourceHandler("/staff_file/**")
                                .addResourceLocations(
                                                Path.of(uploadProperties.getFileDir())
                                                                .toAbsolutePath()
                                                                .toUri()
                                                                .toString());
        }
}