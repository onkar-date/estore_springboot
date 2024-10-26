package com.example.estore.repositories;

import com.example.estore.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByProductId(Long productId);

    Image findByProductIdAndIsPrimaryTrue(Long productId);
}
