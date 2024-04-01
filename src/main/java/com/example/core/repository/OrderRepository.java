package com.example.core.repository;

import com.example.core.entity.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository  extends JpaRepository<Order,Long> {
    @Query(value ="update  orders set status_order ='PAID' where user_id = ?1", nativeQuery = true)
    void changeStatusOrder(Long userId);
}
