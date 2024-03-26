package com.example.core.repository;


import com.example.core.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query(value ="select * from cart_items where user_id =?1 and quantity>0", nativeQuery = true)
    List<CartItem> getCartItemsByUserId(Long cartId);


    void deleteByUserId(Long cartId);
    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM cart_items WHERE user_id = ?1 AND variant_id = ?2 AND quantity > 0", nativeQuery = true)
    Integer existsByUserIdAndVariantIdAndQuantityGreaterThanZero(Long userId, Long variantId);

}
