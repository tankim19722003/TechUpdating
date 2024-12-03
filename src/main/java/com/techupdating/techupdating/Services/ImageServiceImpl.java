package com.techupdating.techupdating.Services;

import com.techupdating.techupdating.models.Image;
import com.techupdating.techupdating.models.Part;
import com.techupdating.techupdating.models.User;
import com.techupdating.techupdating.repositories.ImageRepository;
import com.techupdating.techupdating.repositories.PartRepository;
import com.techupdating.techupdating.repositories.UserRepository;
import com.techupdating.techupdating.responses.ImageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
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

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements  ImageService{
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final PartRepository partRepository;

    @Value("${avatar.path}")
    private String avatarPath;

    @Override
    public String storeImage(MultipartFile file, String folderName) throws IOException {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename(),"Tên file không được bỏ trống"));

        //thêm UUID vào trước tên file để đẩm bảo tên file là duy nhất
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;

        // Tạo đường dẫn tới thư mục trong src/main/resources/static/avatar
        File uploadDir = new File(avatarPath);
        if (!Files.exists(uploadDir.toPath())) {
            Files.createDirectories(uploadDir.toPath());
        }

        //import java.nio.file.Path;
        Path destination = Paths.get(uploadDir.toString(), uniqueFilename);

        // sao chép file vào thư mục đích
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }

    @Override
    @Transactional
    public String updateUserAvatar(MultipartFile avatarFile, int userId) throws IOException {

        // check user existing
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User does not found!!")
        );


        // remove old avatar file in server
//        deleteFile(avatarPath + user.getAvatar());
        ImageStoreServiceImpl.deleteImage(avatarPath, user.getAvatar());

        // store file to sever
        String avatarFileName = storeImage(avatarFile, ImageFolder.avatars.toString());

        // store file to db
        user.setAvatar(avatarFileName);

        userRepository.save(user);

        return avatarFileName;
    }

    @Override
    @Transactional
    public void removeImage(String folderName, int imageId) {

        // is image existing on db
        Image image = imageRepository.findById(imageId).orElseThrow(
                () -> new RuntimeException("Image does not exist!!")
        );

        // remove image on db
        image.setPart(null);
        imageRepository.delete(image);


        // remove image out of store
        ImageStoreServiceImpl.deleteImage(folderName, image.getUrlImage());

    }

    @Override
    @Transactional
    public ImageResponse createImage(String folder, int partId, MultipartFile file) throws IOException {

        // check part existing
        Part part = partRepository.findById(partId).orElseThrow(
                () ->new RuntimeException("Part does not exist!!")
        );

        // store image to server
        String imageName = ImageStoreServiceImpl.createImage(folder, file);

        // save image to db
        Image image = Image.builder()
                .part(part)
                .urlImage(imageName)
                .build();

        return imageRepository.save(image).toImageResponse();
    }

    @Override
    public UrlResource getImageResourceById(String imageFolder, int imageId) throws MalformedURLException {

        Image image = imageRepository.findById(imageId).orElseThrow(
                () -> new RuntimeException("Image does not exist!!")
        );


        return getImageResourceByName(imageFolder, image.getUrlImage());
    }

    @Override
    public UrlResource getImageResourceByName(String imageFolder, String imageName) throws MalformedURLException {
        return ImageStoreServiceImpl.getImageResource(imageFolder, imageName);
    }


    // remove image in db
    @Transactional
    public void removeImageFormDb(
            int id
    ) {
        imageRepository.deleteById(id);
    }


}

