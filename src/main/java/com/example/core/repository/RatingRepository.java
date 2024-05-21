package com.example.core.repository;

import com.example.core.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Rating getRatingByUserIdAndProductId(Long userId, Long productId);

    @Query(value = "select * from ratings where product_id in ?1", nativeQuery = true)
    List<Rating> getRatingsByProductIdIs(List<Long> productIds);
}
