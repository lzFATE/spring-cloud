package com.example.product.service.impl;

import com.example.product.bean.Product;
import com.example.product.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Service
public class ProductServiceImpl implements ProductService {

    @Override
    public Product getProductById(Long productId) {
        Product product = new Product();
        product.setId(productId);
        product.setProductName("Zippo打火机-");
        product.setPrice(new BigDecimal("159.9"));
        product.setNum(999);

//        try {
//            TimeUnit.SECONDS.sleep(61);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        return product;
    }
}
