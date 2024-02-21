package com.example.core.service;

import com.example.core.dto.request.CartItemRequest;
import com.example.core.dto.request.OrderRequest;
import com.example.core.entity.CartItem;
import com.example.core.entity.Order;
import com.example.core.entity.OrderDetail;
import com.example.core.entity.Variant;
import com.example.core.repository.CartItemRepository;
import com.example.core.repository.OrderDetailRepository;
import com.example.core.repository.OrderRepository;
import com.example.core.repository.VariantRepository;
import com.example.core.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    VariantRepository variantRepository;

    public void createOrderVnPay(OrderRequest orderRequest) {
        Long userId = UserUtil.getUserId();
        List<CartItem> cartItems = cartItemRepository.getCartItemsByUserId(userId);
        Order order = Order.builder().addressId(orderRequest.getAddressId())
                .estimateDate(orderRequest.getEstimateDate()).shippingFee(orderRequest.getShippingFee())
                .shippingMethod(orderRequest.getShippingMethod())
                .payMethod("VNPAY")
                .build();

       List<Variant> variants = variantRepository.getVariantsByIdIsIn(cartItems.stream().map(item-> item.getVariantId()).collect(Collectors.toList()));
       Map<Long,Variant> variantMap = new HashMap<>();
       variants.forEach((variant -> {
           variantMap.put(variant.getId(), variant);
       }));
        List<OrderDetail> orderDetails =
                cartItems.stream().map(item -> {
                    OrderDetail orderDetail = OrderDetail.builder()
                            .orderPrice(variantMap.get(variantMap.get(item.getVariantId())).getPrice())
                            .build();
                    return orderDetail;
                }).collect(Collectors.toList());
    }

}
