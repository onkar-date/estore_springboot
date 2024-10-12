package com.example.estore.services;

import com.example.estore.dto.ProductDTO;
import com.example.estore.entity.Product;
import com.example.estore.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();

        // Convert Product entities to ProductDTOs
        return products.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice() / 100.0); // Convert from paise to rupees
        productDTO.setStockQuantity(product.getStockQuantity());
        productDTO.setSellerId(product.getSeller().getId());

        // Convert BLOB image to Base64 string
        if (product.getImage() != null) {
            String base64Image = Base64.getEncoder().encodeToString(product.getImage());
            productDTO.setImage(base64Image);
        }

        return productDTO;
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
}
