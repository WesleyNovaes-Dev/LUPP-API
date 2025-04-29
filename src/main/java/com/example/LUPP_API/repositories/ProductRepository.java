package com.example.LUPP_API.repositories;

import com.example.LUPP_API.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
