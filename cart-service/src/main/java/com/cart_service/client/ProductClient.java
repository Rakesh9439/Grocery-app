package com.cart_service.client;

import com.cart_service.dto.APIResponse;
import com.cart_service.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {

    @GetMapping("/api/v1/products/{id}")
    public APIResponse<ProductDto> getPropertyById(@RequestParam long id);



}
