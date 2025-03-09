package com.example.order.controller;

import com.example.order.bean.Order;
import com.example.order.properties.OrderProperties;
import com.example.order.service.OrderService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
// @RefreshScope 自动刷新配置
@RestController
public class OrderController {

    @Autowired
    OrderService ordreService;
    @Autowired
    OrderProperties orderProperties;

//    @Value("${order.timeout}")
//    private String orderTimeout;
//
//    @Value("${order.auto-confirm}")
//    private String orderAutoConfirm;

    @GetMapping("/config")
    public String getConfig() {
        return "order.timeout=" + orderProperties.getTimeout() +
                ", order.auto-confirm=" + orderProperties.getAutoConfirm() +
                ", db.url=" + orderProperties.getDbUrl();
    }

    // 创建订单
    @GetMapping("/create")
    public Order createOrder(@RequestParam("userId") Long userId,
                             @RequestParam("productId") Long productId) {
        Order order = ordreService.createOrder(userId, productId);
        return order;
    }

    // 创建订单
    @GetMapping("/seckill")
    public Order seckill(@RequestParam("userId") Long userId,
                             @RequestParam("productId") Long productId) {
        Order order = ordreService.createOrder(userId, productId);
        order.setId(Long.MAX_VALUE);
        return order;
    }

    @GetMapping("/writeDb")
    public String writeDb() {
        // 写入数据库
        return " write db success";
    }

    @GetMapping("/readDb")
    public String readDb() {
        log.info("readDb方法被调用");
        // 写入数据库
        return " read db success";
    }

}
