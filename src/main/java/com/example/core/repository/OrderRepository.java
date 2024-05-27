package com.example.core.repository;

import com.example.core.entity.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "update  orders set status_order ='PAID' where user_id = ?1", nativeQuery = true)
    void changeStatusOrder(Long userId);


    @Query(value = " select o.*  from orders o, order_details od where o.id =od.order_id and o.user_id =?1 and od.variant_id in(select v.id from variants v where v.product_id = ?2)", nativeQuery = true)
    List<Order> getOrderByUserIdAndVariantId(Long userId, Long productId);

    @Query(value = "select p.id, count(*) from orders o, order_details od, variants v, products p where o.id = od.order_id and od.variant_id = v.id and v.product_id = p.id and p.id in ?1 group by (p.id)", nativeQuery = true)
    List<Object[]> getOrderByProductId(List<Long> productId);
}
