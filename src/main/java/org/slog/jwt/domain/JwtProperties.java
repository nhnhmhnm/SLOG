package org.slog.jwt.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@Configuration("jwt") // application.yml 프로퍼티 사용
public class JwtProperties {
    private String issuer; // jwt.issuer 매핑
    private String secretKey; // jwt.secret_key 매핑

//    public Key getSecretKey() {
//        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
//    }
}
