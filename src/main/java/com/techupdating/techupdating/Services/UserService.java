package com.techupdating.techupdating.Services;

import com.techupdating.techupdating.dtos.AdminLoginDTO;
import com.techupdating.techupdating.dtos.UserLoginDTO;
import com.techupdating.techupdating.dtos.UserRegisterDTO;
import com.techupdating.techupdating.models.User;
import com.techupdating.techupdating.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User register(UserRegisterDTO userRegisterDTO) {

        // check existing user
        boolean isUserExisting = userRepository
                .existsByAccount(userRegisterDTO.getAccount());

        if (isUserExisting) throw new RuntimeException("User account is existing");

        // check password match
        // NEED TO CHANGE IF PASSWORD IS ENCODED
        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getRetypePassword())) {
            throw new RuntimeException("Password does not match");
        }

        // initialize new user
        // PASSWORD NEED TO ENCODE BEFORE SAVING

        User user = User.builder()
                .email(userRegisterDTO.getEmail())
                .account(userRegisterDTO.getAccount())
                .password(passwordEncoder.encode(userRegisterDTO.getPassword()))
                .enabled(true)
                .fullname(userRegisterDTO.getFullname())
                .build();

        // save user to db and return user
        return  userRepository.save(user);

    }

    public User login(
        UserLoginDTO userLoginDTO
    ) {

        User user = userRepository.findByAccount(userLoginDTO.getAccount()).orElseThrow(
                () ->  new RuntimeException("Invalid User or Password")
        );

        // check password match with password user enter
        if (!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid User or Password");
        }


        return user;
    }

    public User loginAdmin(
            AdminLoginDTO adminLoginDTO
    ) {

        User user = userRepository.findByAccount(adminLoginDTO.getAccount()).orElseThrow(
                () ->  new RuntimeException("Invalid User or Password")
        );

//        // check user role
//        String roleName = user.getRoles().getName();
//        if (!roleName.equalsIgnoreCase("admin"))
//                throw new RuntimeException("Unauthorized");

        // check password match with password user enter
        // check password match with password user enter
        if (!passwordEncoder.matches(adminLoginDTO.getPassword() , user.getPassword())) {
            throw new RuntimeException("Invalid User or Password");
        }



        return user;
    }

}

