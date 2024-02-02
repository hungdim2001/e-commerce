package com.example.core.service;

import com.example.core.dto.CartItemDTO;
import com.example.core.dto.request.CartItemRequest;
import com.example.core.entity.CartItem;
import com.example.core.entity.Product;
import com.example.core.entity.Variant;
import com.example.core.repository.CartItemRepository;
import com.example.core.repository.ProductRepository;
import com.example.core.repository.VariantRepository;
import com.example.core.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    VariantRepository variantRepository;
    @Autowired
    ProductRepository productRepository;

    public List<CartItemDTO> addToCart(CartItem cartItem) {
        List<CartItem> cartItems = cartItemRepository.getCartItemsByUserId(UserUtil.getUserId());
        cartItem.setUserId(UserUtil.getUserId());
        List<CartItem> newCartItems = cartItems.stream().map(item -> {
            if (item.getVariantId().equals(cartItem.getVariantId())) {
                item.setQuantity(item.getQuantity() + 1);
                return item;
            }
            return item;
        }).collect(Collectors.toList());
        cartItemRepository.save(cartItem);
        return getCartItem();
    }

    public List<CartItemDTO> getCartItem() {
//        Cart cart = cartRepository.getCartByUserId(UserUtil.getUserId());
//        if (Utils.isNull(cart)) {
//            return null;
//        }
        List<CartItem> cartItems = cartItemRepository.getCartItemsByUserId(UserUtil.getUserId());
        List<Long> variantId = cartItems.stream().map(CartItem::getVariantId).collect(Collectors.toList());
        List<Variant> variants = variantRepository.getVariantsByIdIsIn(variantId);
        Map<Long, Variant> variantMap = new HashMap<>();
        variants.forEach(variant -> {
            variantMap.put(variant.getId(), variant);
        });
        List<Product> products = productRepository.findProductsByIdIsIn(variants.stream().map(Variant::getProductId).collect(Collectors.toList()));
        Map<Long, Product> productMap = new HashMap<>();
        products.forEach(product -> {
            productMap.put(product.getId(), product);
        });

        return null;
    }
}
