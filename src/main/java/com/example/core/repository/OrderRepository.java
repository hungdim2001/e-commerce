package com.example.core.repository;

import com.example.core.entity.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "update  orders set status_order ='PAID' where user_id = ?1", nativeQuery = true)
    void changeStatusOrder(Long userId);

    @Query(value = " select o.*  from orders o, order_details od where o.id =od.order_id and o.user_id =?1 and od.variant_id in(select v.id from variants v where v.product_id = ?2)", nativeQuery = true)
    Order getOrderByUserIdAndVariantId(Long userId, Long productId);
}
