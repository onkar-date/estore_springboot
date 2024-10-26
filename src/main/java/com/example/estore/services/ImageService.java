package com.example.estore.services;

import com.example.estore.dto.ImageDTO;
import com.example.estore.entity.Image;
import com.example.estore.entity.Product;
import com.example.estore.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public Image saveImage(Product product, MultipartFile imageFile, Boolean isPrimary) throws IOException {
        byte[] imageData = imageFile.getBytes();
        Image imageToSave = new Image(
                product,
                imageData,
                isPrimary
        );
        return imageRepository.save(imageToSave);
    }

    public List<Image> getImagesByProductId(Long productId) {
        return imageRepository.findByProductId(productId);
    }

    public ImageDTO mapImageToDTO(Image image) {
        ImageDTO imageDTO = new ImageDTO();

        String base64Image = Base64.getEncoder().encodeToString(image.getImage());

        imageDTO.setBase64Image(base64Image);
        imageDTO.setId(image.getId());
        imageDTO.setIsPrimary(image.getIsPrimary());

        return imageDTO;
    }
}
