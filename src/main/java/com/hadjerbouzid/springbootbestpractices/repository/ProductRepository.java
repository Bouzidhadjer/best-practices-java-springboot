package com.hadjerbouzid.springbootbestpractices.repository;

import com.hadjerbouzid.springbootbestpractices.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
    // Product findBySupplierCode(String supplierCode);
}
