package com.example.order.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "order") // 配置批量绑定配置项，自动刷新
@Component
public class OrderProperties {

    String timeout;
    String autoConfirm;
    String dbUrl;

}
