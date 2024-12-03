package com.techupdating.techupdating.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.techupdating.techupdating.responses.UserInfoResponse;
import com.techupdating.techupdating.responses.UserLoginResponse;
import com.techupdating.techupdating.responses.UserResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "account")
    private String account;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "avatar")
    private String avatar;

    @Column(name="enabled")
    private boolean enabled;

    @Column(name = "is_two_ways_enabled")
    private Boolean isTwoWaysEnabled;

    public static UserLoginResponse toUserLoginResponse(User user) {

        return UserLoginResponse.builder()
                .id(user.getId())
                .fullname(user.getFullname())
                .avatar(user.getAvatar())
                .account(user.getAccount())
                .email(user.getEmail())
                .isTwoWaysSecurityEnabled(user.isTwoWaysEnabled)
                .build();

    }

    public static UserInfoResponse toUserInfoResponse(User user) {

        return UserInfoResponse.builder()
                .id(user.getId())
                .fullname(user.getFullname())
                .avatar(user.getAvatar())
                .account(user.getAccount())
                .email(user.getEmail())
                .build();

    }

    @ManyToMany( fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
            })
    @JoinTable(
            name = "user_course",
            joinColumns = @JoinColumn(name= "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;


    @OneToMany(mappedBy = "user", cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
    })
    private List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Comment> comments;


    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullname='" + fullname + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
