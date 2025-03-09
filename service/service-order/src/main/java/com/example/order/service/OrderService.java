package com.example.order.service;

import com.example.order.bean.Order;

public interface OrderService {

    public Order createOrder(Long userId, Long productId);
}
