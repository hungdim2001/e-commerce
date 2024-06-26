package com.example.core.repository;

import com.example.core.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>,ProductRepositoryCustom {
    @Query(value = "SELECT p.*\n" +
            "FROM products p\n" +
            "WHERE\n" +
            "(?3 is null or p.product_type_id = ?3)" +
            " and (?4 is null or p.name like %?4%)" +
            "  and   (?1 IS NULL OR p.id = ?1) -- Lọc theo id nếu được chỉ định, hoặc bỏ qua nếu không có id được chỉ định\n" +
            "    AND (\n" +
            "        ?2 = false  -- Nếu không có biến is_newest được truyền vào\n" +
            "        OR (?2 = true  AND p.create_datetime >= (\n" +
            "            SELECT MIN(create_datetime)\n" +
            "            FROM (\n" +
            "                SELECT create_datetime\n" +
            "                FROM products\n" +
            "                ORDER BY create_datetime DESC\n" +
            "                LIMIT 4\n" +
            "            ) AS latest_products\n" +
            "        ))\n" +
            "    )\n" +
            "ORDER BY p.create_datetime DESC; -- Sắp xếp sản phẩm theo thời gian tạo giảm dần\n", nativeQuery = true)
    List<Product> findByIdCus(Long id, Boolean  newest, Long productTypeId, String keyword);
    List<Product> findProductsByIdIsIn(List<Long> ids);
}
