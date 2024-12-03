package com.techupdating.techupdating.Services;

import com.techupdating.techupdating.dtos.*;
import com.techupdating.techupdating.models.User;
import com.techupdating.techupdating.repositories.UserRepository;
import com.techupdating.techupdating.responses.UserInfoResponse;
import com.techupdating.techupdating.responses.UserResponse;
import com.techupdating.techupdating.responses.UserTwoWayResponse;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

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
                .password(userRegisterDTO.getPassword())
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

        if (user.getAvatar() == null) {
            user.setAvatar("user.png");
        }
        // check password match with password user enter
        if (!user.getPassword().equals(userLoginDTO.getPassword())) {
            throw new RuntimeException("Invalid User or Password");
        }

//        if (!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
//            throw new RuntimeException("Invalid User or Password");
//        }



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
//        if (!passwordEncoder.matches(adminLoginDTO.getPassword() , user.getPassword())) {
//            throw new RuntimeException("Invalid User or Password");
//        }



        return user;
    }

    public UserInfoResponse findInfoUser(
            int userId
    ) {

        // find user
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found!!")
        );

        if (user.getAvatar() == null) {
            user.setAvatar("user.png");
        }

        return UserInfoResponse.builder()
                .id(user.getId())
                .account(user.getAccount())
                .avatar(user.getAvatar())
                .fullname(user.getFullname())
                .email(user.getEmail())
                .build();
    }

    @Override
    @Transactional
    public UserInfoResponse updateFullname(int userId, String fullname) {
        // check user Existing
        User user = userRepository.findById(userId).orElseThrow(
                () ->  new RuntimeException("User does not existing!!")
        );

        if (fullname.isEmpty()) {
            throw new RuntimeException("Fullname can't be empty!!");
        }

        // update user
        int row = userRepository.updateFullnameById(fullname, userId);

        // get updated user
        if (row != 0) {
            user.setFullname(fullname);

            return User.toUserInfoResponse(user);
        }

        return null;
    }

    @Override
    public UserInfoResponse updateEmail(int userId, String email) {
        // check user Existing
        User user = userRepository.findById(userId).orElseThrow(
                () ->  new RuntimeException("User does not existing!!")
        );

        // check the valid email
        final String GMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
        Pattern pattern = Pattern.compile(GMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            throw new RuntimeException("Invalid email!!");
        }


        // update user
        int row = userRepository.updateEmailById(email, userId);

        // get updated user
        if (row != 0) {
            user.setEmail(email);

            return User.toUserInfoResponse(user);
        } else {
            throw new RuntimeException("Updated fail!!");
        }
    }

    @Override
    public boolean existsByUserId(int userId) {
        return userRepository.existsById(userId);
    }

    @Override
    @Transactional
    public UserTwoWayResponse updateTwoWaysSecurity(UserTwoWaySecurityDTO userTwoWaySecurityDTO) {

        User user = userRepository.findById(userTwoWaySecurityDTO.getId()).orElseThrow(
                () -> new RuntimeException("User does not found")
        );

        // COMPARE WITH HASH CODE
        if (!userTwoWaySecurityDTO.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Invalid password!!");
        }

        Boolean isTwoWaysSercurityEnabled = (user.getIsTwoWaysEnabled()) ? false:true;

        user.setIsTwoWaysEnabled(isTwoWaysSercurityEnabled);

        // convert user to user two way security response
        UserTwoWayResponse userTwoWayResponse = UserTwoWayResponse.builder()
                .id(user.getId())
                .twoWaysSecurityEnabled(user.getIsTwoWaysEnabled())
                .build();

        userRepository.save(user);

        return userTwoWayResponse;
    }

    @Override
    public void changePassword(int userId, UserChangingPassworDTO userChangingPassworDTO) {

        // check user existing
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User does not exist!!")
        );

        // check correct password
        if (!userChangingPassworDTO.getOldPassword().equals(user.getPassword())) {
            throw new RuntimeException("Password is incorrect!!");
        }

        // check password and retype password mismatch
        if (!userChangingPassworDTO.getRetypePassword().equals(userChangingPassworDTO.getNewPassword())) {
            throw  new RuntimeException("Password does not match!!");
        }

        // check new password differ from old password
        if (userChangingPassworDTO.getNewPassword().equals(user.getPassword())) {
            throw  new RuntimeException("New password cannot be the same as the old password!!");
        }

        // update password
        user.setPassword(userChangingPassworDTO.getNewPassword());
        userRepository.save(user);

    }

}

