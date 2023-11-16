package com.hadjerbouzid.springbootbestpractices.service.impl;

import com.hadjerbouzid.springbootbestpractices.dto.ProductRequestDTO;
import com.hadjerbouzid.springbootbestpractices.dto.ProductResponseDTO;
import com.hadjerbouzid.springbootbestpractices.entity.Product;
import com.hadjerbouzid.springbootbestpractices.exception.ProductNotFoundException;
import com.hadjerbouzid.springbootbestpractices.exception.ProductServiceBusinessException;
import com.hadjerbouzid.springbootbestpractices.repository.ProductRepository;
import com.hadjerbouzid.springbootbestpractices.service.ProductService;
import com.hadjerbouzid.springbootbestpractices.util.ValueMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl  implements ProductService {


    private ProductRepository productRepository;


    @Override
    public ProductResponseDTO createNewProduct(ProductRequestDTO productRequestDTO) throws ProductServiceBusinessException {
        ProductResponseDTO productResponseDTO;
        try {
            log.info("ProductService: createNewProduct execution started.");
            Product product = ValueMapper.convertToEntity(productRequestDTO);
            log.debug("ProductService:createNewProduct request parameters {}", ValueMapper.jsonAsString(productRequestDTO));
            Product productResult = productRepository.save(product);
            productResponseDTO = ValueMapper.convertToDTO(productResult);
            log.debug("ProductService:createNewProduct received from database {}", ValueMapper.jsonAsString(productResponseDTO));

        } catch (ProductServiceBusinessException ex) {
            log.error("Exception occurred while persisting product to database, Exception message {}",ex.getMessage());
            throw new ProductServiceBusinessException("Exception occurred while create a new product");
        }
        log.info("ProductService:createNewProduct execution ended");
        return  productResponseDTO;
    }

    @Override
    @Cacheable(value = "product")
    public List<ProductResponseDTO> getProducts() throws  ProductServiceBusinessException{
        List<ProductResponseDTO> productResponseDTOS;
        try {
            log.info("ProductService:getProducts execution started");
            List<Product> productList = productRepository.findAll();
            if(!productList.isEmpty()){
                productResponseDTOS = productList.stream().map(ValueMapper::convertToDTO)
                        .collect(Collectors.toList());
            }else  {
                productResponseDTOS = Collections.emptyList();
            }
            log.debug("ProductService:getProducts retrieving products from database {}", ValueMapper.jsonAsString(productResponseDTOS));
        } catch (ProductServiceBusinessException ex){
           log.error("Exception occurred while retrieving products from  database, Exception message {}", ex.getMessage());
           throw  new ProductServiceBusinessException("Exception occurred while fetch all products from database");
        }
        log.info("ProductService:getProducts execution ended");
        return productResponseDTOS;
    }

    /**
     *  this method will fetch product from database by ID
     *
     * @param  productId
     * @return  product response from database
     */
    @Override
    @Cacheable(value = "product")
    public ProductResponseDTO getProductById(Long productId) throws  ProductServiceBusinessException{
        ProductResponseDTO productResponseDTO;
        try {
            log.info("ProductService:getProductById execution started.");
            Product product = productRepository.findById(productId).orElseThrow(() ->
                    new ProductNotFoundException("Product not found with id"+ productId));
            productResponseDTO = ValueMapper.convertToDTO(product);
        } catch (Exception ex) {
            log.error("Exception occurred while retrieving product {} from database, Exception message {}", productId, ex.getMessage());
            throw new ProductServiceBusinessException("Exception occurred while fetch product from database"+productId);
        }
        log.info("ProductService:getProductById execution ended");
        return productResponseDTO;
    }

    @Override
    @Cacheable(value = "product")
    public Map<String, List<ProductResponseDTO>> getProductsByTypes() {
        try {
              log.info("ProductService:getProductsByTypes execution started.");
              Map<String, List<ProductResponseDTO>> productMap =
                      productRepository.findAll().stream().map(ValueMapper::convertToDTO)
                              .filter(productResponseDTO -> productResponseDTO.getProductType() != null)
                              .collect(Collectors.groupingBy(ProductResponseDTO::getProductType));
              log.info("ProductService:getProductsByTypes execution ended");
              return productMap;
        } catch (Exception ex){
            log.error("Exception occurred while retrieving product grouping by type from database , Exception message {}", ex.getMessage());
            throw  new ProductServiceBusinessException("Exception occurred while fetch product from database");
        }
    }

    @Override
    public Map<String, List<ProductResponseDTO>> getProductsByTypesBeforeJava8() {
        Map<String, List<ProductResponseDTO>> productsMap = new HashMap<>();
        List<String> productTypes = Arrays.asList("Electronics", "fashion","Kitchen");

        List<Product> productList = productRepository.findAll();
        for(String type: productTypes) {
            List<ProductResponseDTO>  productResponseDTOList = new ArrayList<>();
            for (Product product: productList) {
                if(type.equals(product.getProductType())) {
                    productResponseDTOList.add(ValueMapper.convertToDTO(product));
                }
                productsMap.put(type,productResponseDTOList);
            }
        }
        return productsMap;
    }
}
