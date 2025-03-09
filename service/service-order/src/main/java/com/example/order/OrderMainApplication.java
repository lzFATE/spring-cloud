package com.example.order;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@EnableFeignClients // 开启Feign客户端
@EnableDiscoveryClient // 开启服务发现
@SpringBootApplication
public class OrderMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderMainApplication.class, args);
        System.out.println("订单服务启动成功...");
    }

    // 1、项目启动任务监听配置文件变化
    // 2、获取配置文件信息
    // 3、发送监听事件通知
    @Bean
    ApplicationRunner applicationRunner(NacosConfigManager nacosConfigManager) {
         return args -> {
                ConfigService configService = nacosConfigManager.getConfigService();
                configService.addListener("service-order.yaml",
                        "DEFAULT_GROUP", new Listener() {
                            @Override
                            public Executor getExecutor() {
                                return Executors.newFixedThreadPool(4);
                            }

                            @Override
                            public void receiveConfigInfo(String configInfo) {
                                System.out.println("配置文件变化信息：" + configInfo);
                                System.out.println("邮件通知...");
                            }
                        });
                System.out.println("监听到配置文件变化");

        };
    }
}
