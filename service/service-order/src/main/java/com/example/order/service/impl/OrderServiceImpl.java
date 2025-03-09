package com.example.order.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.example.order.bean.Order;
import com.example.order.feign.ProductFeignClient;
import com.example.product.bean.Product;
import com.example.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    RestTemplate restTemplate;

    @Autowired // 服务需要导入 spring-cloud-starter-loadbalancer 依赖
    LoadBalancerClient loadBalancerClient;

    @Autowired
    ProductFeignClient productFeignClient;

    @SentinelResource(value = "createOrder",blockHandler = "createOrderFallback")
    @Override
    public Order createOrder(Long userId, Long productId) {
        // 使用Product product = getProductFromRemoteWithLoadBalanceAnnotation(productId);

        // 使用 FeignClient 实现远程调用
        Product product = productFeignClient.getProduct(productId);

        Order order = new Order();
        order.setId(1L);
        // TODO: 计算 总金额
        product.getPrice().multiply(new BigDecimal(product.getNum()));
        order.setTotalAmount(new BigDecimal(product.getNum()));
        order.setUserId(userId);
        order.setNickName("admin");
        order.setAddress("北京");
        // TODO: 远程查询 商品列表
        order.setProductList(Arrays.asList(product));

        return order;
    }

    // 兜底回调方法
    public Order createOrderFallback(Long userId, Long productId, BlockException e) {
        Order order = new Order();
        order.setId(0L);
        order.setTotalAmount(new BigDecimal(0));
        order.setUserId(userId);
        order.setNickName("未知用户");
        order.setAddress("未知地址");
        return order;
    }

    // 1、使用 RestTemplate 实现远程调用
    private Product getProductFromRemote(Long productId) {
        // TODO: 远程查询商品信息
        // 1、获取到商品服务所在的机器IP地址 + port端口号
        List<ServiceInstance> ServiceInstances = discoveryClient.getInstances("service-product");

        ServiceInstance serviceInstance = ServiceInstances.get(0);
        String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/product/" + productId;
        log.info("远程请求: " + url);
        // 2、给远程商品服务发送请求，获取商品信息
        Product product = restTemplate.getForObject(url, Product.class);

        return product;
    }

    // 2、使用 spring-cloud-starter-loadbalancer 实现负载均衡
    private Product getProductFromRemoteWithLoadBalance(Long productId) {
        // TODO: 远程查询商品信息
        // 1、获取到商品服务所在的机器IP地址 + port端口号
        ServiceInstance choose = loadBalancerClient.choose("service-product");

        String url = "http://" + choose.getHost() + ":" + choose.getPort() + "/product/" + productId;
        log.info("远程请求: " + url);
        // 2、给远程商品服务发送请求，获取商品信息
        Product product = restTemplate.getForObject(url, Product.class);

        return product;
    }

    // 3、基于 @LoadBalanced注解 实现负载均衡
    private Product getProductFromRemoteWithLoadBalanceAnnotation(Long productId) {

        // @LoadBalanced 会将service-product服务名称负载均衡的选择一个端口，（可用实例的IP地址）默认轮询
        String url = "http://service-product/product/" + productId;
        // 2、给远程商品服务发送请求，获取商品信息
        Product product = restTemplate.getForObject(url, Product.class);

        return product;
    }
}
