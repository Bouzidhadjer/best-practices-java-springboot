package com.hadjerbouzid.springbootbestpractices.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ProductRequestDTO {

    @NotBlank(message = "product name shouldn't be NULL OR EMPTY")
    private  String name;
    private String description;
    @NotBlank(message = "product type shouldn't be NULL OR EMPTY")
    private String productType;
    @Min(value= 1,message = "quantity is not defined !")
    private int quantity;
    @Min(value= 200,message = "product price can't be less than 200")
    @Max(value= 500000,message = "product price can't be more than 500000")
    private  double price;
    private String supplierName;
    @NotBlank(message = "supplier code shouldn't be NULL OR EMPTY")
    private String supplierCode;
}
