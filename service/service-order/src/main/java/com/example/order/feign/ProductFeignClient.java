package com.example.order.feign;

import com.example.order.feign.fallback.ProductFeignClientFallback;
import com.example.product.bean.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// 声名这是一个远程调用的fenign客户端, 并指定了调用的服务名为service-product
@FeignClient(value = "service-product",fallback = ProductFeignClientFallback.class)
public interface ProductFeignClient {

    // mvc模式，注解的两种使用方式
    // 1、标注在Controller接口上，接收请求
    // 2、标注在FeignClient接口上，发送请求

    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable("id") Long productId);

}
