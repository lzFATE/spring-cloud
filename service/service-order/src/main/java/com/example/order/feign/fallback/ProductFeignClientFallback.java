package com.example.order.feign.fallback;

import com.example.order.feign.ProductFeignClient;
import com.example.product.bean.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class ProductFeignClientFallback implements ProductFeignClient {

    @Override
    public Product getProduct(Long productId) {
        System.out.println("调用失败，执行兜底回调方法");
        Product product = new Product();
        product.setId(productId);
        product.setProductName("返现两元券");
        product.setPrice(new BigDecimal("0"));
        product.setNum(999);
        return product;
    }
}
