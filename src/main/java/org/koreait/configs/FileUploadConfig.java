package org.koreait.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix="file.upload") // path , url 값을 변수로 받아서 입력하면 된다.
public class FileUploadConfig { // yml에 있는 # 파일 업로드 설정에 대한 클래스
    private String path;
    private String url;
}
