package com.techupdating.techupdating.Services;

import com.techupdating.techupdating.dtos.PartDTO;
import com.techupdating.techupdating.dtos.PostDTO;
import com.techupdating.techupdating.models.*;
import com.techupdating.techupdating.repositories.*;
import com.techupdating.techupdating.responses.CourseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    Logger logger = Logger.getLogger(this.getClass().getName());
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final PartRepository partRepository;
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final LanguageRepository languageRepository;

    @Override
    @Transactional
    public Post createPost (
            PostDTO postDTO
    ) throws IOException {

        // saving post
        Post post = ConvertPostSaving(postDTO);
        post = postRepository.save(post);

        logger.info("Post Saving: " + post);

//        // saving part
        savingPart(post, postDTO);


        return postRepository.findById(post.getId()).orElseThrow(
                () ->  new RuntimeException("Post does not found")
        );
    }


    @Transactional
    private void savingPart(Post post, PostDTO postDTO) throws IOException {

        List <PartDTO> partList = postDTO.getParts();

        for (PartDTO partItem: partList) {

            // create part
            Part part = Part.builder()
                    .title(post.getTitle())
                    .content(partItem.getContent())
                    .post(post)
                    .build();

            part = partRepository.save(part);
            logger.info("Part: " + part);
            // create image
            // saving image to folder
            String filename = storeFile(partItem.getImage());


            // saving image to db
            Image image = Image.builder()
                    .urlImage(filename)
                    .part(part)
                    .build();

            // save image to db
            image = imageRepository.save(image);

            logger.info("Image Saving" + image);
        }

    }

    private String storeFile(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename(),"Tên file không được bỏ trống"));

        //thêm UUID vào trước tên file để đẩm bảo tên file là duy nhất
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;

        //
        Path uploadDir = Paths.get("uploads");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        //import java.nio.file.Path;
        Path destination = Paths.get(uploadDir.toString(), uniqueFilename);

        // sao chép file vào thư mục đích
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }

    private Post ConvertPostSaving(PostDTO postDTO) {
        // create post
        Post post = new Post();

        // get course
        Course course = courseRepository.findById(postDTO.getCourseId()).orElseThrow(
                () ->  new RuntimeException("Course does not found")
        );

        //get user
        User user = userRepository.findById(postDTO.getUserId()).orElseThrow(
                () -> new RuntimeException("User does not found")
        );

        post.setPostView(0);
        post.setCourse(course);
        post.setTitle(postDTO.getTitle());
        post.setShortDescription(postDTO.getShortDescription());
        post.setQuantityOfLike(0);
        post.setUser(user);

        return post;

    }



    @Override
    public List<Language> findAllLanguage() {
        return languageRepository.findAll();

    }


}

