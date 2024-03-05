package com.example.core.service;

import com.example.core.dto.VariantDTO;
import com.example.core.dto.request.CartItemRequest;
import com.example.core.dto.request.OrderRequest;
import com.example.core.entity.CartItem;
import com.example.core.entity.Order;
import com.example.core.entity.OrderDetail;
import com.example.core.entity.Variant;
import com.example.core.exceptions.IllegalArgumentException;
import com.example.core.repository.CartItemRepository;
import com.example.core.repository.OrderDetailRepository;
import com.example.core.repository.OrderRepository;
import com.example.core.repository.VariantRepository;
import com.example.core.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
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
    @Autowired
    VnPayService vnPayService;

    public String createOrderVnPay(OrderRequest orderRequest) throws UnsupportedEncodingException {
        Long userId = UserUtil.getUserId();
        List<CartItem> cartItems = cartItemRepository.getCartItemsByUserId(userId);
        Order order = Order.builder()
                .addressId(orderRequest.getAddressId())
                .estimateDate(orderRequest.getEstimateDate())
                .payMethod("VnPay")
                .shippingFee(orderRequest.getShippingFee())
                .shippingMethod(orderRequest.getShippingMethod())
                .createUser(userId)
                .createDatetime(new Date())
                .build();
        Order orderSave = orderRepository.save(order);
        List<Variant> variants = variantRepository.getVariantsByIdIsIn(cartItems.stream().map(item -> item.getVariantId()).collect(Collectors.toList()));
        Map<Long, Variant> variantMap = new HashMap<>();
        variants.forEach(variant -> {
            variantMap.put(variant.getId(), variant);
        });
        List<OrderDetail> orderDetails = cartItems.stream().map((cartItem -> {
            // if quantity of product less than variant available
            if (cartItem.getQuantity() > variantMap.get(cartItem.getVariantId()).getQuantity()) {
                throw new IllegalArgumentException(HttpStatus.BAD_REQUEST, "Please check your order as the selected quantity exceeds available stock");
            }
            OrderDetail orderDetail = OrderDetail.builder()
                    .createDatetime(new Date())
                    .quantity(cartItem.getQuantity())
                    .variantId(cartItem.getVariantId())
                    .orderId(orderSave.getId())
                    .orderPrice(variantMap.get(cartItem.getVariantId()).getPrice())
                    .createUser(userId)
                    .build();
            return orderDetail;
        })).collect(Collectors.toList());
        Long totalPrice = orderDetails.stream().map(orderDetail ->
            orderDetail.getOrderPrice() * orderDetail.getQuantity()
       ).reduce(0L, Long::sum
        );
        return vnPayService.createPayment(totalPrice, orderSave.getId());
    }

}
