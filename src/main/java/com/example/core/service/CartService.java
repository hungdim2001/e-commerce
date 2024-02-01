package com.example.core.service;

import com.example.core.dto.CartItemDTO;
import com.example.core.entity.Cart;
import com.example.core.entity.CartItem;
import com.example.core.entity.Variant;
import com.example.core.repository.CartItemRepository;
import com.example.core.repository.CartRepository;
import com.example.core.repository.VariantRepository;
import com.example.core.util.UserUtil;
import com.example.core.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    VariantRepository variantRepository;

    public List<CartItem> addToCart(List<CartItem> CartItems) {
        Cart cart = Cart.builder().userId(UserUtil.getUserId()).createUser(UserUtil.getUserId()).createDatetime(new Date()).status(true).status(true).build();
        Cart cartSave = cartRepository.save(cart);
        CartItems.stream().forEach(item -> {
            item.setCartId(cartSave.getId());
        });
        return cartItemRepository.saveAll(CartItems);
    }

    public List<CartItemDTO> getCartItem() {
        Cart cart = cartRepository.getCartByUserId(UserUtil.getUserId());
        if (Utils.isNull(cart)) {
            return null;
        }
        List<CartItem> cartItems = cartItemRepository.getCartItemsByCartId(cart.getId());
        List<Long> variantId = cartItems.stream().map(CartItem::getVariantId).collect(Collectors.toList());
        List<Variant> variants = variantRepository.getVariantsByIdIs(variantId);

        return null;
    }
}
