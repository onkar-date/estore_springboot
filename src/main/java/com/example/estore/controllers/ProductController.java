package com.example.estore.controllers;

import com.example.estore.dto.ProductDTO;
import com.example.estore.dto.request.ProductRequestDTO;
import com.example.estore.dto.response.PaginatedResponse;
import com.example.estore.services.ProductService;
import com.example.estore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<ProductDTO>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String searchKey
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDTO> productPage = productService.getAllProducts(pageable, searchKey);
        PaginatedResponse<ProductDTO> paginatedResponse = new PaginatedResponse<>(
                productPage.getContent(),
                productPage.getNumber(),
                productPage.getTotalPages(),
                productPage.getTotalElements()
        );
        return ResponseEntity.ok(paginatedResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO product = productService.getProductDTOById(id);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<ProductDTO> addProduct(@ModelAttribute ProductRequestDTO productDTO) throws IOException {

        ProductDTO addedProduct = productService.saveProduct(productDTO);

        return ResponseEntity.ok(addedProduct);
    }


}
