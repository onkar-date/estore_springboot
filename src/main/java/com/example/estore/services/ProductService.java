package com.example.estore.services;

import com.example.estore.dto.ImageDTO;
import com.example.estore.dto.ProductDTO;
import com.example.estore.dto.request.ProductRequestDTO;
import com.example.estore.entity.Image;
import com.example.estore.entity.Product;
import com.example.estore.entity.User;
import com.example.estore.repositories.ProductRepository;
import com.example.estore.specifications.ProductSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    public Page <ProductDTO> getAllProducts(Pageable pageable, String searchKey) {
        Specification<Product> spec = Specification.where(null);
        if (searchKey != null && !searchKey.isEmpty()) {
            spec = spec.and(ProductSpecifications.nameContains(searchKey));
        }
        return productRepository.findAll(spec, pageable).map(this::convertToDTO);
    }

    public ProductDTO saveProduct(ProductRequestDTO productToAdd) throws IOException {
        Product product = new Product();
        product.setName(productToAdd.getName());
        product.setDescription(productToAdd.getDescription());

        int priceInPaise = productToAdd.getPrice() * 100;
        product.setPrice(priceInPaise);

        product.setStockQuantity(productToAdd.getStockQuantity());

        User seller = userService.getUserById(productToAdd.getSellerId()); // Get seller by ID
        product.setSeller(seller);

        Product addedProduct =  productRepository.save(product);

        imageService.saveImage(product, productToAdd.getImage(), true);
        return convertToDTO(addedProduct);
    }

    public ProductDTO getProductDTOById(Long id) {
        Product product = getProductById(id);
        if (product != null) {
            return convertToDTO(product);
        }
        return null;
    }

    public Product getProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.orElse(null);
    }

    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice()); // Convert from paise to rupees
        productDTO.setStockQuantity(product.getStockQuantity());
        productDTO.setSellerId(product.getSeller().getId());

        List<Image> images = imageService.getImagesByProductId(product.getId());
        for (Image image: images) {
            productDTO.getImages().add(imageService.mapImageToDTO(image));
        }

        return productDTO;
    }
}
