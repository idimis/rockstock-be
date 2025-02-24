package com.rockstock.backend.service.cloudinary.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.rockstock.backend.service.cloudinary.DeleteCloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteCloudinaryServiceImpl implements DeleteCloudinaryService {

    private final Cloudinary cloudinary;

    @Override
    @Transactional
    public void deleteFromCloudinary(String imageUrl) {
        try {
            // Extract public ID from Cloudinary URL
            String publicId = imageUrl.substring(imageUrl.lastIndexOf("/") + 1, imageUrl.lastIndexOf("."));

            // Delete the file from Cloudinary
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());

            System.out.println("Deleted from Cloudinary: " + publicId);
        } catch (Exception e) {
            System.err.println("Error deleting file from Cloudinary: " + e.getMessage());
        }
    }
}
