package com.hadjerbouzid.springbootbestpractices.service;

import com.hadjerbouzid.springbootbestpractices.dto.ProductRequestDTO;
import com.hadjerbouzid.springbootbestpractices.dto.ProductResponseDTO;
import com.hadjerbouzid.springbootbestpractices.exception.ProductServiceBusinessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface ProductService {

     ProductResponseDTO createNewProduct(ProductRequestDTO productRequestDTO) throws ProductServiceBusinessException;
    
     List<ProductResponseDTO> getProducts() throws  ProductServiceBusinessException;

     ProductResponseDTO getProductById(Long productId) throws ProductServiceBusinessException;

     Map<String, List<ProductResponseDTO>> getProductsByTypes();

    Map<String, List<ProductResponseDTO>> getProductsByTypesBeforeJava8();
}
