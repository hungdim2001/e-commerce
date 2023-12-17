package com.example.core.repository;

import com.example.core.entity.Area;
import com.example.core.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long>{
    List<ProductImage> findByProductId(Long id);
    void deleteByImage(String image);

}
