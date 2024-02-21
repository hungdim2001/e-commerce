package com.example.core.service;

import com.example.core.dto.request.CartItemRequest;
import com.example.core.dto.request.OrderRequest;
import com.example.core.entity.CartItem;
import com.example.core.entity.Order;
import com.example.core.repository.CartItemRepository;
import com.example.core.repository.OrderDetailRepository;
import com.example.core.repository.OrderRepository;
import com.example.core.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    public void createOrderVnPay(OrderRequest orderRequest) {
        Long userId = UserUtil.getUserId();
        List<CartItem> cartItems = cartItemRepository.getCartItemsByUserId(userId);
//        Order order = Order.builder().

    }

}
