package com.example.core.service;

import com.example.core.dto.VariantDTO;
import com.example.core.dto.request.CartItemRequest;
import com.example.core.dto.request.OrderRequest;
import com.example.core.entity.*;
import com.example.core.exceptions.IllegalArgumentException;
import com.example.core.exceptions.InvalidRefreshToken;
import com.example.core.exceptions.NotFoundException;
import com.example.core.repository.*;
import com.example.core.security.jwt.JwtUtils;
import com.example.core.util.BaseUtils;
import com.example.core.util.UserUtil;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
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
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CodeRepository codeRepository;

    public String createOrderVnPay(OrderRequest orderRequest, HttpServletRequest httpRequest) throws UnsupportedEncodingException {
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
        String token = jwtUtils.generateJwtToken(UserUtil.getUserId(), true).getToken();
        Code newCode = Code.builder().code(token).expiredTime(new Date().getTime() + 300000).userId(UserUtil.getUserId()).build();
        codeRepository.save(newCode);
        return vnPayService.createPayment(totalPrice, orderSave.getId(), httpRequest, token);
    }

    public String checkOrder(HttpServletRequest request) throws IOException {
        String jwt = BaseUtils.parseJwt(request);
        Code token = codeRepository.findByCode(jwt).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND, "Not user with id"));
        if (new Date(token.getExpiredTime() * 1000).after(new Date())) {
            throw new InvalidRefreshToken(HttpStatus.BAD_REQUEST, "code is expired");
        }
        Long id = Long.valueOf(jwtUtils.getIdFromJwtToken(jwt, true));
        userRepository.findById(id).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND, "Not user with id"));
        // chuyển trạng thái của order
        orderRepository.changeStatusOrder(UserUtil.getUserId());
        // xoá cart item
        cartItemRepository.deleteByUserId(id);
        //xoá token
        codeRepository.removeCode(UserUtil.getUserId());


        return "";
    }

}
