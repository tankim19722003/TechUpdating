package com.techupdating.techupdating.Services;

import com.techupdating.techupdating.dtos.*;
import com.techupdating.techupdating.models.User;
import com.techupdating.techupdating.responses.UserInfoResponse;
import com.techupdating.techupdating.responses.UserResponse;
import com.techupdating.techupdating.responses.UserTwoWayResponse;

public interface UserService {
    User register(UserRegisterDTO userRegisterDTO);

    User login(UserLoginDTO userLoginDTO);

    User loginAdmin(AdminLoginDTO adminLoginDTO);

    UserInfoResponse findInfoUser(int userId);

    UserInfoResponse updateFullname(int userId, String fullname);

    UserInfoResponse updateEmail(int userId, String email);

    boolean existsByUserId(int userId);

    UserTwoWayResponse updateTwoWaysSecurity(UserTwoWaySecurityDTO userTwoWaySecurityDTO);

    void changePassword(int userId, UserChangingPassworDTO userChangingPassworDTO);
}
