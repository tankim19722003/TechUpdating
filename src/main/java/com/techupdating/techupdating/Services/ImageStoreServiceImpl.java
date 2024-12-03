package com.techupdating.techupdating.Services;

import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

public class ImageStoreServiceImpl{

    public static void deleteImage(String imageFolder, String imageName) {
        String filePath = imageFolder +"/" + imageName;

        // Create a File object
        File file = new File(filePath);

        // Check if the file exists and delete it
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("File deleted successfully.");
            } else {
                System.out.println("Failed to delete the file.");
            }
        } else {
            System.out.println("File not found.");
        }
    }

    public static String createImage(String imageFolder, MultipartFile file) throws IOException {

        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename(),"Tên file không được bỏ trống"));

        //thêm UUID vào trước tên file để đẩm bảo tên file là duy nhất
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;

        //
        Path uploadDir = Paths.get(imageFolder);

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        //import java.nio.file.Path;
        Path destination = Paths.get(uploadDir.toString(), uniqueFilename);

        // sao chép file vào thư mục đích
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFilename;
    }

    public static UrlResource getImageResource(
            String imageFolder,
            String imageName
    ) throws MalformedURLException {

            Path imagePath = Paths.get(imageFolder + "/" +imageName);

            // Check if the image file exists
            if (Files.exists(imagePath)) {
                // Create a UrlResource from the image path
                UrlResource resource = new UrlResource(imagePath.toUri());

                // Return the image as the response entity
                return resource;
            } else {
                throw new RuntimeException("Image does not exist!!");
            }

    }

}
