package org.okten.may2024.demo.repository;

import org.okten.may2024.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByPriceBetween(Double minPrice, Double maxPrice);

    @Query("select p from Product p where p.price > :minPrice and p.price < :maxPrice") // JPQL
    List<Product> findAllByPriceBetweenWithJpql(Double minPrice, Double maxPrice);

    @Query(value = "select p.* from products p where p.price > :minPrice and p.price < :maxPrice", nativeQuery = true)
    List<Product> findAllByPriceBetweenWithSql(Double minPrice, Double maxPrice);

    List<Product> findAllByPriceGreaterThan(Double value);

    List<Product> findAllByPriceLessThan(Double value);
}
