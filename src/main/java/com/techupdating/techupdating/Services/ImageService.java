package com.techupdating.techupdating.Services;

import com.techupdating.techupdating.responses.ImageResponse;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

public interface ImageService {

    String storeImage(MultipartFile imageFile, String folderName) throws IOException;

    String updateUserAvatar(MultipartFile avatarFile, int userId) throws IOException;

    void removeImage(String folderName, int imageId);

    ImageResponse createImage(String folder, int partId, MultipartFile file) throws IOException;

    UrlResource getImageResourceById(String imageFolder, int imageId) throws MalformedURLException;

    UrlResource getImageResourceByName(String imageFolder, String imageName) throws MalformedURLException;
}
