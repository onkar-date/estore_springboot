package com.example.estore.controllers;

import com.example.estore.dto.ProductDTO;
import com.example.estore.dto.request.ProductRequestDTO;
import com.example.estore.entity.Product;
import com.example.estore.entity.User;
import com.example.estore.services.ProductService;
import com.example.estore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
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
