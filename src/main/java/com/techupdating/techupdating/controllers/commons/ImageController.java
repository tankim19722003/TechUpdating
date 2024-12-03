package com.techupdating.techupdating.controllers.commons;

import com.techupdating.techupdating.Services.ImageService;
import com.techupdating.techupdating.responses.ErrorResponse;
import com.techupdating.techupdating.responses.ImageResponse;
import com.techupdating.techupdating.responses.Reponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("${api.prefix}/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @Value("${post.image.folder}")
    private String postImagesFolder;

    @Value("${uploads.folder}")
    private String uploadsFolder;

    @Value("${thumbnails.folder}")
    private String thumbnailsFolder;

    @Value("${avatar.path}")
    private String avatarPath;


    @GetMapping("/{imageName}")
    public ResponseEntity<?> viewImage(
            @PathVariable String imageName
    ) {
        try {
            Path imagePath = Paths.get("images/"+imageName);

            // Check if the image file exists
            if (Files.exists(imagePath)) {
                // Create a UrlResource from the image path
                UrlResource resource = new UrlResource(imagePath.toUri());

                // Return the image as the response entity
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                // Return a default image if the requested one is not found
                Path notFoundPath = Paths.get("target/avatars/notfound.jpg");
                UrlResource notFoundResource = new UrlResource(notFoundPath.toUri());
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(notFoundResource);
            }
        } catch (Exception e) {
            // Return 404 if any exception occurs
            return ResponseEntity.notFound().build();
        }
    }

//    @GetMapping("/uploads/{imageName}")
//    public ResponseEntity<?> viewImageUpload(
//            @PathVariable String imageName
//    ) {
//        try {
//            Path imagePath = Paths.get("uploads/"+imageName);
//
//            // Check if the image file exists
//            if (Files.exists(imagePath)) {
//                // Create a UrlResource from the image path
//                UrlResource resource = new UrlResource(imagePath.toUri());
//
//                // Return the image as the response entity
//                return ResponseEntity.ok()
//                        .contentType(MediaType.IMAGE_JPEG)
//                        .body(resource);
//            } else {
//                // Return a default image if the requested one is not found
//                Path notFoundPath = Paths.get("target/avatars/notfound.jpg");
//                UrlResource notFoundResource = new UrlResource(notFoundPath.toUri());
//                return ResponseEntity.ok()
//                        .contentType(MediaType.IMAGE_JPEG)
//                        .body(notFoundResource);
//            }
//        } catch (Exception e) {
//            // Return 404 if any exception occurs
//            return ResponseEntity.notFound().build();
//        }
//    }

//    @GetMapping("/images/avatar/{imageName}")
//    public ResponseEntity<?> viewAvatarImage(
//            @PathVariable String imageName
//    ) {
//        try {
//            File avatarFile = new File(avatarPath);
//            Path imagePath = avatarFile.toPath();
//
//            // Check if the image file exists
//            if (Files.exists(imagePath)) {
//                // Create a UrlResource from the image path
//                UrlResource resource = new UrlResource(imagePath.toUri());
//
//                // Return the image as the response entity
//                return ResponseEntity.ok()
//                        .contentType(MediaType.IMAGE_JPEG)
//                        .body(resource);
//            } else {
//                // Return a default image if the requested one is not found
//                Path notFoundPath = Paths.get("target/avatars/notfound.jpg");
//                UrlResource notFoundResource = new UrlResource(notFoundPath.toUri());
//                return ResponseEntity.ok()
//                        .contentType(MediaType.IMAGE_JPEG)
//                        .body(notFoundResource);
//            }
//        } catch (Exception e) {
//            // Return 404 if any exception occurs
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping("/avatar/{imageName}")
    public ResponseEntity<?> viewAvatar(
            @PathVariable String imageName
    ) {
        try {
            File file = new File(avatarPath + "/" + imageName);
            URL fileUrl = file.toURI().toURL();
            UrlResource resource = new UrlResource(fileUrl);
            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(new UrlResource(Paths.get("path/to/notfound.jpg").toUri()));
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/update_avatar/{user_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAvatar(
            @PathVariable("user_id") int userId,
            @RequestParam("file") MultipartFile avatarFile
    ) {

        try {
            String avatarImageName = imageService.updateUserAvatar(avatarFile,userId);
            return ResponseEntity.ok().body(
                    Reponse.builder()
                            .name("avatarName")
                            .message(avatarImageName)
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ErrorResponse.builder()
                            .name("error")
                            .error(e.getMessage())
                            .build()
            );
        }
    }

    // api to removing image
    @DeleteMapping("/part_image/{image_id}")
    public ResponseEntity<?> removingImage(
        @PathVariable("image_id") int imageId
    ) {

        try {
            imageService.removeImage(postImagesFolder,imageId);
            return ResponseEntity.ok("");
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(
                    ErrorResponse
                            .builder()
                            .name(ErrorResponse.ERROR)
                            .error(e.getMessage())
                            .build()
            );
        }

    }


    @PostMapping(value="/part_image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createPartImage(
            @RequestParam("part_id") int partId,
            @RequestParam("file") MultipartFile image
    ) {

        try {
            ImageResponse imageResponse = imageService.createImage(postImagesFolder, partId, image);
            return ResponseEntity.ok().body(imageResponse);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/image_part/{imageName}")
    public ResponseEntity<?> viewImagePart(
            @PathVariable("imageName") String imageName
    ) {

       try {
           UrlResource resource = imageService.getImageResourceByName(postImagesFolder, imageName);
           return ResponseEntity.ok()
                   .contentType(MediaType.IMAGE_JPEG)
                   .body(resource);
       } catch (Exception e) {
           return ResponseEntity.badRequest().body(
                   ErrorResponse.builder()
                           .name(ErrorResponse.ERROR)
                           .error(e.getMessage())
                           .build()
           );
       }
    }




    @GetMapping("/uploads/{image_name}")
    public ResponseEntity<?> viewUploadImage(
            @PathVariable("image_name") String imageName
    ) {

        try {
            UrlResource resource = imageService.getImageResourceByName(uploadsFolder, imageName);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ErrorResponse.builder()
                            .name(ErrorResponse.ERROR)
                            .error(e.getMessage())
                            .build()
            );
        }
    }



    @GetMapping("/thumbnails/{image_name}")
    public ResponseEntity<?> viewThumbnail(
            @PathVariable("image_name") String imageName
    ) {

        try {
            UrlResource resource = imageService.getImageResourceByName(thumbnailsFolder, imageName);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ErrorResponse.builder()
                            .name(ErrorResponse.ERROR)
                            .error(e.getMessage())
                            .build()
            );
        }
    }

}
