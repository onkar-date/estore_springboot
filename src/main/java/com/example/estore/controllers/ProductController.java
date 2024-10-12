package com.example.estore.controllers;

import com.example.estore.dto.ProductDTO;
import com.example.estore.dto.ProductRequestDTO;
import com.example.estore.entity.Product;
import com.example.estore.entity.User;
import com.example.estore.services.ProductService;
import com.example.estore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping()
    public ResponseEntity<String> addProduct(@ModelAttribute ProductRequestDTO productDTO,
                                             @RequestParam("image") MultipartFile imageFile) throws IOException {

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());

        // Convert price from rupees to paise (multiply by 100)
        int priceInPaise = (int) (productDTO.getPrice() * 100);
        product.setPrice(priceInPaise);

        product.setStockQuantity(productDTO.getStockQuantity());

        User seller = userService.getUserById(productDTO.getSellerId()); // Get seller by ID
        product.setSeller(seller);

        // Convert image to byte array
        byte[] imageData = imageFile.getBytes();
        product.setImage(imageData);

        productService.saveProduct(product);

        return ResponseEntity.ok("Product added successfully");
    }

}
