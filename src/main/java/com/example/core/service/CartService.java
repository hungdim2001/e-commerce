package com.example.core.service;

import com.example.core.exceptions.IllegalArgumentException;
import com.example.core.dto.CartItemDTO;
import com.example.core.entity.CartItem;
import com.example.core.entity.Product;
import com.example.core.entity.Variant;
import com.example.core.exceptions.NotFoundException;
import com.example.core.repository.CartItemRepository;
import com.example.core.repository.ProductRepository;
import com.example.core.repository.VariantRepository;
import com.example.core.util.UserUtil;
import com.example.core.util.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @Autowired
    private ModelMapper modelMapper;

    public List<CartItemDTO> addToCart(CartItem cartItem) {
        List<CartItem> cartItems = cartItemRepository.getCartItemsByUserId(UserUtil.getUserId());
        // check exist cartItem
        Variant variant = variantRepository.findById(cartItem.getVariantId()).orElseThrow(
                () -> new NotFoundException(HttpStatus.NOT_FOUND,"User Not Found with id: " + cartItem.getVariantId())
        );
        if (cartItem.getQuantity() > variant.getQuantity()) {
            throw new IllegalArgumentException(HttpStatus.BAD_REQUEST, "quantity is not enough");
        }
        cartItem.setUserId(UserUtil.getUserId());
        List<CartItem> newCartItems = new ArrayList<>();
        Integer count = cartItemRepository.existsByUserIdAndVariantIdAndQuantityGreaterThanZero(UserUtil.getUserId(), cartItem.getVariantId());
        if (!(count != null && count > 0)) {
            cartItem.setCreateDatetime(new Date());
            cartItem.setCreateUser(UserUtil.getUserId());
            cartItemRepository.save(cartItem);
            return getCartItem();
        }
        newCartItems = cartItems.stream().map(item -> {
            if (item.getVariantId().equals(cartItem.getVariantId())) {
                item.setQuantity(item.getQuantity() + cartItem.getQuantity());
                item.setUpdateDatetime(new Date());
                item.setUpdateUser(UserUtil.getUserId());
                return item;
            }
            return item;
        }).collect(Collectors.toList());
        if (!Utils.isNullOrEmpty(newCartItems)) {
            cartItemRepository.saveAll(newCartItems);
            return getCartItem();
        }
        return getCartItem();
    }

    public List<CartItemDTO> getCartItem() {
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
        List<CartItemDTO> cartItemDTOS = cartItems.stream().map(cartItem -> {
            CartItemDTO cartItemDTO = modelMapper.map(cartItem, CartItemDTO.class);
            cartItemDTO.setSubtotal(variantMap.get(cartItemDTO.getVariantId()).getPrice() * cartItem.getQuantity());
            cartItemDTO.setName(productMap.get(variantMap.get(cartItemDTO.getVariantId()).getProductId()).getName());
            return cartItemDTO;
        }).collect(Collectors.toList());
        return cartItemDTOS;
    }
}
