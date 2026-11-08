package com.swiftcart.swiftcartproductservice.repositories;

import com.swiftcart.swiftcartproductservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Query("""
    UPDATE Product p
    SET p.stockQuantity = p.stockQuantity - :quantity,
        p.updatedAt = :updatedAt
    WHERE p.id = :id
    AND p.stockQuantity >= :quantity
""")
    int reduceStock(
            @Param("id") Long id,
            @Param("quantity") Integer quantity,
            @Param("updatedAt") LocalDateTime updatedAt
    );


    @Modifying
    @Query("""
    UPDATE Product p
    SET p.stockQuantity = p.stockQuantity + :quantity,
        p.updatedAt = :updatedAt
    WHERE p.id = :id
""")
    int restoreStock(
            @Param("id") Long id,
            @Param("quantity") Integer quantity,
            @Param("updatedAt") LocalDateTime updatedAt
    );
}
